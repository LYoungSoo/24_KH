package edu.kh.servlet2.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 해당 클래스를 Servlet으로 등록 + "/login 요청 주소 처리하도록 매핑
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	// "/login" POST 방식 요청을 처리하는 메서드
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 요청 시 제출된 값(파라미터) 모두 얻어오기
		String inputId = req.getParameter("inputId");
		String inputPw = req.getParameter("inputPw");
		String check = req.getParameter("check");
		
		System.out.println(inputId);
		System.out.println(inputPw);
		System.out.println(check);
		
		// ----------------------------------------------------------------------------------------------------
		
		// 아이디 	: user01
		// 비밀번호 : pass01!
		// check 	: 헬로 월드
		
		// 모두 일치 하는 경우 ==> "로그인 성공"
		// 불일치한 내용이 있을 경우
		// ==> OOO이 일치하지 않습니다 (OOO은 ID, PW, CHECK)
		
		String result = "";	// 결과 문자열 저장
		
		if(inputId.equals("user01") && inputPw.equals("pass01!") && check.equals("헬로 월드")) {
			result = "<h1 style='color:red;'>로그인 성공</h1>";
		} else {
			if(!inputId.equals("user01")) {
				result += "<h3>ID가 일치하지 않습니다</h3>";
			}
			if(!inputPw.equals("pass01!")) {
				result += "<h3>PW가 일치하지 않습니다</h3>";
			}
			if(!check.equals("헬로 월드")) {
				result += "<h3>CHECK가 일치하지 않습니다</h3>";
			}
		}
		
		// ----------------------------------------------------------------------------------------------------
		
		// 응답하기
		
		// 응답되는 문서의 형식, 문자 인코딩 지정
		resp.setContentType("text/html; charset-utf-8");
		
		// 클라이언트와 연결된 출력용 스트림 얻어오기
		PrintWriter out = resp.getWriter();
		
		// 출력할 HTML 작성하기
		StringBuilder sb = new StringBuilder();
		
		sb.append("""
				<!DOCTYPE html>
				<html>
				<head>
					<title>로그인 결과 페이지</title>
				</head>
				<body> """);
		sb.append(result);
		sb.append("""		
				</body>
				</html>
				""");
		out.write(sb.toString());	// 클라이언트에게 출력	// == 서버 응답
	}


}
