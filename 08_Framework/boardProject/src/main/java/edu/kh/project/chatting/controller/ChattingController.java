package edu.kh.project.chatting.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.project.chatting.dto.ChattingRoom;
import edu.kh.project.chatting.dto.Message;
import edu.kh.project.chatting.service.ChattingService;
import edu.kh.project.member.dto.Member;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("chatting")
@RequiredArgsConstructor
public class ChattingController {
	
	private final ChattingService service;
	
	// /chatting ==> 사용 주소
	/**
	 * 채팅 페이지 전환
	 * @return
	 */
	@GetMapping("")		// ==> 공통 주소만 있고 현재 나머지 주소가 없으므로 빈칸
	public String chattingPage(
		@SessionAttribute("loginMember") Member loginMember,
		Model model
	) {
		List<ChattingRoom> roomList = service.selectRoomList(loginMember.getMemberNo());
		
		model.addAttribute("roomList", roomList);
		
		return "chatting/chatting";
	}
	
	
	/**
	 * 채팅 상대 검색(본인 제외)
	 * @param query : 상대 닉네임 또는 이메일
	 * @param loginMember : 로그인한 회원 정보
	 * @return 검색 결과(list)
	 */
	@GetMapping("selectTarget")
	@ResponseBody
	public List<Member> selectTarget(
		@RequestParam("query") String query,
		@SessionAttribute("loginMember") Member loginMember
	) {
		
		return service.selectTarget(query, loginMember.getMemberNo());
	}
	
	
	/**
	 * 채팅방 입장(처음 채팅이면 채팅방 생성(INSERT)
	 * @param targetNo
	 * @param loginMember
	 * @return
	 */
	@ResponseBody
	@PostMapping("enter")
	public int chattingEnter(
		@RequestBody int targetNo,
		@SessionAttribute("loginMember") Member loginMember
	) {		
		int chattingNo = service.chattingEnter(targetNo, loginMember.getMemberNo());
		
		return chattingNo;
	}
	
	
	/**
	 * 로그인한 회원이 참여한 채팅방 목록 조회
	 * @param loginMember
	 * @return
	 */
	@GetMapping("roomList")
	@ResponseBody
	public List<ChattingRoom> selectRoomList(
		@SessionAttribute("loginMember") Member loginMember
	) {
		return service.selectRoomList(loginMember.getMemberNo());
	}
	
	
	/**
	 * 특정 채팅방의 메시지 모두 조회
	 * @param chattingNo
	 * @param loginMember
	 * @return
	 */
	@GetMapping("selectMessage")
	@ResponseBody
	public List<Message> selectMessage(
		@RequestParam("chattingNo") int chattingNo,
		@SessionAttribute("loginMember") Member loginMember
	) {
		return service.selectMessage(chattingNo, loginMember.getMemberNo());
	}
	
	
	/**
	 * 채팅 읽음 표시
	 * @param chattingNo
	 * @param loginMember
	 * @return
	 */
	@PutMapping("updateReadFlag")
	@ResponseBody
	public int updateReadFlag(
		@RequestBody int chattingNo,
		@SessionAttribute("loginMember") Member loginMember
	) {
		return service.updateReadFlag(chattingNo, loginMember.getMemberNo());
	}
	
}
