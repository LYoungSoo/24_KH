package edu.kh.project.board.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Pagination;
import edu.kh.project.board.service.BoardService;
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
	 * @param boardCode : 게시판 종류
	 * @param boardNo   : 게시글 번호
	 * @param model		: forward 시 request scope 값 전달 객체
	 * @param ra		: redirect 시 request scope 값 전달 객체
	 * @return
	 */
	@GetMapping("/{boardCode:[0-9]+}/{boardNo:[0-9]+}")
	public String boardDetail(
		@PathVariable("boardCode") int boardCode,
		@PathVariable("boardNo") int boardNo,
		Model model,
		RedirectAttributes ra
	) {
		// 1) SQL 수행에 필요한 파라미터들 Map 으로 묶기
		Map<String, Integer> map = Map.of("boardCode", boardCode,
										  "boardNo", boardNo);
		// K : V 쌍으로 묶은 Map ==> 자동으로 대입됨 ==> 수정이 불가능함!
		
		// 2) 서비스 호출 후 결과 반환 받기
		Board board = service.selectDetail(map);
		
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
	
}

/* 게시글 상세 조회
 * 1) 게시글 정보
 * 		(제목, 내용, 작성자, 프로필,
 * 		 작성일, 수정일, 조회수, 좋아요)
 * 2) 게시글 이미지 (0 ~ 5)개
 * 3) 게시글 댓글 (전부 다)
 */