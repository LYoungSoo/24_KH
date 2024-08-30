package edu.kh.jdbc.controller;

import java.io.IOException;

import edu.kh.jdbc.service.UserService;
import edu.kh.jdbc.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		try {
			// 파라미터 얻어오기
			int userNo = Integer.parseInt(req.getParameter("userNo"));
			
			// 서비스 호출 후 결과 반환 받기
			UserService service = new UserServiceImpl();
			
			int result = service.deleteUser(userNo);
			
			// 삭제 결과에 따라 출력 메시지 지정
			String message = null;
			
			if(result > 0) message = "삭제 되었습니다";
			else		   message = "해당 사용자가 존재하지 않습니다";
			
			// session scope 에 message 세팅
			req.getSession().setAttribute("message", message);
			
			// 사용자 목록(/selectAll) 리다이렉트
			resp.sendRedirect("/selectAll");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
