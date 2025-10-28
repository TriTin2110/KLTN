package vn.kltn.KLTN.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kltn.KLTN.entity.News;
import vn.kltn.KLTN.repository.NewsRepository;
import vn.kltn.KLTN.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	private NewsRepository repository;

	@Autowired
	public NewsServiceImpl(NewsRepository repository) {
		this.repository = repository;
	}

	@Override
	public String encryptContent(String rawContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDecryptedContent(String encryptedContent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News add(News news) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public News update(News news) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String newsId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public News findById(String newsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<News> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
