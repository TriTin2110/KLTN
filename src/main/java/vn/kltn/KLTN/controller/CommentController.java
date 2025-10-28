package vn.kltn.KLTN.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import jakarta.servlet.http.HttpServletRequest;
import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.service.CommentService;

//@Controller
@RestController
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
			@RequestParam("username") String username) {
		if (content == null || content.trim().isEmpty()) {
			return null;
		}
		String commentId = username + "_" + productName + "_" + System.currentTimeMillis();
		// Nếu đã có bình luận → quay lại trang chi tiết
		Comment existed = commentService.findById(commentId);
		if (existed != null) {
			return null;
		}

		// Tạo mới
		Comment cmt = new Comment(commentId, content, new java.sql.Date(System.currentTimeMillis()), 0);
		cmt = commentService.add(cmt, productName, username);
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
			HttpServletRequest request) {
		Comment c = commentService.findById(commentId);
		if (c == null)
			return;
		if (content == null || content.trim().isBlank())
			return;
		c.setContent(content);
		commentService.update(c);
	}

//	/**
//	 * Xoá bình luận
//	 */
//	@PostMapping("/comments/{commentId}/delete")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
//	public String delete(@PathVariable String commentId, Authentication auth) {
//		Comment c = commentService.findById(commentId);
//		String productName = extractProductNameFromCommentId(commentId);
//		String pidEnc = encode(productName);
//
//		if (c == null) {
//			return "redirect:/san-pham/" + pidEnc + "?error=comment_not_found";
//		}
//
//		// Verify quyền: ADMIN hoặc owner
//		boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
//		boolean isOwner = c.getUser().getUsername().equals(auth.getName()); // Giả sử User có getUsername()
//		if (!isAdmin && !isOwner) {
//			return "redirect:/san-pham/" + pidEnc + "?error=forbidden";
//		}
//
//		boolean deleted = commentService.remove(commentId);
//		if (!deleted) {
//			return "redirect:/san-pham/" + pidEnc + "?error=delete_failed";
//		}
//
//		return "redirect:/san-pham/" + pidEnc + "#comments";
//	}

	/**
	 * Xoá bình luận
	 */
	@PostMapping("/delete/{commentId}")
	public void delete(@PathVariable("commentId") String commentId) {
		Comment c = commentService.findById(commentId);

		if (c == null) {
			return;
		}

		// Verify quyền: ADMIN hoặc owner
		boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
		boolean isOwner = c.getUser().getUsername().equals(auth.getName()); // Giả sử User có getUsername()
		if (!isAdmin && !isOwner) {
			return;
		}

		boolean deleted = commentService.remove(commentId);
		if (!deleted) {
			return;
		}

		return;
	}

	/* ===================== Tiện ích nội bộ ===================== */

	private static String extractProductNameFromCommentId(String commentId) {
		if (commentId == null)
			return "";
		int i = commentId.lastIndexOf('_');
		if (i >= 0 && i < commentId.length() - 1) {
			return commentId.substring(i + 1);
		}
		return "";
	}

	private static String encode(String raw) {
		return UriUtils.encodePathSegment(raw == null ? "" : raw, StandardCharsets.UTF_8);
	}
}