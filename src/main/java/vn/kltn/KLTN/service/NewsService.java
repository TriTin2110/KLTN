package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.News;

public interface NewsService {
	public String encryptContent(String rawContent);

	public String getDecryptedContent(String encryptedContent);

	public News add(News news);

	public News update(News news);

	public boolean remove(String newsId);

	public News findById(String newsId);

	public List<News> findAll();
}
