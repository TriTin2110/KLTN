package vn.kltn.KLTN.controller;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.model.ProductStoreDTO;
import vn.kltn.KLTN.service.ProductService;
import vn.kltn.KLTN.service.VectorStoreSevice;

@RestController
@RequestMapping("/ai")
public class AIController {
	@Autowired
	private ProductService productService;
	@Autowired
	private VectorStoreSevice vectorStoreSevice;

	@GetMapping("/insert")
	public String insertData() {
		List<Product> products = productService.findAll();
		List<Document> documents = null;
		ProductStoreDTO productStoreDTO = null;
		StringBuilder builder = new StringBuilder();
		for (Product product : products) {
			productStoreDTO = new ProductStoreDTO(product);
			documents = vectorStoreSevice.insertData(productStoreDTO);
			builder.append(documents.get(0).getFormattedContent());
		}
		return builder.toString();
	}

	@GetMapping("/asking/{question}")
	public List<Document> getData(@PathVariable("question") String question) {
		return vectorStoreSevice.search(question);
	}
}
