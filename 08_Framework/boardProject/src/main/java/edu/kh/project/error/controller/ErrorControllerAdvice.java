package edu.kh.project.error.controller;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/*
 * @ControllerAdvice 어노테이션
 * - 프로젝트 전역(global)에서 발생하는 예외를 처리하는 컨트롤러임을 명시 + Bean 등록
 * 
 * @ResponseStatus(응답상태코드)
 * - 응답 시 응답 상태 코드를 지정하는 어노테이션
 * 
 * - HttpStatus.INTERNAL_SERVER_ERROR : 응답 상태 코드 500
 */
@ControllerAdvice
public class ErrorControllerAdvice {
	// JDBC - Mybatis 최상위 예외 - SQLException 보다 상위
	
	/**
	 * DB(SQL) 관련 예외 처리 메서드
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String dataAccessExceptionHandler(
		DataAccessException e, Model model
	) {
		e.printStackTrace();		// 콘솔에 자세한 예외 내용 출력
		
		 model.addAttribute("errorMessage", "DB(SQL) 관련 오류 발생");
		 model.addAttribute("e", e);
		 model.addAttribute("stackTrace", getStackTraceAsString(e));
		 
		 return "error/500";
	}
	
	/**
	 * 파라미터가 없을 때를 처리하는 메서드
	 * 비 로그인 문제는 파라미터 전달이 아니라 세션어트리뷰트로 전달한 값이라서 안나오는게 맞음
	 * @param e
	 * @param model
	 * @return
	 */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e, Model model) {
		e.printStackTrace();
		model.addAttribute("errorMessage", "필수 파라미터 '" + e.getParameterName() + "'가 누락되었습니다.");
		return "error/400"; // 400 에러 페이지로 이동
	}

	/**
	 * 파라미터의 자료형이 일치하지 않을 때 처리하는 메서드
	 * @param e
	 * @param model
	 * @return
	 */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException e, Model model) {
		e.printStackTrace();
		model.addAttribute("errorMessage", "파라미터 '" + e.getName() + "'의 타입이 일치하지 않습니다. " +
				"제출된 값: '" + e.getValue() + "' (매개 변수 타입: " + e.getRequiredType().getSimpleName() + ")");
		return "error/400"; // 400 에러 페이지로 이동
	}
	
	/**
	 * 요청 주소를 찾을 수 없을 경우 처리하는 메서드(404)
	 * @return
	 */
	@ExceptionHandler(NoResourceFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String notFoundHandler() {
		return "Error/404";
	}
	
	// 예외의 스택 트레이스를 문자열로 변환하는 메서드
	private String getStackTraceAsString(Exception e) {
		StringBuilder sb = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			String str = element.toString();
			str = str.replaceFirst("\\(([^)]+)\\)", "<span class='highlight'>($1)</span>");
			sb.append("<p>").append(str).append("</p>"); // 각 스택 트레이스를 구분
		}
		return sb.toString();
	}
	
	

}
