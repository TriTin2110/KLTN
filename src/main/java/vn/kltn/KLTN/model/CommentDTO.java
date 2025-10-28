package vn.kltn.KLTN.model;

public class CommentDTO {
	private String commentId, productID, content;
	private int rating;

	public CommentDTO() {
	}

	public CommentDTO(String commentId, String productID, int rating, String content) {
		this.commentId = commentId;
		this.productID = productID;
		this.rating = rating;
		this.content = content;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
