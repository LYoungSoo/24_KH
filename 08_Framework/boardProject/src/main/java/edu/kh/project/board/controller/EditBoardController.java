package edu.kh.project.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.service.BoardService;
import edu.kh.project.board.service.EditBoardService;
import edu.kh.project.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("editBoard")
@Slf4j
public class EditBoardController {
	
	private final EditBoardService service;
	
	// 수정 시 상세 조회 서비스 호출을 위한 객체 의존성 주입
	private final BoardService boardService;
	
	/*
	 * @PathVariable 사용 시 정규 표현식 적용 가능!!
	 * {변수명:정규표현식}
	 */
	
	/**
	 * 게시글 작성 화면 전환
	 * {boardCode:[0-9]+} : boardCode는 숫자 1글자 이상만 가능
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(
		@PathVariable("boardCode") int boardCode
		// @PathVariable 지정 시 forward 한 html 파일에서도 사용 가능!
	) {
		
		return "board/boardWrite";
	}
	
	/**
	 * 게시글 등록
	 * @param boardCode : 게시판 종류 번호
	 * @param inputBoard : 제출된 값의 key 값이 일치하는 필드에 값이 저장된 객체 (커맨드 객체)
	 * @param loginMember : 로그인한 회원 정보 (글쓴이 회원 번호 필요)
	 * @param images : 제출된 file 타입 input 태그 데이터
	 * @param ra : 리다이렉트 시 request scope로 값 전달
	 * @return
	 */
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(
		@PathVariable("boardCode") int boardCode,	// 주소에 있는 값 얻어오는거
		@ModelAttribute Board inputBoard,
		@SessionAttribute("loginMember") Member loginMember,
		@RequestParam("images") List<MultipartFile> images,	// 요청 파라미터를 얻어와서 지정한 변수에 저장할 수 있는
		RedirectAttributes ra
	) {
		
		/* 전달된 파라미터 확인 (debug 모드)
		 * 제목, 내용, boardCode ==> inputBoard 커멘드 객체
		 * 
		 * List<MultipartFile> images 의 크기(size()) == 제출된 file 타입의 input 태그 개수 == 5개
		 * 
		 * * 선택된 파일이 없더라도 빈칸으로 제출이 된다!!!
		 * ex) 0, 2, 4 인덱스만 선택 ==> 0, 2, 4 는 제출된 파일이 있고 1, 3 은 빈칸으로 존재
		 * 
		 *  */
		
		// 1) 작성자 회원 번호를 inputBoard에 세팅
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		// 2) 서비스 호출 후 결과(작성된 게시글 번호) 반환 받기
		int boardNo = service.boardInsert(inputBoard, images);
		
		// 3) 서비스 결과에 따라 응답 제어
		String path = null;
		String message = null;
		
		if(boardNo == 0) {	// 실패
			path = "insert";
			message = "게시글 작성 실패";
		} else {
			path = "/board/" + boardCode + "/" + boardNo;	// 상세 조회 주소
//			path = "/board/" + boardCode;	// 목록 조회 주소(임시) - 상세 주소는 다음시간에!
			message = "게시글이 작성 되었습니다";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/** 게시글 삭제
	 *  - DB에서 boardNo, memberNo 가 일치하는
	 *    "BOARD" TABLE 의 행의 BOARD_DEL_FL 컬럼 값을 'Y'로 변경 
	 * @param boardNo
	 * @param loginMember
	 * @param ra
	 * @param referer : 현재 컨트롤러 메서드를 요청한 페이지 주소
	 * 					(이전 페이지 주소 == 상세 조회 페이지)
	 * @return
	 *  - 삭제 성공 시 : "삭제 되었습니다" 메시지 전달
	 *                   + 해당 게시판 목록으로 redirect
	 *                   
	 *  - 삭제 실패 시 : "삭제 실패" 메시지 전달
	 *                   + 삭제 하려던 게시글 상세조회 페이지 redirect
	 */
	@PostMapping("delete")
	public String boardDelete(
		@RequestParam("boardNo") int boardNo,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra,
		@RequestHeader("referer") String referer  	// referer: http://localhost/board/1/2020
	) {
/*
		// 1) 반환 주소 방법 1 ==> split
		String[] arr = referer.split("/");
		// http: /   /  localhost  /  board  /  1  /  2020
		//   0   / 1 /      2      /    3    /  4  /   5
		String result1 = "/" + arr[3] + "/" + arr[4];
		
		// 2) 반환 주소 방법 2 ==> subString
		int start = referer.indexOf("/board");
		int end   = referer.lastIndexOf("/");
		String result2 = referer.substring(start, end);
		
		// 3) 반환 주소 방법 3 ==> 정규 표현식
		String regExp = "/board/[0-9]+";		// "/board/" 뒤 숫자를 제외한 다른 문자가 들어오기 전까지 숫자들
		
		// 정규식이 적용된 자바 객체
		Pattern pattern = Pattern.compile(regExp);
		
		// input에서 정규식과 일치하는 부분을 찾아 저장하는 객체
		Matcher matcher = pattern.matcher(referer);		// import java.util.regex.Matcher;
														// import java.util.regex.Pattern;
		if(matcher.find()) {					// 일치하는 부분을 찾은 경우
			String result3 = matcher.group();
//			System.out.println(result3);		// "/board/2"
		}
*/
		String[] arr = referer.split("/");
		

		// 2) 서비스 호출 후 결과(작성된 게시글 번호) 반환 받기
		int confirm = service.boardDelete(loginMember.getMemberNo(), boardNo);
		
		// 3) 서비스 결과에 따라 응답 제어
		String path = null;
		String message = null;
		
		if(confirm == 0) {	// 실패
			path = "referer";
			message = "삭제 실패";
		} else {
			path = "/" + arr[3] + "/" + arr[4];
			message = "게시글이 삭제 되었습니다";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/**
	 * 게시글 수정 화면 전환
	 * @param boardCode	  : 게시판 종류
	 * @param boardNo     : 수정할 게시글 번호
	 * @param loginMember : 로그인한 회원 정보(session)
	 * @param ra          : redirect 시 request scope로 데이터 전달
	 * @param model       : forward 시 request scope로 데이터 전달
	 * @return
	 */
	@PostMapping("{boardCode}/{boardNo}/updateView")
	public String updateView(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo")   int boardNo,
		@SessionAttribute("loginMember") Member loginMember,
		RedirectAttributes ra,
		Model model
	) {
		// boardCode, boardNo 가 일치하는 글 조회
		Map<String, Integer> map = Map.of("boardCode", boardCode, "boardNo", boardNo);
		
		// boardCode, boardNo 가 일치하는 글 조회
		Board board = boardService.selectDetail(map);
		
		// 게시글이 존재하지 않는 경우 ==> 주소창 건드리기 같은 경우
		if(board == null) {
			ra.addFlashAttribute("message", "해당 게시글이 존재하지 않습니다.");
			return "redirect:/board/" + boardCode;
		}
		
		// 게시글 작성자가 로그인한 회원이 아닌 경우
		if(board.getMemberNo() != loginMember.getMemberNo()) {
			ra.addFlashAttribute("message", "글 작성자만 수정 가능합니다");
			
			return String.format("redirect:/board/%d/%d", boardCode, boardNo);		// 상세 조회
		}
		
		// 게시글이 존재하고
		// 로그인한 회원이 작성한 글이 맞을 경우
		// 수정 화면으로 forward
		model.addAttribute("board", board);
		return "board/boardUpdate";
	}
	
	
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo")   int boardNo,
		@ModelAttribute Board inputBoard,		// 제출된 파라미터 중에서 name 속성값이 같으면 저장하는 커멘드 객체
		@SessionAttribute("loginMember") Member loginMember,	// 접속자가 수정자(작성자)가 맞는지
		@RequestParam("images") List<MultipartFile> images,
		@RequestParam(value="deleteOrderList", required = false) String deleteOrderList,
		RedirectAttributes ra
	) {
		// 1. 커멘드 객체 inputBoard 에 로그인한 회원 번호 추가
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		// inputBoard에 세팅된 값
		// : boardCode, boardNo, boardTitle, boardContent, memberNo
		
		// 2. 게시글 수정 서비스 호출 후 결과 반환
		int result = service.boardUpdate(inputBoard, images, deleteOrderList);
		
		String message = null;
		if(result > 0) {
			message = "게시글이 수정 되었습니다";
		} else {
			message = "수정 실패";
		}
		
		ra.addFlashAttribute("message", message);
		
		return String.format("redirect:/board/%d/%d", boardCode, boardNo);	// 수정 끝나면 해당 글 상세조회
	}

}
