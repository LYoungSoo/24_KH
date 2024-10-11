package edu.kh.project.board.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.kh.project.board.dto.Comment;
import edu.kh.project.board.service.CommentService;
import edu.kh.project.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@Controller		// 요청을 받고 응답 + Bean 으로 등록
@RestController		// @Controller + @ResponseBody
					// 비동기 요청 처리 전용 컨트롤러 ==> return 되는 모든 값을 있는 그대로 호출부로 반환
@RequiredArgsConstructor
@Slf4j
public class CommentController {

	private final CommentService service;
	
	/**
	 * 댓글 등록
	 * @param comment : 요청 시 body에 JSON 형태로 담겨져 제출된 데이터를
	 * 					HttpMessageConverter 가 DTO로 변환한 객체
	 * 					(boardNo, commentContent, parentCommentNo)
	 * @param loginMember : 로그인한 회원 정보
	 * @return commentNo : 삽입된 댓글 번호
	 */
//	@ResponseBody				// @RestController 로 인해 작성할 필요가 없음
	@PostMapping("comment")		// POST == CREATE / INSERT 의미
	public int commentInsert(
		@RequestBody Comment comment,
		@SessionAttribute("loginMember") Member loginMember
//		HttpMessageConverter :전달된 자료형을 java에 맞게 바꿔주거나 전달할 자료를 전달받는 곳에 맞게 자동으로 바꿔줌
	) {
		// 로그인된 회원 번호를 comment에 세팅
		comment.setMemberNo(loginMember.getMemberNo());
		
		return service.commentInsert(comment);
	}
	
	/**
	 * 댓글 수정
	 * @return
	 */
//	@ResponseBody
	@PutMapping("comment")		// PUT == UPDATE 의미
	public int commentUpdate() {
		return 0;
	}
	
	/**
	 * 댓글 삭제
	 * @param commentNo : 삭제하려는 댓글 번호
	 * @param member    : 로그인한 회원 번호
	 * @return result
	 */
//	@ResponseBody
	@DeleteMapping("comment")	// DELETE == DELETE 의미
	public int commentDelete(
		@RequestBody int commentNo,
		@SessionAttribute("loginMember") Member loginMember
	) {
		
		
		return service.commentDelete(commentNo, loginMember.getMemberNo());
	}
	
}
