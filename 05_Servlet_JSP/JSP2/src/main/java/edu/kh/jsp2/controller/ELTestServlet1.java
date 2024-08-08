package edu.kh.jsp2.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/el/test1")
public class ELTestServlet1 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 요청 처리 */
		
		//----------------------------------------------------------------------------------------------------
		
		/* 응답 처리 */
		// ==> 원래는 Java에서 outWrite("<html>") 써야 하는데 너무 번거롭다
		// ==> JSP한테 응답 화면을 만들어 출력하라고 떠넘기기 (== 요청 위임(forward))
		
		// 1) JSP 경로 작성 (webapp 폴더 기준)
		String path = "/WEB-INF/views/el/test1.jsp";
		
		// 2) 지정된 JSP 경로로 요청을 넘겨줄 요청 발송자(RequestDispatcher) 객체 생성
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		
		// 3) 요청 위임하기
		dispatcher.forward(req, resp);
	}
}
