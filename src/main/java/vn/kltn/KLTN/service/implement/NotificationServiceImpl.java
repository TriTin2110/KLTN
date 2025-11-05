package vn.kltn.KLTN.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import vn.kltn.KLTN.entity.Notification;
import vn.kltn.KLTN.modules.NoticeHandler;
import vn.kltn.KLTN.repository.NotificationRepository;
import vn.kltn.KLTN.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
	private NotificationRepository repository;
	private NoticeHandler noticeHandler;

	@Autowired
	public NotificationServiceImpl(NotificationRepository repository, NoticeHandler noticeHandler) {
		this.repository = repository;
		this.noticeHandler = noticeHandler;
	}

	@Override
	@Transactional
	public void add(Notification notification) {
		// TODO Auto-generated method stub
		try {
			this.repository.save(notification);
			updateStatusWebSocket(notification);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	@Async
	public void update(Notification notification) {
		// TODO Auto-generated method stub
		this.repository.saveAndFlush(notification);
	}

	@Override
	public List<Notification> findByUserIdOrderByLocalDateTimeDesc(String userId) {
		// TODO Auto-generated method stub
		return this.repository.findByUserIdOrderByLocalDateTimeDesc(userId);
	}

	@Override
	public Notification findByContent(String content) {
		// TODO Auto-generated method stub
		return this.repository.findByContent(content);
	}

	@Override
	@Transactional
	@Async
	public void updateStatus(String orderId, String status) {
		// TODO Auto-generated method stub
		this.repository.updateStatus(orderId, status, LocalDateTime.now());
		Notification notification = findByContent(orderId);
		updateStatusWebSocket(notification);
	}

	private void updateStatusWebSocket(Notification notification) {
		try {
			this.noticeHandler.updateNotificationSocket(notification);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
