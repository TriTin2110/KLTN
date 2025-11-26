package vn.kltn.KLTN.service.implement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import vn.kltn.KLTN.model.ProductStoreDTO;
import vn.kltn.KLTN.service.VectorStoreSevice;

@Service
public class VectorStoreServiceImpl implements VectorStoreSevice {
	@Autowired
	private VectorStore vectorStore;
	@Autowired
	@Qualifier("myCustomChatClient") // Chỉ định dùng Bean tự tạo
	private ChatClient customChatClient;
	private ChatClient outputChatClient;
	@Value("classpath:/OutputForAI.txt")
	private Resource outputSystemResource;

	private final float SCORE = 0.8f;

	@Autowired
	public VectorStoreServiceImpl(ChatClient.Builder builder) {
		ChatMemory memory = MessageWindowChatMemory.builder().maxMessages(5).build();
		this.outputChatClient = builder.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build()).build();
	}

	@Override
	public List<Document> insertData(ProductStoreDTO productStoreDTO) {
		// TODO Auto-generated method stub
		Document document = new Document(productStoreDTO.toString(),
				Map.of("name", productStoreDTO.getName(), "category", productStoreDTO.getCategory(), "image",
						productStoreDTO.getImage(), "productStatus", productStoreDTO.getProductStatus(), "price",
						productStoreDTO.getPrice()));
		List<Document> documentSplitted = getDocumentSplitted(document);
		vectorStore.add(documentSplitted);
		return documentSplitted;
	}

	private List<Document> getDocumentSplitted(Document document) {
		TextSplitter splitter = new TokenTextSplitter();
		List<Document> documentSplitted = splitter.split(document);
		return documentSplitted;
	}

	@Override
	public String search(String userInput) {
		// TODO Auto-generated method stub
		String prompt = customChatClient.prompt(userInput).call().chatResponse().getResult().getOutput().getText();
		return getDataByDescription(prompt, userInput);
	}

	private String getDataByDescription(String prompt, String userInput) {
		// TODO Auto-generated method stub
		List<Document> documents = null;
		System.out.println(prompt);
		if (!prompt.matches("^\\[Tên sản phẩm:[^,]*,Thể loại:[^,]*, Giá:[^\\]]*\\]$")) {
			return prompt;
		} else {
			prompt = prompt.replace("[", "").replace("]", "");
			String[] inputSplit = prompt.split(",");
			String name = inputSplit[0].split(":")[1];
			String category = inputSplit[1].split(":")[1];
			String price = inputSplit[2].split(":")[1];
			String tempPrice = price;
			String numberOnly = tempPrice.replaceAll("[^0-9]", "");// Vì price sẽ có thể chứa "<" hoặc ">". Xóa "<" hoặc
																	// ">"
																	// nếu có
			System.out.println("name: " + name + ", category: " + category + ", price: " + price);
			StringBuilder builder = new StringBuilder();
			if (!name.equals("null")) {
				builder.append("name=" + name);
			}

			if (!category.equals("null")) {
				builder.append(", category=" + category);
			}
			if (!price.contains("null") && !price.equalsIgnoreCase("max") && !price.equalsIgnoreCase("min")) {
				builder.append(", price=" + numberOnly);
			}
			if (builder.isEmpty()) {
				System.out.println("Bạn có thể cho tôi biết thức uống yêu thích của bạn không? (Ví dụ: Cà phê)");
			} else {

				documents = filter(tempPrice, numberOnly, builder);
				if (price.equalsIgnoreCase("max") || price.equalsIgnoreCase("min"))// Nếu yêu cầu giá cao/thấp nhất
					documents = getMinMax(documents, tempPrice);
			}
			return getOutput(documents, userInput);
		}
	}

	private List<Document> filter(String price, String numberOnly, StringBuilder builder) {
		FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
		Expression expression = null;

		if (!price.equals("null") && !price.equalsIgnoreCase("max") && !price.equalsIgnoreCase("min")) {
			int priceInt = Integer.parseInt(numberOnly);
			if (price.contains("<")) { // Trường hợp người dùng so sánh giá với số cụ thể
				expression = filterBuilder.lt("price", priceInt).build();
			} else if (price.contains(">")) {
				expression = filterBuilder.gt("price", priceInt).build();
			}
		}
		List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder().query(builder.toString())
				.similarityThreshold(SCORE).filterExpression(expression).build());
		return documents;
	}

	private List<Document> getMinMax(List<Document> documents, String price) { // Khi người dùng yêu cầu lấy đồ uống với
																				// giá cao/thấp nhất
		if (documents.size() < 2)
			return null;
		documents = documents.stream().sorted((o1, o2) -> {
			int p1 = Integer.parseInt(o1.getMetadata().get("price").toString());
			int p2 = Integer.parseInt(o2.getMetadata().get("price").toString());

			return price.equalsIgnoreCase("max") ? Integer.compare(p2, p1) : Integer.compare(p1, p2);
		}).toList();
		return Arrays.asList(documents.get(0));
	}

	private String getOutput(List<Document> documents, String userInput) {
		String resultText = documents.stream().map(product -> {
			return "[ name: " + product.getMetadata().get("name").toString() + ", price: "
					+ product.getMetadata().get("price").toString() + ", status: "
					+ product.getMetadata().get("productStatus").toString() + ", category: "
					+ product.getMetadata().get("category").toString() + "]";
		}).collect(Collectors.joining(", "));

		System.out.println(resultText);
		String promptAnswer = """
				Bạn là trợ lý tìm kiếm đồ uống.
				Người dùng vừa tìm kiếm: "%s".

				Danh sách các sản phẩm thực tế có trong cửa hàng (chỉ được sử dụng các sản phẩm này):
				%s

				Nhiệm vụ:
				1. Phân tích ý định (intent) của truy vấn để tạo câu mở đầu phù hợp:
				   - Nếu truy vấn chứa: "rẻ nhất", "giá thấp", "giá rẻ"
				       → Câu mở đầu phải thỏa mãn được nhu cầu của khách hàng
				   - Nếu truy vấn chứa: "đắt nhất", "cao nhất", "giá cao"
				       → Câu mở đầu phải thỏa mãn được nhu cầu của khách hàng
				   - Nếu truy vấn chứa: "ngon nhất", "best", "được đánh giá cao", "tốt nhất"
				      → Câu mở đầu phải thỏa mãn được nhu cầu của khách hàng
				   - Nếu truy vấn chứa: "có ... không", "có không", "có ko"
				        → Câu mở đầu phải thỏa mãn được nhu cầu của khách hàng
				   - Nếu truy vấn không rơi vào các loại trên
				       → Câu mở đầu trung tính, ví dụ:
				         "Tôi nghĩ đây là một số sản phẩm phù hợp với bạn:"

				2. Nếu người dùng yêu cầu:
				- “các loại khác với các loại trên”
				- “còn loại nào nữa không?”
				- “ngoài những loại đó thì còn gì?”

				Và câu trước (người dùng vừa nói) có đề cập đến một danh sách sản phẩm cụ thể,
				thì bạn phải:
					1) Nhận dạng các sản phẩm người dùng muốn loại trừ.
					2) Loại trừ chúng khỏi danh sách đầy đủ.
					3) Nếu không còn gì → trả “Không tìm thấy đồ uống phù hợp.”

				Nếu không có sản phẩm nào phù hợp → trả lời rằng không có sản phẩm phù hợp hoặc là không tìm thấy sản phẩm phù họp với yêu cầu của người dùng!

				3. Nếu có sản phẩm phù hợp → liệt kê theo format bắt buộc:
				   <a class="product-chat-bot-result" target="_blank" href="http://localhost:8080/san-pham/[Tên sản phẩm 1]">1. [Tên sản phẩm 1]</a>
				        <a class="product-chat-bot-result" target="_blank" href="http://localhost:8080/san-pham/[Tên sản phẩm 2]">2. [Tên sản phẩm 2]</a>
				        <a class="product-chat-bot-result" target="_blank" href="http://localhost:8080/san-pham/[Tên sản phẩm 3]">3. [Tên sản phẩm 3]</a>

				4. Không mô tả sản phẩm, không kèm giá, không phân tích thêm.
				5. Không tạo ra sản phẩm không có trong danh sách thực tế.
				6. Output chỉ gồm 1 câu mở đầu + danh sách sản phẩm (nếu có).
				"""
				.formatted(userInput, resultText);

		String answer = outputChatClient.prompt(promptAnswer).call().chatResponse().getResult().getOutput().getText();// Tạo
																														// câu
																														// mở
																														// đầu
		return answer;
	}

}
