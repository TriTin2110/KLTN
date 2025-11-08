package vn.kltn.KLTN.model;

import java.time.LocalDateTime;

public class CommentDTO {
	private String id;
	private String content;
	private LocalDateTime datePost;
	private int rating;

	public CommentDTO() {
		super();
	}

	public CommentDTO(String id, String content, LocalDateTime datePost, int rating) {
		super();
		this.id = id;
		this.content = content;
		this.datePost = datePost;
		this.rating = rating;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getDatePost() {
		return datePost;
	}

	public void setDatePost(LocalDateTime datePost) {
		this.datePost = datePost;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
