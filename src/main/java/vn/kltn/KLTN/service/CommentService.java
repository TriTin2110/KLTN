package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.model.CommentDTO;

public interface CommentService {
	public CommentDTO add(Comment comment, Product product, User user);

	/*
	 * + remove(id: String): boolean id: username + "_" + productName
	 */
	public boolean remove(String id);

	public boolean update(Comment comment);

	public List<Comment> findAllByProductId(String productId);

	public Comment findById(String commentId);

	public List<Comment> findAll();
}
