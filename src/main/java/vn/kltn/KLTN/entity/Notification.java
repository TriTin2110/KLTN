package vn.kltn.KLTN.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

//Được thiết kế chỉ dành cho khách hàng
@Entity
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title, image;
	@Lob
	private String content;
	private LocalDateTime localDateTime;
	private String userId, type;

	public Notification() {
	}

	public Notification(String content, LocalDateTime localDateTime, String type) {
		this.content = content;
		this.localDateTime = localDateTime;
		this.type = type;
	}

	public Notification(String title, String image, String content, LocalDateTime localDateTime, String userId,
			String type) {
		this.title = title;
		this.image = image;
		this.content = content;
		this.localDateTime = localDateTime;
		this.userId = userId;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
