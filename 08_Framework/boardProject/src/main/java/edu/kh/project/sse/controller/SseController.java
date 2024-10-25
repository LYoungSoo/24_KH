package edu.kh.project.sse.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import edu.kh.project.member.dto.Member;
import edu.kh.project.sse.dto.Notification;
import edu.kh.project.sse.service.SseService;
import lombok.extern.slf4j.Slf4j;

//@Controller + @ResponseBody (메서드 반환값을 포워드하지 않고 응답 본문으로 그대로 반환)
@RestController		// 비동기 통신만 하는 컨트롤러	
@Slf4j
public class SseController {
	
	@Autowired
	private SseService service;
	
	// Emitter : 이미터 : 연결된 클라이언트를 의미하는 객체 : 발행인, 발포자 대충 그런뜻
	// SseEmitter : 서버로부터 메시지를 전닥 받을 클라이언트 정보를 저장한 객체 == 연결된 클라이언트
	
	// Concurrent : 동시발생
	// ConcurrentHashMap : 멀티스레드 환경에서 동기화를 보장하는 Map
	// ==> 한 번에 많은 요청이 있어도 차례대로 처리
	private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
	// == 연결된 클라이언트 대기 명단
	
	
	/* 클라이언트 연결 요청 처리 */
	@GetMapping("sse/connect")
	public SseEmitter sseConnect(
		@SessionAttribute("loginMember") Member loginMember
	) {
		
		// Map에 저장될 Key 값으로 회원 번호 얻어오기
		String clientId = loginMember.getMemberNo() + "";
		
		// SseEmitter 객체 생성
		// ==> 연결 대기 시간 10분 설정
		SseEmitter emitter = new SseEmitter(10 * 60 * 1000L);
		
		// 클라이언트 정보를 Map 에 추가
		emitters.put(clientId, emitter);
		
		// 클라이언트 연결 종료 시 Map(대기목록) 에서 제거
		// 람다(lambda) 써야함 ==> 콜백함수(매개변수) 작성은 쉬움
		emitter.onCompletion(() -> emitters.remove(clientId));
		
		// 클라이언트 타임아웃 시 Map 에서 제거
		emitter.onTimeout(() -> emitters.remove(clientId));
		
		return emitter;
	}
	
	
	/**
	 * 알림 메시지 전송
	 */
	@PostMapping("sse/send")
	public void sendNotification(
		@RequestBody Notification notification,
		@SessionAttribute("loginMember") Member loginMember
	) {
		
		// 알림 보낸 회원(현재 로그인한 회원) 번호 추가
		notification.setSendMemberNo(loginMember.getMemberNo());
		
		// 전달 받은 알림 데이터를 DB에 저장하고
		// 알림 받을 회원의 번호
		// + 해당 회원이 읽지 않은 알림 개수 반환 받는 서비스 호출
		Map<String, Object> map = service.insertNotification(notification);
		
		// 알림을 받을 클라이언트 id == 알림 받을 회원 번호
		String clientId = map.get("receiveMemberNo").toString();
		
		// 연결된 클라이언트 대기 명단(emitters) 에서
		// clientId가 일치하는 클라이언트 찾기
		SseEmitter emitter = emitters.get(clientId);	// for 문으로 검색할 필요도 없다!
		
		// clientId 가 일치하는  클라이언트가 있을 경우
		if(emitter != null) {
			try {
				emitter.send(map);		// 누가, 몇개나, 안읽었구나! 를 묶어서 보낼것임
			} catch(Exception e) {
				emitters.remove(clientId);
			}
		}
	}
	
	
	// --------------------------------------------------------------------------------
	
	/**
	 * 로그인한 회원의 알림 목록 조회
	 * @param loginMember
	 * @return
	 */
	@GetMapping("notification")
	public List<Notification> selectNotificationList(
		@SessionAttribute("loginMember") Member loginMember
	) {
		int memberNo = loginMember.getMemberNo();
		return service.selectNotificationList(memberNo);
	}
	
	/**
	 * 현재 로그인한 회원의 알림 중 읽지 않은 알림 개수 조회
	 * ("NOTIFICATION".NOTIFICATION_CHECK = 'N')
	 * @return
	 */
	@GetMapping("notification/notReadCheck")
	public int notReadCheck(
		@SessionAttribute("loginMember") Member loginMember
	) {
		return service.notReadCheck(loginMember.getMemberNo());
	}
	
	/**
	 * 알림 삭제
	 * @param notificationNo
	 */
	@DeleteMapping("notification")
	public void deleteNotification(
		@RequestBody int notificationNo
	) {
		service.deleteNotification(notificationNo);
	}
	
	/**
	 * 알림 읽음 여부 변경(N ==> Y)
	 * @param notificationNo
	 */
	@PutMapping("notification")
	public void updateNotification(
		@RequestBody int notificationNo
	) {
		service.updateNotification(notificationNo);
	}
	
	
	
	
	
	
}
