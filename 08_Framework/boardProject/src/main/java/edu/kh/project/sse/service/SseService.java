package edu.kh.project.sse.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.sse.dto.Notification;

public interface SseService {

	/**
	 * 알림 삽입 후 알림 받을 회원 번호 + 알림 개수 반환
	 * @param notification
	 * @return
	 */
	Map<String, Object> insertNotification(Notification notification);

	/**
	 * 로그인한 회원의 알림 목록 조회
	 * @param memberNo
	 * @return list
	 */
	List<Notification> selectNotificationList(int memberNo);

}
