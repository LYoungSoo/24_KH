package edu.kh.project.common.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/* Filter : 요청, 응답 시 걸러내거나 추가할 수 있는 객체
 * 
 * [필터 생성 및 적용 방법]
 * 1. jakarta.servlet.Filter 인터페이스를 상속 받은 클래스 생성
 * 2. 상속 후 doFilter() 메서드 오버라이딩
 * 3. FilterConfig 클래스에 생성한 필터 클래스를 등록
 */
public class LoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		// HtpServletRequest/Response 로 다운캐스팅
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		
		// Session 객체 얻어오기
		HttpSession session = req.getSession(); 
		
		// 로그인이 되어있지 않은 경우
		if(session.getAttribute("loginMember") == null) {
			resp.sendRedirect("/");		// 메인 페이지 리다이렉트
		} else {
			chain.doFilter(request,  response);		// 다음 필터로 이동
		}
		
	}
	
}
