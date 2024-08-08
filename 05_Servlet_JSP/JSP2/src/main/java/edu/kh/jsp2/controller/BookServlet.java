package edu.kh.jsp2.controller;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import edu.kh.jsp2.dto.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/book/list")
public class BookServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Book으로 타입이 제환된 List 생성
		List<Book> bookList = new ArrayList<Book>();
		
		// bookList에 샘플 데이터 추가
		bookList.add(new Book("Java 공부"   , "백동현", 10000));
		bookList.add(new Book("HTML 공부"   , "고현우", 15000));
		bookList.add(new Book("JS 공부"     , "성상민", 20000));
		bookList.add(new Book("CSS 공부"    , "신짱구", 25000));
		bookList.add(new Book("JSP 공부"    , "노진구", 30000));
		bookList.add(new Book("Servlet이란" , "교수님", 35000));
		bookList.add(new Book("Unity 공부"  , "강사님", 40000));
		bookList.add(new Book("OracleDB"    , "누구지", 45000));
		bookList.add(new Book("Spring이란"  , "졸려요", 50000));
		
		// bookList를 요청 위임된 JSP에서도 유지되어 사용할 수 있도록 request scope 객체에 속성으로 추가(세팅)
		req.setAttribute("bookList", bookList);
		
		// JSP로 요청 위임
		String path = "/WEB-INF/views/book/bookList.jsp";
		
		req.getRequestDispatcher(path).forward(req, resp);
		// ServletRequest 객체에 있는 path에 존재하는 jsp에 request, response를 떠넘긴다!
	}
}