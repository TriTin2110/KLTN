package vn.kltn.KLTN.service.implement;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
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
	private ChatClient.Builder builder;
	@Autowired
	@Qualifier("myCustomChatClient") // Chỉ định dùng Bean tự tạo
	private ChatClient customChatClient;
	@Value("classpath:/OutputForAI.txt")
	private Resource outputSystemResource;

	private final float SCORE = 0.8f;

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
		System.out.println(prompt);
		return getDataByDescription(prompt, userInput);
	}

	private String getDataByDescription(String prompt, String userInput) {
		// TODO Auto-generated method stub
		List<Document> documents = null;
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
		StringBuilder builder = new StringBuilder();
		ProductStoreDTO store = null;
		String resultText = documents.stream().map(product -> {
			return "[" + product.getMetadata().get("name").toString() + product.getMetadata().get("price").toString()
					+ product.getMetadata().get("productStatus").toString() + "]";
		}).collect(Collectors.joining(", "));

		String promptAnswer = """
				Bạn là trợ lý tìm kiếm đồ uống.
				Người dùng vừa tìm kiếm: "%s".

				Danh sách các sản phẩm thực tế có trong cửa hàng:
				%s

				Yêu cầu:
				1. Nếu có ít nhất một sản phẩm phù hợp với truy vấn, trả lời 1 câu HTML tự nhiên: <p>...</p>
				2. Nếu không có sản phẩm nào phù hợp, phải trả lời **chính xác**:
				   "Không tìm thấy đồ uống phù hợp."
				3. Không được thêm bất kỳ sản phẩm nào khác, không được giải thích, không bịa.
				4. Trả lời ngắn gọn, tự nhiên.
				""".formatted(userInput, resultText);

		String answer = this.builder.build().prompt(promptAnswer).call().chatResponse().getResult().getOutput()
				.getText();// Tạo câu mở đầu
		builder.append(answer);
		if (!"Không tìm thấy đồ uống phù hợp.".equalsIgnoreCase(answer)) {
			builder.append("<div class=\"product-list\">");
			for (Document document : documents) {
				store = new ProductStoreDTO(document);
				builder.append("<div class=\"product-item\">\r\n" + "    <h3 class=\"product-name\">" + store.getName()
						+ "</h3>\r\n" + "    <p class=\"product-category\"><strong>Loại:</strong> "
						+ store.getCategory() + "</p>\r\n" + "    <p class=\"product-price\"><strong>Giá:</strong> "
						+ store.getPrice() + "</p>\r\n"
						+ "    <img class=\"product-image\" src=\"https://s3.cloudfly.vn/kltn/images/"
						+ store.getImage() + "\" alt=\"" + store.getName() + "\" />\r\n" + "  </div>" + "");
			}
			builder.append("</div>");
		}
		return builder.toString();
	}

}
