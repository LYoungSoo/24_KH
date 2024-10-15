package edu.kh.project.error.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller		// 컨트롤러 명시 + Bean 등록
public class CommonErrorController implements ErrorController {
	
	// ErrorController 인터페이스를 상속 받은 경우
	// 기존 Spring 에서 에러를 처리하던 코드(에러 출력 페이지(글자 많이나오던 페이지)로 forward) 를
	// 대체해서 동작함!!
	
	// [동작 순서]
	// @ControllerAdvice 에서 일치하는 예외 처리 메서드 찾기
	// ==> 없으면 ErrorController 구현 객체가 처리
	/**
	 * 공용 예외 처리 메서드
	 * @param modle
	 * @param req
	 * @return
	 */
	@RequestMapping("error")
	public String errorHandler(Model model, HttpServletRequest req) {
		
		// 응답 상태 코드 얻어오기
		Object status = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		int statusCode = Integer.parseInt(status.toString());
		
		// 에러 메시지 얻어오기
		Object message = req.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		String errorMessage = (message != null) ? message.toString() : "알 수 없는 오류 발생";
		
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("statusCode", statusCode);
		
		return "error/common-error";
	}

}
