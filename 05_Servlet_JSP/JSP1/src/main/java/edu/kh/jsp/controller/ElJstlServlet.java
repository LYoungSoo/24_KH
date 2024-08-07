package edu.kh.jsp.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/el_jstl")		//<a href="/el_jstl">
public class ElJstlServlet extends HttpServlet{

	// GET 방식 요청 시 지정된 JSP로 바로 요청 위임
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// <a> (a태그) 요청 방식은 GET 방식이기 때문에
		
		// 1) JSP 경로	(webapp 폴더 기준)
		String path = "/WEB-INF/views/el_jstl.jsp";
		
		// 2) 요청 발송자 얻어오기
		RequestDispatcher dispatcher = req.getRequestDispatcher(path);
		
		// 3) 요청 위임(forward)
		dispatcher.forward(req, resp);
		
		
	}
}