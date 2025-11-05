package vn.kltn.KLTN.service;

import java.util.List;

import vn.kltn.KLTN.entity.Notification;

public interface NotificationService {
	public void add(Notification notification);

	public void update(Notification notification);

	public List<Notification> findByUserIdOrderByLocalDateTimeDesc(String userId);

	public Notification findByContent(String content);

	public void updateStatus(String orderId, String status);
}
