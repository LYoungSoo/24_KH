package edu.kh.project.board.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Comment;
import edu.kh.project.board.dto.Pagination;
import edu.kh.project.board.service.BoardService;
import edu.kh.project.member.dto.Member;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
	/**
	 * 게시글 목록 조회 
	 * @param boardCode : 게시판 종류 번호
	 * @param cp		: 현재 조회하려는 목록의 페이지 번호 (current page)
	 * 					  (필수 아님, 없으면 1)
	 * @param model     : forward 시 데이터 전달하는 용도의 객체(request)
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}")
	public String selectBoardList(
		@PathVariable("boardCode") int boardCode,
		@RequestParam(value = "cp", required = false, defaultValue = "1") int cp,
		Model model
	) {
		
		// 서비스 호출 후 결과 반환 받기
		// - 목록 조회인데 Map으로 반환 받는 이유?
		// ==> 서비스에서 여러 결과를 만들어 내야 되는데
		//     메서드는 반환을 1개만 할 수 있기 때문에 Map으로 묶어서 반환 받을 예정
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		
		// map에 묶여있는 값 풀어놓기
		List<Board> boardList = (List<Board>)map.get("boardList");
		Pagination pagination = (Pagination)map.get("pagination");
		
		// 정상 조회 되었는지 log 확인
//		for(Board b : boardList) log.debug(b.toString());
//		log.debug(pagination.toString());
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pagination", pagination);
		
		return "board/boardList";
	}
	
	/**
	 * 게시글 상세 조회
	 * @param boardCode   : 게시판 종류
	 * @param boardNo     : 게시글 번호
	 * @param model		  : forward 시 request scope 값 전달 객체
	 * @param ra		  : redirect 시 request scope 값 전달 객체
	 * @param loginMEmber : 로그인한 회원 정보, 로그인 안되어 있으면 null
	 * @param req		  : 요청 관련 데이터를 담고 있는 객체 (쿠키 포함) 
	 * @param resp		  : 응답 방법을 담고 있는 객체 (쿠키 생성, 쿠키를 클라이언트에게 전달)
	 * @return
	 * @throws ParseException : Date nextDay 요구사항
	 */
	@GetMapping("/{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo") int boardNo,
		Model model,
		RedirectAttributes ra,
		@SessionAttribute(value="loginMember", required=false) Member loginMember,
		HttpServletRequest req,
		HttpServletResponse resp
	) throws ParseException {
		// 1) SQL 수행에 필요한 파라미터들 Map 으로 묶기
//		Map<String, Integer> map = Map.of("boardCode", boardCode, "boardNo", boardNo);
		// K : V 쌍으로 묶은 Map ==> 자동으로 대입됨 ==> 수정이 불가능함!
		
		Map<String, Integer> map = new HashMap<>();
		map.put("boardCode",boardCode);
		map.put("boardNo",boardNo);
		
		/* 로그인이 되어있는 경우 memberNo를 map에 추가 */
		if(loginMember != null) {
			map.put("memberNo", loginMember.getMemberNo());
		}
		
		
		// 2) 서비스 호출 후 결과 반환 받기
		Board board = service.selectDetail(map);
		
		/* 게시글 상세 조회 결과가 없을 경우 */
		if(board == null) {
			ra.addFlashAttribute("message", "게시글이 존재하지 않습니다");
			return "redirect:/board/" + boardCode;
		}
		
		/* ---------- 조회 수 증가 시작 ---------- */
		
		/*
			[게시글 조회 수 증가]
			- 게시글 상세 조회 시 증가
			- 컴퓨터당 하루 1회씩 증가
			- 내가 쓴 글은 조회 수 증가 X
			
			- Cookie
			  - 데이터를 클라이언트가 관리
			  - 서버 요청 시 마다 요청에 담아서 서버에 전달
			  ==> 서버에서 읽은 게시글 번호를 쿠키에 기록하여
			      클라이언트에게 전달
			
			- Session
			  - 데이터를 서버에서 관리
			  - 클라이언트가 서버에 최초 연결 시 생성
		 */
		
		// 로그인한 회원이 작성한 글이 아닌 경우 + 비회원
		if(loginMember == null || loginMember.getMemberNo() != board.getMemberNo()) {
			
			// 1. 요청에 담겨있는 모든 쿠키 얻어오기
			Cookie[] cookies = null;
			
			Cookie c = null;
			if(req.getCookies() != null) {
				cookies = req.getCookies();
				
				for (Cookie temp : cookies) {
					// Cookie는 Map 형식 (name = Value)
					
					// 클라이언트로 부터 전달 받은 쿠키에
					// "readBoardNo" 라는 key(name)가 존재하는 경우
					// == 기존에 읽은 게시글 번호를 저장한 쿠키가 있는 경우
					if(temp.getName().equals("readBoardNo")) {
						c = temp;
						break;
					}
				}
			}
			
			// service 호출 결과 저장
			int result = 0;
			
			// 이전에 "readBoardNo" 라는 name을 가지는 쿠키가 없을 경우
			if(c == null) {
				// 새 쿠키 생성
				c = new Cookie("readBoardNo", "[" + boardNo + "]");
				
				/* DB 에서 해당 게시글의 조회 수를 1 증가 시키는 서비스 호출 */
				result = service.updateReadCount(boardNo);
			}
			// 이전에 "readBoardNo" 라는 name을 가지는 쿠키가 있을 경우
			else {
				// readBoardNo = [1000][2000][3000]
				
				// 현재 읽은 게시글 번호가 쿠키에 없다면
				// == 해당 글은 처음 읽음
				if(c.getValue().contains(boardNo+"") == false) {
					c.setValue(c.getValue() + "[" + boardNo + "]");
					
					// DB에서 조회 수 증가
					result = service.updateReadCount(boardNo);
				}
			}
			
			
			// 2. 조회 수가 증가된 경우 쿠키 세팅하기
			if(result > 0) {
				
				// 미리 조회된 조회 수 와 DB 조회 수 동기화
				board.setReadCount(board.getReadCount() + 1);
				
				// 읽은 글 번호가 저장된 쿠키(c)가 
				// 어떤 주소 요청 시 서버로 전달될지 지정
				c.setPath("/");		// "/" 이하 모든 요청에 쿠키가 포함됨
				
				/* 쿠키의 수명 지정 */
				// - 다음날 00시 00분 00초가 되면 삭제
				// == 오늘 23시 59분 59초까지 유지
				// === 다음날 00시 00분 00초 까지 남은 시간을 계산해서 쿠키에 세팅
				
				// Calendar 객체 : 시간을 저장하는 객체
				// Calendar.getInstance() : 현재 시간이 저장된 객체가 반환됨
				Calendar cal = Calendar.getInstance();
				
				cal.add(cal.DATE, 1);	// 1일 더하기 = 24*60*60초 더하기
				// ex) 2024-10-08 10:14:30 ==> 2024-10-09 10:14:30
				
				// 날짜 데이터를 지정된 포멧의 문자열로 변경하는 객체
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				// import java.util.Date
				Date currentDay = new Date();	// 현재 시간 (2024-10-08 10:14:30)
				
				// 내일(24시간 후) (2024-10-09 10:14:30)
				// Calendar 객체 ==> Date 타입으로 변환
				Date b = new Date(cal.getTimeInMillis());
				
				// sdf.format(b) == "2024-10-09"
				// sdf.parse("2024-10-09") == 2024-10-09 00:00:00 Date 변환
				Date nextDay = sdf.parse(sdf.format(b));
				
				// 다음날 자정 - 현재 시간의 결과를 초(second) 단위로 얻어오기
				// 다음날 0시 0분 0초 까지 몇 초 남았나 계산
				long diff = (nextDay.getTime() - currentDay.getTime()) / 1000;
				
				// 쿠키 수명 설정
				c.setMaxAge((int)diff);
				
				// 응답 객체에 쿠키를 추가해서
				// 응답 시 클라이언트에게 전달할 수 있게 하기
				resp.addCookie(c);
			}
		}
		
		
		
		/* ---------- 조회 수 증가 끝   ---------- */
		
		model.addAttribute("board", board);
		
		// 조회된 이미지 목록이 있을 경우
		if(board.getImageList().isEmpty() == false) {
			
			// 썸네일 X ==> 0~3 index
			// 썸네일 O ==> 1~4 index
			
			// for 문 시작 인덱스 지정
			int start = 0;
			
			// 썸네일이 없다
//			if(board.getImageList().get(0).getImgOrder() == 0) {
			if(board.getThumbnail() != null) start = 1;
			
			model.addAttribute("start", start);		// 0 또는 1
		}
	
		return "board/boardDetail";
	}
	
	/**
	 * 게시글 좋아요 체크 or 해제
	 * @param boardNo
	 * @return map (check, clear / 좋아요 개수)
	 */
	@ResponseBody		// forward 하지않고 redirect
	@PostMapping("like")
	public Map<String, Object> boardLike(
		@RequestBody int boardNo,
		@SessionAttribute("loginMember") Member loginMember
	) {
		int memberNo = loginMember.getMemberNo();
		
		return service.boardLike(boardNo, memberNo);
	}
	
	/**
	 * 댓글 목록 조회(비동기)
	 * @param boardNo : 게시글 번호(쿼리스크링 전달 받음)
	 * @param model   : forward 대상에게 데이터를 전달하는 객체
	 * @return
	 */
	@GetMapping("commentList")
	public String selectCommentList(
		@RequestParam("boardNo") int boardNo,
		Model model
	) {

		List<Comment> commentList = service.selectCommentList(boardNo);
		
		/*
		 * * 보통 비동기 통신(AJAX) 방법
		 * - 요청 ==> 응답(데이터)
		 * 
		 * * forward
		 * - 요청 위임
		 * - 요청에 대한 응답 화면 생성을 템플릿 엔진(jsp, Thymeleaf)이 대신 수행
		 * - 템플릿 엔진이 html을 만들어서 
		 * 
		 * - 동기식 X
		 *   템플릿 엔진을 이용해서 html 코드를 쉽게 생성
		 *   
		 * * @ResponseBody
		 * - 컨트롤러에서 반환되는 값을 응답 본문에 그대로 반환
		 *   ==> 템플릿 엔진(thymeleaf)를 이용해서 html 코드를 만들어서 반환하지 않음 ==> 새로고침 되지 않음
		 *       데이터를 있는 그대로 반환(return 문에 있는 반환 주소에 전달하는게 아니라,
		 *       주소를 String으로 반환(글자 그대로)
		 */
		
		Board board = Board.builder().commentList(commentList).build();
		
		// "board" 라는 key 값으로 생성한 Board 객체를
		// forward 대상인 comment.html 로 전달
		model.addAttribute("board",board);
		
		
		// comment.html 에 작성된 Thymeleaf 코드를 해석해서
		// 완전한 html 코드로 변환 후 요청한 곳으로 응답(fetch() API) 코드로 html 코드가 반환)
		return "board/comment :: comment-list";
//		return "common/main";
	}
	
	
	
	
}

/* 게시글 상세 조회
 * 1) 게시글 정보
 * 		(제목, 내용, 작성자, 프로필,
 * 		 작성일, 수정일, 조회수, 좋아요)
 * 2) 게시글 이미지 (0 ~ 5)개
 * 3) 게시글 댓글 (전부 다)
 */