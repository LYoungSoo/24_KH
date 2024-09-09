package edu.kh.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todolist.dto.Todo;
import edu.kh.todolist.service.TodoListService;
import lombok.extern.slf4j.Slf4j;

/* @RequestBody
 * - 비동기 요청(ajax) 시 전달되는 데이터 중
 *   body 부분에 포함된 요청 데이터를
 *   알맞은 Java 객체 타입으로 바인딩하는 어노테이션
 * 
 * (쉬운 설명)
 * - 비동기 요청 시 body에 담긴 값을
 *   알맞은 타입으로 변환해서 매개 변수에 저장
 */

/* @ResponseBody
 * - 컨트롤러 메서드의 반환 값을
 *   HTTP 응답 본문에 직접 바인딩하는 역할임을 명시
 *  
 * (쉬운 해석)  
 * -> 컨트롤러 메서드의 반환 값을
 *  비동기 (ajax)요청했던 
 *  HTML/JS 파일 부분에 값을 돌려 보낼 것이다를 명시
 *  
 *  - forward/redirect 로 인식 X
 */

@Slf4j
@Controller
@RequestMapping("todo")
public class TodoAjaxController {
	
	@Autowired
	private TodoListService service;
	
	/**
	 * 비동기로 할 일 추가
	 * @param todo : @RequestBody를 이용해서 전달 받은 JSON 형태(String) 의 데이터를 Todo 객체로 변환
	 * @return
	 */
	@ResponseBody
	@PostMapping("add")
	public int todoAdd(
		@RequestBody Todo todo
	) {
		log.debug("todo : {}", todo);
		
		// 서비스 호출 후 결과 반환 받기
		int result = service.todoAdd(todo);
		
		/* 비동기 통신의 목적 : "값" 또는 화면 일부만
		 * 갱신 없이 서버로 부터 응답 받고 싶을 때 사용 
		 */
		return result;	// service 수행 결과 그대로 반환
	}
	
	// ajax 기초 연습 - todoNo 일치하는 할 일의 제목 얻어오기
	
	/**
	 * 
	 * @param todoNo : GET 방식 요청은 body가 아닌 주소에 담겨 전달된 "파라미터!"
	 * 				   => @RequestParam 으로 얻어옴
	 * @return 검색된 제목
	 */
	@ResponseBody	// 비동기 요청한 JS 본문으로 값 반환(forward 아니고 값 반환! 의미의 어노테이션)
	@GetMapping("searchTitle")
	public String searchTitle(
		@RequestParam("todoNo") int todoNo
	) {
		
		String todoTitle = service.searchTitle(todoNo);
		
		// 서비스 결과를 "값: 형태 그대로 JS 본문으로 반환
		return todoTitle;
	}

}
