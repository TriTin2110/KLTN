package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kltn.KLTN.model.ProductStoreDTO;
import vn.kltn.KLTN.service.VectorStoreSevice;

@Service
public class VectorStoreServiceImpl implements VectorStoreSevice {
	@Autowired
	private VectorStore vectorStore;

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
	public String getData(String attribute) {
		// TODO Auto-generated method stub
		return null;
	}

}
