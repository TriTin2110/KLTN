package vn.kltn.KLTN.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
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
	@Transactional
	public News add(News news) {
		// TODO Auto-generated method stub
		return repository.save(news);
	}

	@Override
	@Transactional
	public News update(News news) {
		// TODO Auto-generated method stub
		return repository.saveAndFlush(news);
	}

	@Override
	@Transactional
	public boolean remove(String newsId) {
		// TODO Auto-generated method stub
		try {
			repository.deleteById(newsId);
		} catch (IllegalArgumentException | OptimisticLockingFailureException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public News findById(String newsId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Cacheable("news")
	public List<News> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	@CachePut("news")
	public List<News> updateCache() {
		return repository.findAll();
	}

}
