package vn.kltn.KLTN.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.repository.UserRepository;
import vn.kltn.KLTN.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository repository;

	@Override
	public User signIn(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public User signUp(User user) {
		// TODO Auto-generated method stub
		return repository.save(user);
	}

	@Override
	@Transactional
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(String userName) {
		// TODO Auto-generated method stub
		Optional<User> optUser = repository.findById(userName);
		return (optUser.isEmpty()) ? null : optUser.get();
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public boolean delete(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

}
