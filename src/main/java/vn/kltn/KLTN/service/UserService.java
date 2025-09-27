package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.User;

public interface UserService {
	public User signIn(String userName, String password);

	public User signUp(User user);

	public User update(User user);

	public User findById(String userName);

	public List<User> findAll();

	public boolean delete(String userName);

	public boolean updateComment(User user, Comment comment);
}
