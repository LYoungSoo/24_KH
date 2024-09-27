package edu.kh.project.myPage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.dto.Member;
import edu.kh.project.myPage.service.MyPageService;

// @SessionAttribute"s" 용도
// 1. Model을 이용하여 값을 request -> session으로 scope 변경
// 2. @SessionAttribute""(뒤에 s 없음) 를 이용해
//	  @SessionAttribute"s"에 의해서 session에 등록된 값을 얻어올 수 있음

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
public class MyPageController {
	
	@Autowired	// DI
	private MyPageService service;
	
	/**
	 * 마이페이지(내 정보) 전환
	 * @param loginMember
	 * @param model
	 * @return model : 데이터 전달용 객체
	 */
	@GetMapping("info")
	public String info(
		@SessionAttribute("loginMember") Member loginMember,
		Model model
	) {
		// 로그인 회원 정보에 주소가 있을 경우
		if(loginMember.getMemberAddress() != null) {
			// 주소를 , 기준으로 쪼개서 String[] 형태로 반환
			String[] arr = loginMember.getMemberAddress().split(",");
			// 		"04540,서울 중구 남대문로 120,2층"
			// ==> {"04540", "서울 중구 남대문로 120", "2층"}
			
			model.addAttribute("postcode",		arr[0]);
			model.addAttribute("address",		arr[1]);
			model.addAttribute("detailAddress",	arr[2]);
						
		}
		return "myPage/myPage-info";
	}
	
	/**
	 * 내 정보 수정
	 * @param inputMember : 수정할 닉네임, 전화번호, 주소
	 * @param loginMember : 현재 로그인된 회원 정보
	 * 						session에 저장된 Member 객체의 주소가 반환됨
	 * 					==	session에 저장된 Member 객체의 데이터를 수정할 수 있음
	 * @return
	 */
	@PostMapping("info")
	public String updateInfo(
		@ModelAttribute Member inputMember,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra
	) {
		// @SessionAttribute("key")
		// - @SessionAttribute"s" 를 통해 session에 올라온 값을 얻어오는 어노테이션
		
		// - 사용 방법
		// 1) 클래스 위에 @SessionAttribute"s" 어노테이션을 작성하고
		//    해당 클래스에서 꺼내서 사용할 값의 key를 작성
		//	  ==> 그럼 세션에서 값을 미리 얻어와 놓음
		
		// 2) 필요한 메서드 매개 변수에
		//	  @SessionAttribute("key")를 작성하면
		//    해당 key 와 일치하는 session 값을 얻어와서 대입
		
		// 1. inputMember에 로그인된 회원 번호를 추가
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo); // 회원번호, 닉네임, 전화번호, 주소
		
		// 2. 회원 정보 수정 서비스 호출 후 결과 반환 받기
		int result = service.updateInfo(inputMember);
		
		// 3. 수정 결과에 따라 message 지정
		String message = null;
		if(result > 0) {
			
		// 4. 수정 성공 시 session 저장된 로그인 회원 정보를
		//	  수정 값으로 변경해서 DB와 같은 데이터를 가지게 함 == 동기화
			loginMember.setMemberNickname(inputMember.getMemberNickname());
			loginMember.setMemberTel(inputMember.getMemberTel());
			loginMember.setMemberAddress(inputMember.getMemberAddress());
			
			message = "수정 성공!!!";
		}
		else			message = "수정 실패...";
		
		ra.addFlashAttribute("message", message);
		// ==> footer.html 조각에서 alert() 수행
		
		
		
