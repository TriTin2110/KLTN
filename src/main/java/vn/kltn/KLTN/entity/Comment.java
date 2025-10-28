package vn.kltn.KLTN.entity;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	private String id;
	private String content;
	private Date datePost;
	private int rating;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "user_name")
	private User user;

	public Comment() {
	}

	public Comment(String id, String content, Date datePost, int rating) {
		this.id = id;
		this.content = content;
		this.datePost = datePost;
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

	public Date getDatePost() {
		return datePost;
	}

	public void setDatePost(Date datePost) {
		this.datePost = datePost;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", datePost=" + datePost + "]";
	}

}
