package edu.kh.jsp2.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


// 주소 제일 앞에 "/" 꼭 작성!!
// ==> 서블릿 매핑에서 유효하지 않은 <url-pattern> [el/scope] 오류 발생
@WebServlet("/el/scope")
public class ELTestServlet2 extends HttpServlet {
	
	// a태그 요청은 GET 방식이다. 클릭을 하면 페이지를 달라는 요청이 날라가서 그 페이지를 '받아'오는거니까
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/* 요청 처리 */
		
		// 1. page scope ==> JSP에서만 확인 가능
		
		// 2. request scope
		// - 요청 받은 Servlet/JSP와 요청이 위임(forward)된 Servlet/JSP 에서 유지되는 객체
		
		// * Servlet/JSP 범위 객체에
		//	a) 값(속성) 추가하는 방법 : 범위객체.setAttribute("key",value);
		//  b) 값(속성) 얻어오는 방법 : Object 범위객체.getAttribute("key");
		//								==> 반환형 Object ==> 필요 시 다운캐스팅(근데 안함)
		
		req.setAttribute("requestValue", "request scope 객체에 세팅한 값");
		
		System.out.println(req.getAttribute("requestValue"));
		
		//--------------------------------------------------
		
		// 3. session(입회, 접속) scope
		// - 클라이언트가 서버에 첫 요청 시 서버쪽에 생성되는 객체
		// - 클라이언트가 브라우저를 종료 하거나, 저장된 세션 만료 시간이 지나면 사라짐
		//		==> 위 상황이 아니면 계속 유지되는 객체
		
		//	a) session scope 객체 얻어오기
		HttpSession session = req.getSession();	// 요청자 서버 개체 이름을 얻어온?
		
		//	b) session scope 객체에 값 세팅
		session.setAttribute("sessionValue", "session scope 객체에 세팅한 값");
		
		/* session 만료 시키기 */
		session.setMaxInactiveInterval(1);		// 초 단위
		
		//--------------------------------------------------
		
		// 4. application scope
		// - 서버 전체에 1개만 존재하는 객체 ==> 모든 클라이언트가 공유
		// - 서버 시작 시 생성, 서버 종료 시 소멸

		//	a) application scope 객체 얻어오기
		ServletContext application = req.getServletContext();
		
		//	b) 값 세팅
		application.setAttribute("applicationValue","application scope 객체에 세팅한 값");
		
		//--------------------------------------------------
		
		// 5. 범위별 우선순위 확인
		// (좁은 범위가 우선순위가 높다!!)
		// page > request > sesstion > application
		
		// key 값을 동일하게 하여 범위별 객체에 값 추가
		req.setAttribute("menu", "짬뽕(request)");
		session.setAttribute("menu", "짜장(sesstion)");
		application.setAttribute("menu", "볶음밥(application)");;
		
		//====================================================================================================
		
		/* 응답 처리 */
		
		String path = "/WEB-INF/views/el/scope.jsp";
		
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		
		dispatcher.forward(req, resp);
		
	}

}
