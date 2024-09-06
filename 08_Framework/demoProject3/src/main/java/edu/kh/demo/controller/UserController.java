package edu.kh.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.demo.dto.UserDto;
import edu.kh.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j			// Simple Logging Facade for Java   // for... four... 4...
@Controller		// Controller 역할 명시 + bean 등록
@RequestMapping("user")		// /user 로 시작하는 요청 매핑
public class UserController {

	// 필드
	
	// 서비스 객체 DI (의존성 주입)
	@Autowired
	private UserService service;
	
	/**
	 * 사용자 번호를 입력 받아 일치하는 사용자의 이름 조회
	 * @param userNo : 제출된 파라미터 중 key 값이 "userNo" 인 값
	 * @param model  : Spring 에서 사용하는 데이터 전달용 객체(request)
	 * @return
	 */
	@GetMapping("test1")
	public String selectUserName (
		@RequestParam("userNo") int userNo, Model model
	) {
		
		// 사용자 이름 조회 서비스 호출 후 결과 반환 받기
		String userName = service.selectUserName(userNo);
		
		// 조회 결과를 model에 추가
		model.addAttribute("userName", userName);
		
		// classpath:/templates/user/searchName.html 요청 위임
		return "user/searchName";
	}
	
	/**
	 * 사용자 전체 조회
	 * @param model : 데이터 전달용 객체(request)
	 * @return user/selectAll.html forward
	 */
	@GetMapping("selectAll")
	public String selectAll(Model model) {
		
		// service : 의존성 주입(DI) 받은 UserServiceImpl Bean(객체) ==> new Service ~ 필요없음
		List<UserDto> userList = service.selectAll();
		
		model.addAttribute("userList", userList);
		
		return "user/selectAll";
	}
	
	
	
	/* RedirectAttributes
	 * - 리다이렉트 시 request scope 로 값을 전달할 수 있는 스프링 제공 객체
	 * 
	 * [원리]
	 * 
	 * 1) 값 세팅(request scope)
	 * 2) 리다이렉트 수행되려고 할 때, 1)에서 세팅된 값을 session scope로 잠깐(Flash) 대피
	 * 3) 리다이렉트 수행 수, session 에 대피했던 값을 다시 request scope 로 가져옴
	 */
	
	/**
	 * userNo가 일치하는 사용자 조회
	 * @param userNo : 주소에 작성된 사용자 번혼
	 * @param model  : 데이터 전달용 객체
	 * @param ra     : 
	 * @return
	 */
	@GetMapping("select/{userNo}")
	public String selectUser(
		@PathVariable("userNo") int userNo, Model model, RedirectAttributes ra
	) {
		
		UserDto user = service.selectUser(userNo);
		
		// 조회 결과가 있을 경우
		if(user != null) {
			model.addAttribute("user", user);
			return "user/selectUser";
		}
		
		// 조회 결과가 없을 경우
		
		// 리다이렉트 시 잠깐 session으로 대피할 값 세팅
		ra.addFlashAttribute("message", "사용자가 존재하지 않습니다");
		
		// 목록으로 redirect
		return "redirect:/user/selectAll";
	}
	
	/*
	 * @ModelAttribute
	 * - 전달된 파라미터의 key(name 속성) 값이
	 * 	 작성된 DTO의 필드명과 일치하면
	 * 	 DTO 객체의 필드에 자동으로 세팅하는 어노테이션
	 * 	 ==> 이렇게 만들어진 객체를 "커맨드 객체" 라고 함
	 */
	
	/**
	 * 사용자 정보 수정
	 * @param userNo : 주소에 포함된 userNo
	 * @param user	 : userNo, userPw, userName 포함된 커맨드 객체
	 * @param ra	 : 리다이렉트 시 request scope로 값 전달하는 객체
	 * @return
	 */
	@PostMapping("update/{userNo}")
	public String updateUser(
		@PathVariable("userNo") int userNo,
		@ModelAttribute UserDto user,
		RedirectAttributes ra
	) {
		
		log.debug("userNo : {}", userNo);
		log.debug("user   : {}", user);
		// UserDto user == 제출된 userPW, userName, @PathVariable("userNo")
		
		// DML(update, 등) 수행 결과 == 결과 행의 개수 == int
		int result = service.updateUser(user);
		
		// 수정 결과에 따라 메시지 지정
		String message = null;
		
		if(result > 0) message = "수정 성공!!!";
		else		   message = "수정 실패...";
		
		// ra에 값 추가(Flash! 찰칵! 번쩍! F점멸!)
		ra.addFlashAttribute("message", message);
		
		// 상세 조회 페이지 redirect
		return "redirect:/user/select/" + userNo;
	}
	
	
	/**
	 * 
	 * @param userNo : 주소에서 {userNo} 자리를 얻어와 저장한 변수
	 * @param ra	 : redirect 시 request scope로 값 전달하는 객체
	 * @return
	 */
	@PostMapping("delete/{userNo}")
	public String deleteUser(
		@PathVariable("userNo") int userNo,		// 주소의 일부분을 값처럼 쓰는것
		RedirectAttributes ra
	) {
		int result = service.deleteUser(userNo);
		
		// 삭제 여부에 따라 redirect 경로, 메시지 지정하기
		String path = null;
		String message = null;
		
		if (result > 0) {
			path = "redirect:/user/selectAll";	// 목록
			message = "삭제 되었습니다";
		} else {
			path = "redirect:/user/select/" + userNo;	// 상세
			message = "삭제 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
	/**
	 * 사용자 추가 화면으로 전환
	 * @return
	 */
	@GetMapping("insert")
	public String insertUser() {
		return "user/insertUser";
	}
	
	/**
	 * 사용자 추가
	 * @param user : 제출된 값이 필드에 담겨진 "커멘드 객체"
	 * @param ra   : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("insert")
	public String insertUser(
		@ModelAttribute UserDto user,
		RedirectAttributes ra
	) {
		int result = service.insertUser(user);
		
		// INSERT 수행 후
		// 성공 시 : {userId} 님이 추가 되었습니다 (메시지 전달)
		//			==> /user/selectAll 리다이렉트 
		// 실패 시 : 추가 실패 (메시지 전달)
		//			==> /user/insert 리다이렉트
		String path = null;
		String message = null;
		
		// 성공 여부에 따라 redirect 경로, 메시지 지정하기
		if (result > 0) {
			path = "redirect:/user/selectAll";
			message = user.getUserId() + " 님이 추가 되었습니다";
		} else {
			path = "redirect:/user/insert/";
			message = "추가 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return path;

	}
}
