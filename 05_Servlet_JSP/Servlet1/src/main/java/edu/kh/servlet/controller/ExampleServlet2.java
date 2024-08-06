package edu.kh.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* [Servlet 용도의 클래스 생성 시 해야 할 것!!]
 * 
 * 1. HttpServlet 상속 받기
 * 
 * 2. @WebServlet("요청주소") 어노테이션 작성하기
 * 
 * 3. doGet() 또는 doPost() 오버라이딩 하기 
 */

// 어노테이션(Annotation) : 컴파일러가 읽고 해석하는 주석

// @WebServlet("요청주소") 어노테이션
// - 컴파일 시 web.xml 파일에 해당 클래스를 Servlet으로 등록하는 <servlet> 태그 작성하고
//	 "요청 주소"와 매핑되도록 <servlet-mapping> 태그 작성하게 함

// *** 주의사항 *** : 요청 주소는 반드시 "/" 부터 시작해야 한다!!
@WebServlet("/ex2")		// 이거 빼먹으면 안켜진다는데 난 왜 처음보는거냐 다른방식이 있나본데?
public class ExampleServlet2 extends HttpServlet{
	
	// "/ex2" GET 방식 요청 처리 메서드
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 제출된 파라미터를 모두 얻어와 변수에 저장	==> input 태그의 name을 보면됨
		String input1 = req.getParameter("input1");
		String input2 = req.getParameter("input2");
		String op 	  = req.getParameter("op");
		
		// input 1/2 정수로 변환
		int num1 = Integer.parseInt(input1);
		int num2 = Integer.parseInt(input2);
		
		// op에 따라 연산하기
		int result = 0;
		String operator = null;
		switch(op) {
		case "plus"  : result = num1 + num2; operator = "+" ; break;
		case "minus" : result = num1 - num2; operator = "-" ; break;
		case "multi" : result = num1 * num2; operator = "*" ; break;
		case "div"   : result = num1 / num2; operator = "/" ; break;
		case "mod" 	 : result = num1 % num2; operator = "%" ; break;
		}
		
//----------------------------------------------------------------------------------------------------		
		
		// 응답 페이지 만들기
		
		// 1) 문서 형식/문자 인코딩 지정
		resp.setContentType("text/html; charset=UTF-8");
		
		// 2) 출력용 스트림 얻어오기
		PrintWriter out = resp.getWriter();
		
		// 3) HTML 코드를 작성해서 출력하기
		StringBuilder sb = new StringBuilder();
		
		sb.append("""
			<!DOCTYPE html>
			<html>
			<head>
				<title> 연산 결과 페이지 </title>
			</head>
			<body>""");
		
		// <h1>
		// 	5 + 6 = 11
		// <h1>
		
		sb.append("<h1>");	
		sb.append(String.format("%d %s %d = %d", num1, operator, num2, result));
		sb.append("</h1>");
		sb.append("<button onclick='history.back()'>돌아가기</button>");
		
		sb.append("""
			</body>
			</html>
				""");
		
		out.write(sb.toString()); // 스트림으로 출력
		
	}
}
