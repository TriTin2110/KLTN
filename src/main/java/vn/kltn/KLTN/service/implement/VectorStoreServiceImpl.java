package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter.Expression;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kltn.KLTN.model.ProductStoreDTO;
import vn.kltn.KLTN.service.VectorStoreSevice;

@Service
public class VectorStoreServiceImpl implements VectorStoreSevice {
	@Autowired
	private VectorStore vectorStore;
	@Autowired
	private ChatClient chatClient;

	@Override
	public List<Document> insertData(ProductStoreDTO productStoreDTO) {
		// TODO Auto-generated method stub
		Document document = new Document(productStoreDTO.toString(),
				Map.of("name", productStoreDTO.getName(), "category", productStoreDTO.getCategory(), "image",
						productStoreDTO.getImage(), "productStatus", productStoreDTO.getProductStatus().getLabel(),
						"price", productStoreDTO.getPrice()));
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
	public List<Document> search(String input) {
		// TODO Auto-generated method stub
		String prompt = chatClient.prompt(input).call().chatResponse().getResult().getOutput().getText();
		System.out.println(prompt);
		if (prompt.equals("Xin lỗi nhưng tôi không thể hỗ trợ bạn với yêu cầu này!"))
			return null;
		List<Document> documents = getDataByDescription(prompt);
		return documents;
	}

	private List<Document> getDataByDescription(String input) {
		// TODO Auto-generated method stub
		input = input.replace("[", "").replace("]", "");
		String[] inputSplit = input.split(",");
		String name = inputSplit[0].split(":")[1];
		String category = inputSplit[1].split(":")[1];
		String price = inputSplit[2].split(":")[1];
		String rawPrice = price;
		String numberOnly = rawPrice.replaceAll("[^0-9]", "");// Vì price sẽ có thể chứa "<" hoặc ">". Xóa "<" hoặc ">"
																// nếu có
		System.out.println("name: " + name + ", category: " + category + ", price: " + price);
		StringBuilder builder = new StringBuilder();
		if (!name.equals("null"))
			builder.append("name=" + name);
		if (!category.equals("null"))
			builder.append(", category=" + category);
		if (!price.contains("null")) {
			builder.append(", price=" + numberOnly);
		}

		List<Document> documents = filter(rawPrice, numberOnly, builder);
		return documents;
	}

	private List<Document> filter(String price, String numberOnly, StringBuilder builder) {
		FilterExpressionBuilder filterBuilder = new FilterExpressionBuilder();
		Expression expression = null;
		int priceInt = Integer.parseInt(numberOnly);
		if (!price.equals("null")) {
			if (price.contains("<")) {
				expression = filterBuilder.lt("price", priceInt).build();
			} else if (price.contains(">")) {
				expression = filterBuilder.gt("price", priceInt).build();
			}
		}
		List<Document> documents = vectorStore.similaritySearch(SearchRequest.builder().query(builder.toString())
				.similarityThreshold(0.8).filterExpression(expression).build());
		return documents;
	}
}
