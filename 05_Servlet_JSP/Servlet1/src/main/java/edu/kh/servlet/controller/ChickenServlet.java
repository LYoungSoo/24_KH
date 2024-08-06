package edu.kh.servlet.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/* [Servlet 용도의 클래스 생성 시 해야 할 것!!]
 * 1. HttpServlet 상속 받기
 * 
 * 2. @WebServlet("요청주소") 어노테이션 작성하기
 * 
 * 3. doGet() 또는 doPost() 오버라이딩 하기
 */

@WebServlet("/chicken")
public class ChickenServlet extends HttpServlet{
	
	// HttpServletRequest : 요청자 정보, 전달받은 데이터가 담긴 객체
	
	// HttpServletResponse : 응답 방법을 제공하는 객체
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 요청 시 제출된 값(파라미터) 모두 얻어오기
		
		// 1) 값이 하나인 경우 : String req.getParameter("name")
		String chicken = req.getParameter("chicken");	// 치킨
		String type = req.getParameter("type");			// 뼈 / 순살
		
		String ordererName = req.getParameter("ordererName");	// 주문자명
		String ordererAddress = req.getParameter("ordererAddress");	//주문자 주소
		
		// 2) 값이 여러 개인 경우
		// String[] req.getParameterValues("name");
		// ==> 제출된 값 중 name 속성 값이 일치하는 것을 모두 모아 하나의 "String[]"(배열) 로 반환
		// 단, name 속성 값이 일치하는게 없을 경우 "null" 반환. 빈 배열이 아니라!!
		String[] options = req.getParameterValues("opt");
		
		//----------------------------------------------------------------------------------------------------
		
		/* 선택한 치킨, 뼈/순살, 옵션에 따라 가격 책정하기 */
		
		int price = 0;
		
		switch(chicken) {
		case "후라이드"	 	: price += 10000; break;
		case "양념"			: price += 11000; break;
		case "마늘", "간장" : price += 12000; break;
		}
		
		
		// 순살인 경우 2000원 추가
		if(type.equals("boneless")) price += 2000;
		
		// 추가된 옵션이 있을 경우		
		if(options != null) {
			for(String opt : options) {
				switch(opt) {	// for문 다 돌리면서 switch 돌리는것이라 전혀 문제가 없이 체크박스 내용 다 적용된다.
				case "치킨무" : price += 500; break;
				case "콜라"   : price += 2000; break;
				case "치즈볼" : price += 3000; break;
				}
			}
		}
		
		//----------------------------------------------------------------------------------------------------
		
		/* 응답 HTML 만들어서 출력하기 */
		
		// 응답 데이터의 형식/문자 인코딩 지정
		resp.setContentType("text/html; charset=UTF-8");
		
		// 출력용 스트림 얻어오기
		PrintWriter out = resp.getWriter();
		
		// HTML 코드 모아둘 객체
		StringBuilder sb = new StringBuilder();
		
		sb.append("""
				<!DOCTYPE html>
				<html>
				<head>
					<meta charset='UTF-8'>
					<title>""");
		sb.append(ordererName + "님의 주문 영수증");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");
			
			sb.append("<h3> 주문자명 : " + ordererName + "</h3>");
			sb.append("<h3> 주소 : " + ordererAddress + "</h3>");
			
			sb.append("<ul>");
				sb.append("<li>치킨 : " + chicken + "</li>");
				sb.append("<li>뼈 / 순살 : " + (type.equals("bone") ? "뼈" : "순살") + "</li>");
				
				// 옵션이 있을 경우
				if(options != null) {
					sb.append("<li>선택한 옵션 : ");
					
					// String.join() : String[] ==> String 한 줄로 변환
					sb.append(String.join(" / ",options));	//	생성되는 구분 문자는 "/" 로 정했다!
					
					sb.append("</li>");
				}
				
			sb.append("</ul>");
			
			sb.append("<h3>금액 : " + price + "</h3>");
			
		sb.append("</body>");
		sb.append("</html>");
		out.write(sb.toString());	// HTML 클라이언트에게 출력하기
		
	}	// end doGet()

}