		return "redirect:info";		// /mypage/info GET 방식 요청
	}
	
	/**
	 * (비동기) 닉네임 중복 검사
	 * @param input
	 * @return 0 : 중복 X / 1 : 중복 O
	 */
	@ResponseBody	// 응답 본문(ajax 코드)에 값 그대로 반환  
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("input") String input) {
		return service.checkNickname(input);
	}
	
	/**
	 * 비밀번호 변경 화면 전환
	 * @return
	 */
	@GetMapping("changePw")
	public String changePw() {
		
		// 접두사 : classpath:/templates/
		// 접미사 : .html
		return "myPage/myPage-changePw";
	}
	
	/**
	 * 비밀번호 변경 수행
	 * @param currentPw	  : 현재 비밀번호
	 * @param newPw		  : 변경하려는 새 비밀번호
	 * @param loginMember : 세션에서 얻어온 로그인한 회원 정보
	 * @param ra 		  : 리다이렉트 시 request scope로 데이터 전달하는 객체
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(
		@RequestParam("currentPw") String currentPw,
		@RequestParam("newPw") String newPw,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra
	){
		// 서비스 호출 후 결과 반환 박시
		int result = service.changePw(currentPw, newPw, loginMember);
		
		String message = null;
		String path = null;
		
		// 결과에 따른 응답 제어
		if(result > 0) {
			message = "비밀번호가 변경 되었습니다";
			path = "info";	// 내 정보 페이지로 리다이렉트
		} else {	// 에러(예외) 발생이 아닌 실패할 수 있는 경우의 수는 하나
			message = "현재 비밀번호가 일치하지 않습니다";
			path = "changePw";	// 비밀번호 변경 페이지로 리다이렉트
		}
		
		ra.addFlashAttribute("message", message);
		
		// 현재 컨트롤러 메서드 매핑 주소 : /myPage/changePw (POST)
		// 리다이렉트 주소 : /myPage/info , /myPage/changePw (GET)
		// 주소 맨 앞에 "/" 가 없으면 상대경로 기준
		return "redirect:" + path;
	}
	
	/**
	 * 회원 탈퇴 페이지로 전환
	 * @return
	 */
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	
	/**
	 * 회원 탈퇴 수행
	 * @param memberPw	  : 입력된 비밀번호
	 * @param loginMember : 로그인한 회원 정보(session)
	 * @param ra		  : 리다이렉트 시 request scope 데이터 전달
	 * @param status	  : @SessionAttributes 로 관리되는 세션 데이터의 상태 제어(세션 만료)
	 * @return
	 */
	@PostMapping("secession")
	public String secession(
		@RequestParam("memberPw") String memberPw,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra,
		SessionStatus status
	) {
		
		// 서비스 호출 후 결과 반환 받기
		int result = service.secession(memberPw, loginMember);
		
		String message = null;
		String path = null;
		
		if(result > 0) {
			message = "탈퇴 되었습니다"; 
			path = "/";		// 메인 페이지 리다이렉트(root)
			status.setComplete();	// 세션 만료 ==> 로그아웃
		} else {
			message = "비밀번호가 일치하지 않습니다";
			path = "secession";		// 탈퇴 페이지 리다이렉트
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/**
	 * 프로필 수정 페이지 전환
	 * @return
	 */
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	
	/**
	 * 로그인한 회원의 프로필 이미지 수정
	 * @param profileImg  : 재출된 이미지
	 * @param loginMember : 로그인한 회원 정보
	 * @param ra		  : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("profile")
	public String profile(
		@RequestParam("profileImg") MultipartFile profileImg,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra
	) {
		
		// 1) 로그인한 회원의 회원 번호 얻어오기
		int memberNo = loginMember.getMemberNo();
		
		// 2) 업로드된 이미지로 프로필 이미지 변경하는 서비스 호출
		String filePath = service.profile(profileImg, memberNo);
		
		// 3) 응답 처리
		String message = null;
		
//		if(filePath != null) {
			message = "프로필 이미지가 변경되었습니다";
			
			// DB, Session 에 저장된 프로필 이미지 정보 동기화
			loginMember.setProfileImg(filePath);
			
//		} else {
//			message = "변경 실패";
//		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:profile";		// myPage/profile(GET)
	}

}

/*
 * @RequestParam
 * - 요청 시 제출된 데이터(쿼리스트링, input)를 얻어와
 *   매개변수에 저장하는 어노테이션
 *   
 * @RequestMapping
 * - 요청 주소에 따라서
 *   알맞은 컨트롤러 클래스 / 메서드에 연결하는 어노테이션
 *   
 * @RequestBody
 * - 비동기 요청 시 body 에 담겨져 전달되는 데이터를
 *   매개변수에 저장하는 어노테이션
 * 
 * @ResponseBody
 * - forward 하지않고, 비동기 요청 코드(응답 본문)에
 *   컨트롤러 반환 값을 그대로 전달하는 어노테이션
 * 
 */
