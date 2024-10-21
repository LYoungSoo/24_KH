package edu.kh.project.chatting.handler;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.project.chatting.dto.Message;
import edu.kh.project.chatting.service.ChattingService;
import edu.kh.project.member.dto.Member;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

/**
 * 채팅 웹소켓 요청 시 수행할 구문을 작성한 클래스
 */
@Component		// Bean 등록
@Slf4j
public class ChattingWebsocketHandler extends TextWebSocketHandler {
	
	@Autowired
	private ChattingService service;
	
	// WebsocketSession : HTTP Session 객체를 가로챈 값을 가지고 있는 객체 (SessionHandshakeInterceptor)
	// 	- 클라이언트 <-> 서버 전이중 통신을 담당하는 중요한 객체!!
	
	// synchronizedSet : 동기화된 Set(충돌 방지, 속도 조금 느림) ==> 안정성을 확보한 Set
	private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());
	
	
	// 클라이언트 연결이 완료 된 후 수행		// afterConnectionEstablished : 연결이 확립된 후
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		
		// 연결된 클라이언트의 Session을 sessions 라는 Map 에 저장 ==> 연결된 클라이언트를 목록화
		sessions.add(session);
	}
	
	
	// 클라이언트 와의 연결이 종료 되었을 때
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);		// 목록에서 제거
	}

	
	// 클라이언트로 부터 SockJS.send() 구문을 이용해 텍스트 메시지가 전달된 경우
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		// : message : {"targetNo":"5","messageContent":"ㅎㅇ","chattingRoomNo":"7"}
		log.debug("message : {}", message.getPayload());		// message 에 적재된 값을 get 해라
		
		// ObjectMapper : JSON <-> DTO 변환하는 객체(Jackson 라이브러리 제공)
		ObjectMapper objectMapper = new ObjectMapper();
		
		// 전달 받은 JSON 메시지를 Message 클래스 형태로 변환해서 값을 읽어와 Message 객체에 대입
		Message msg = objectMapper.readValue(message.getPayload(), Message.class);
		
		// 채팅을 보낸 회원의 회원 번호 얻어오기
		// ==> 로그인한 회원 번호(session) ===> WebSocketSession 에 담겨있음!!! (인터셉터 참고)
		HttpSession currentSession = (HttpSession)session.getAttributes().get("session");
		
		// 채팅 보낸 회원
		Member sendMember = ((Member)currentSession.getAttribute("loginMember"));
		
		int senderNo = sendMember.getMemberNo();	// 보낸 회원 번호
		
		msg.setSenderNo(senderNo);		// Message 객체에 세팅
		
		// ---------- DBINSERT ----------
		
		// 1) ChattingService 의존성 주입 받기(필드)
		
		// 2) INSERT 서비스 호출
		// (msg 값 : chattingRoomNo, messageContent, senderNo, targetNo)
		int result = service.insertMessage(msg);
		
		if(result == 0) return;
		
		// 채팅이 보내진 시간을 msg에 기록
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd hh:mm");
		
		msg.setSendTime(sdf.format(new Date()));
		
		// ---------- DBINSERT ----------
		
		
		// 연결된 모든 클라이언트를 순차 접근
		for(WebSocketSession wss : sessions) {

			// 채팅방에 입장한 사람들(보낸사람, 받는사람) 에게만 메시지(msg) 전달
			
			HttpSession clientSession = (HttpSession)wss.getAttributes().get("session");
			
			// 웹소켓 접속 회원 목록에서 꺼낸 회원 번호
			int clientNo = ((Member)clientSession.getAttribute("loginMember")).getMemberNo();
			
			// 받을 대상의 memberNo 혹은 보낸 사람의 memberNo 와 같을때 ==> 보낸자랑 받는자 모두에게
			if(msg.getTargetNo() == clientNo || msg.getSenderNo() == clientNo) {
				
				// msg 객체를 JSON 으로 변환 ==> 이 반환값을 JS가 사용하므로 반환값이 맞지 않으면 고장남
				TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(msg));
				
				wss.sendMessage(textMessage);
			}
			
		}
		
	}
	
}

/*
WebSocketHandler 인터페이스 : 웹소켓을 위한 메소드를 지원하는 인터페이스
	==>WebSocketHandler 인터페이스를 상속받은 클래스를 이용해 웹소켓 기능을 구현

WebSocketHandler 주요 메소드
       
   void handlerMessage(WebSocketSession session, WebSocketMessage message)
   - 클라이언트로부터 메세지가 도착하면 실행
   
   void afterConnectionEstablished(WebSocketSession session)
   - 클라이언트와 연결이 완료되고, 통신할 준비가 되면 실행

   void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
   - 클라이언트와 연결이 종료되면 실행

   void handleTransportError(WebSocketSession session, Throwable exception)
   - 메세지 전송중 에러가 발생하면 실행 


----------------------------------------------------------------------------------------------------

TextWebSocketHandler : WebSocketHandler 인터페이스를 상속받아 구현한 텍스트 메세지 전용 웹소켓 핸들러 클래스

   handlerTextMessage(WebSocketSession session, TextMessage message)
   - 클라이언트로부터 텍스트 메세지를 받았을때 실행
    
*/

