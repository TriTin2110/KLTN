package vn.kltn.KLTN.controller;

import java.nio.charset.StandardCharsets;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import vn.kltn.KLTN.entity.Comment;
import vn.kltn.KLTN.entity.Product;
import vn.kltn.KLTN.entity.User; 
import vn.kltn.KLTN.repository.ProductRepository;
import vn.kltn.KLTN.repository.UserRepository;
import vn.kltn.KLTN.service.CommentService;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentController(CommentService commentService, ProductRepository productRepository, UserRepository userRepository) {
        this.commentService = commentService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/san-pham/{id}/comments")
    @PreAuthorize("hasRole('USER')")
    public String create(@PathVariable("id") String idEncoded,
                         @RequestParam("content") String content,
                         Authentication auth) {
        String productName = UriUtils.decode(idEncoded, StandardCharsets.UTF_8);
        if (content == null || content.trim().isEmpty()) {
            return "redirect:/san-pham/" + encode(productName) + "?error=empty_comment";
        }

        
        Product product = productRepository.findById(productName).orElse(null);
        if (product == null) {
            return "redirect:/san-pham/" + encode(productName) + "?error=product_not_found";
        }

        String username = auth.getName();
        String commentId = username + "_" + productName;

        // Nếu đã có bình luận → quay lại trang chi tiết
        Comment existed = commentService.findById(commentId);
        if (existed != null) {
            return "redirect:/san-pham/" + encode(productName) + "?error=already_commented#comments";
        }

        // Lấy User từ DB bằng ID (username) và check
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            return "redirect:/san-pham/" + encode(productName) + "?error=user_not_found";
        }

        // Tạo mới
        Comment cmt = new Comment();
        cmt.setId(commentId);
        cmt.setContent(content.trim());
        cmt.setProduct(product);                                
        cmt.setDatePost(new java.sql.Date(System.currentTimeMillis()));
        cmt.setUser(user);

        boolean ok = commentService.add(cmt);

        if (!ok) {
            Comment after = commentService.findById(commentId);
            if (after != null) {
                return "redirect:/san-pham/" + encode(productName) + "#comments";
            }
            return "redirect:/san-pham/" + encode(productName) + "?error=comment_save_failed";
        }

        return "redirect:/san-pham/" + encode(productName) + "#comments";
    }

    @GetMapping("/comments/{commentId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String editForm(@PathVariable String commentId,
                           Authentication auth,
                           Model model) {
        Comment c = commentService.findById(commentId);
        if (c == null) {
            String productName = extractProductNameFromCommentId(commentId);
            String pidEnc = encode(productName);
            return "redirect:/san-pham/" + pidEnc + "?error=comment_not_found";
        }

        // Verify ownership
        if (!c.getUser().getUsername().equals(auth.getName())) {  // Giả sử User có getUsername()
            String productName = extractProductNameFromCommentId(commentId);
            String pidEnc = encode(productName);
            return "redirect:/san-pham/" + pidEnc + "?error=forbidden";
        }

        model.addAttribute("comment", c);
        return "comments/edit";
    }

    /**
     * Cập nhật nội dung bình luận
     */
    @PostMapping("/comments/{commentId}/edit")
    @PreAuthorize("hasRole('USER')")
    public String update(@PathVariable String commentId,
                         @RequestParam("content") String content,
                         Authentication auth) {
        Comment c = commentService.findById(commentId);
        String productName = extractProductNameFromCommentId(commentId);
        String pidEnc = encode(productName);

        if (c == null) {
            return "redirect:/san-pham/" + pidEnc + "?error=comment_not_found";
        }

        // Verify ownership
        if (!c.getUser().getUsername().equals(auth.getName())) {  // Giả sử User có getUsername()
            return "redirect:/san-pham/" + pidEnc + "?error=forbidden";
        }

        if (content == null || content.trim().isEmpty()) {
            String cidEnc = encode(commentId);
            return "redirect:/comments/" + cidEnc + "/edit?error=empty_comment";
        }

        c.setContent(content.trim());
        boolean updated = commentService.update(c);

        if (!updated) {
            return "redirect:/san-pham/" + pidEnc + "?error=update_failed";
        }

        return "redirect:/san-pham/" + pidEnc + "#comments";
    }

    /**
     * Xoá bình luận
     */
    @PostMapping("/comments/{commentId}/delete")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public String delete(@PathVariable String commentId,
                         Authentication auth) {
        Comment c = commentService.findById(commentId);
        String productName = extractProductNameFromCommentId(commentId);
        String pidEnc = encode(productName);

        if (c == null) {
            return "redirect:/san-pham/" + pidEnc + "?error=comment_not_found";
        }

        // Verify quyền: ADMIN hoặc owner
        boolean isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean isOwner = c.getUser().getUsername().equals(auth.getName());  // Giả sử User có getUsername()
        if (!isAdmin && !isOwner) {
            return "redirect:/san-pham/" + pidEnc + "?error=forbidden";
        }

        boolean deleted = commentService.remove(commentId);
        if (!deleted) {
            return "redirect:/san-pham/" + pidEnc + "?error=delete_failed";
        }

        return "redirect:/san-pham/" + pidEnc + "#comments";
    }

    /* ===================== Tiện ích nội bộ ===================== */

    private static String extractProductNameFromCommentId(String commentId) {
        if (commentId == null) return "";
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