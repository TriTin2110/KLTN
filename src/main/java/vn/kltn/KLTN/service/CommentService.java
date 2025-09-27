package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Comment;

public interface CommentService {
	public boolean add(Comment comment);

	/*
	 * + remove(id: String): boolean id: username + "_" + productName
	 */
	public boolean remove(String id);

	public boolean update(Comment comment);

	public List<Comment> findAllByProductId(String productId);

	public Comment findById(String commentId);

	public List<Comment> findAll();
}
