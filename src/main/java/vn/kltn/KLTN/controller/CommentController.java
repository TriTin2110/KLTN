package vn.kltn.KLTN.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.User;
import vn.kltn.KLTN.enums.RoleAvailable;
import vn.kltn.KLTN.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	private final CommentService commentService;

	@Autowired
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/{productName}")
	@PreAuthorize("hasRole('USER')")
	
	public Comment create(@PathVariable("productName") String productName, @RequestParam("content") String content,
			@RequestParam("rating") int rating, @AuthenticationPrincipal User user) {
		if (content == null || content.trim().isEmpty()) {
			return null;
		}
		String commentId = user.getUsername() + "_" + productName + "_" + System.currentTimeMillis();
		// Nếu đã có bình luận → quay lại trang chi tiết
		Comment existed = commentService.findById(commentId);
		if (existed != null) {
			return null;
		}

		// Tạo mới
		Comment cmt = new Comment(commentId, content, new java.sql.Date(System.currentTimeMillis()), rating);
		cmt = commentService.add(cmt, productName, user.getUsername());
		if (cmt == null) {
			return null;
		}
		return cmt;
	}

	
	
	/**
	 * Cập nhật nội dung bình luận
	 */
	@PostMapping("/edit/{commentId}")
	@PreAuthorize("hasRole('USER')")
	public void update(@PathVariable String commentId, @RequestParam("content") String content,
			@RequestParam("rating") int rating) {
		Comment c = commentService.findById(commentId);
		if (c == null)
			return;
		if (content == null || content.trim().isBlank())
			return;
		c.setContent(content);
		c.setRating(rating);
		commentService.update(c);
	}

	/**
	 * Xoá bình luận
	 */
	@PostMapping("/delete/{commentId}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public void delete(@PathVariable("commentId") String commentId, @AuthenticationPrincipal User user) {
		Comment c = commentService.findById(commentId);
		if (c == null) {
			return;
		}
		String userComment = c.getUser().getUsername();
		// Verify quyền: ADMIN hoặc owner
		boolean valid = user.getRole().getType().equals(RoleAvailable.ROLE_ADMIN)
				|| user.getUsername().equals(userComment);
		if (valid) {
			commentService.remove(commentId);
		} else {
			return;
		}
	}
}