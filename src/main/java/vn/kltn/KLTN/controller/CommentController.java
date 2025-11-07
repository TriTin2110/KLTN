package vn.kltn.KLTN.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	@ResponseBody
	public Map<String, Object> create(@PathVariable("productName") String productName,
			@RequestBody Map<String, String> map, @AuthenticationPrincipal User user) {
		String content = map.get("content");
		int rating = Integer.parseInt(map.get("rating"));
		Map<String, Object> result = new HashMap<String, Object>();
		boolean success = true;
		result.put("success", true);
		if (content == null || content.trim().isEmpty()) {
			success = false;
		}
		String commentId = user.getUsername() + "_" + productName + "_" + System.currentTimeMillis();

		// Tạo mới
		Comment cmt = new Comment(commentId, content, LocalDateTime.now(), rating);
		cmt = commentService.add(cmt, productName, user.getUsername());
		if (cmt == null) {
			success = false;

		}
		if (success) {
			System.out.println(user.getUsername());
			result.put("comment", cmt);
			result.put("userNameComment", user.getUsername());
		}
		result.put("success", success);
		return result;
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