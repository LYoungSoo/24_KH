package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/*
 * [Interceptor]
 * 
 * - 요청 / 응답을 가로채는 객체 (Spring 지원)
 * 
 * Client <-> filter <-> Dispatcher Servlet <-> Interceptor <-> Controller ....
 * 
 * - HandlerInterCeptor 인터페이스 상속 필요
 * - 제공 메서드 중 필요한 메서드 오버라이딩
 * 
 * 1) preHandle() : 전처리
 *      Dispatcher Servlet ==> Controller 사이에서 요청/응답을 가로채서 수행
 * 
 * 2) postHandle() : 후처리
 *      Controller ==> Dispatcher Servlet 사이에서 요청/응답을 가로채서 수행
 * 
 * 3) afterCompletion() : 완성 후
 *      View Resolver ==> Dispatcher Servlet 사이 수행
 */
// 클라이언트 요청마다 DB에서 게시판 조회해서 반환하면 서버 부담이 크니까
// 서버 시작 후 처음 페이지 요청 시 - application scope에 게시판 결과를 저장하고 그걸 계속 넘겨주기
// ==> 그래서 서버를 껐다가 켜야 게시판 목록 바뀐게 적용이 됨
// ===> 어떤 종류의 요청이 와도 BoardTypeList를 요청해서 가져올 수 있음 
// ====> 요청/응답 을 가로채서 데이터를 변경할 수 있음

@Slf4j
public class BoardTypeInterceptor implements HandlerInterceptor {
	
	@Autowired	// 의존성 주입
	private BoardService service;
	
	// 전처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)
			throws Exception {
		
//		log.info("----- BoardTypeInterceptor 전처리 메서드 실행 -----");
		// 어떤 요청이 와도
		// header에 출력되는 게시판 메뉴를 DB에서 조회해, Application Scpoe에 세팅
		
		// 1) application scope 객체 얻어오기
		ServletContext application = request.getServletContext(); // 뭔가 그럴싸한 함수명
		
		// 2) application 객체에 "boardTypeList" 가 없을 경우
		if(application.getAttribute("boardTypeList") == null) {
			log.info("----- boardTYpeList 조회 -----");
			
			// DB에서 모든 게시판 종류를 조회하는 서비스 호출
			List<Map<String, String>> boardTypeList = service.selectBoardTypeList();
			
			log.debug(boardTypeList.toString());
			
			// 조회 결과를 application 객체에 세팅
			application.setAttribute("boardTypeList", boardTypeList);
		}
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	// 후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	// view 완성 후
	@Override	// 타임리프에서 예외 많이 발생해서 exception 이 기본적으로 있음
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
