package edu.kh.project.email.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.common.util.RedisUtil;
import edu.kh.project.email.service.EmailService;

@Controller
@RequestMapping("email")
public class EmailController {
	
	@Autowired	// 의존성 주입
	public RedisUtil redisUtil;
	
	@Autowired	// 의존성 주입
	public EmailService service;
	
	// 레디스 확인하기
	@ResponseBody
	@GetMapping("redisTest")
	public int redisTest(
		@RequestParam("key") String key,
		@RequestParam("value") String value
	) {
		
		// 전달 받은 key, value를 redis에 set 하기
		redisUtil.setValue(key, value, 20);		// 20초 후에 만료
		// http://localhost/email/redisTest?key=hello&value=world
		
		return 1;
	}
	
	/**
	 * 인증번호 발송
	 * @param email : 입력된 이메일
	 * @return 성공1, 실패 0
	 */
	@ResponseBody
	@PostMapping("sendAuthKey")
	public int sendAuthKey(
		@RequestBody String email
	) {
		return service.sendEmail("signUp", email);
	}
	
	// HttpMessageConverter : JSON => DTO, MAP 으로 변환 가능하게 해주는
	/**
	 * 인증 번호 확인
	 * @param map : 입력 받은 email, authKey 가 저장된 map
	 * 				HttpMessageConverter 에 의해 JSON ==> Map 자동 변환
	 * @return
	 */
	@ResponseBody
	@PostMapping ("checkAuthKey")
	public boolean checkAuthKey(
		@RequestBody Map<String, String> map
	) {
		return service.checkAuthKey(map);
	}
	

}

/*
 * 1. 이메일이 중복이 아닐 경우에만 인증 번호 받기 버튼을 클릭해서 비동기로 서버가 작성된 이메일로 인증 코드를 발송
 *    + Redis에 key = 이메일, value = 인증코드를 5분후에 만료되도록 저장
 * 
 * 2. 인증 번호를 입력하고 인증하기 버튼을 클릭한 경우
 *    1) 인증 번호 6자리가 입력이 되었는지 확인
 *    
 *    2) 입력된 이메일과 인증 번호를 비동기로 서버에 전달 하여
 *       Redis 에 저장된 이메일, 인증번호와 일치하는지 확인
 *       
 *    3) 일치하지 않는 경우 alert("인증 번호가 일치하지 않습니다")
 *    
 *    4) 일치하는 경우
 *       - 타이머 멈춤
 *       + "인증 되었습니다" 화면에 초록색으로 출력
 *       + 
 *   
 */
