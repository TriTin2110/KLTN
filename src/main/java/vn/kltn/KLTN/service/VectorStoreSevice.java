package vn.kltn.KLTN.service;

import java.util.List;

import org.springframework.ai.document.Document;

import vn.kltn.KLTN.model.ProductStoreDTO;

public interface VectorStoreSevice {
	public List<Document> insertData(ProductStoreDTO productStoreDTO);

	public List<Document> search(String input);
}
