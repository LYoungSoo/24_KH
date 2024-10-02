package edu.kh.project.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Pagination;
import edu.kh.project.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor		// final 필드 생성자 자동 완성
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;

	// 게시글 목록 조회
	@Override
	public Map<String, Object> selectBoardList(int boardCode, int cp) {
		
		// 1. boardCode가 일치하는 게시글의 전체 개수 조회
		//    (조건 : 삭제되지 않은 글만 카운트!)
		int listCount = mapper.getListCount(boardCode);
		
		// 2. listCount와 cp를 이용해서
		//	  조회될 목록 페이지,
		//    출력할 페이지네이션의 값을 계산할 Pagination 객체 생성하기
		Pagination pagination = new Pagination(cp, listCount);
		
		// 3. DB에서 cp(조회하려는 페이지)에 해당하는 행을 조회
		// ex) cp == 1,  전체 목록 중  1 ~ 10  행 결과만 반환
		//     cp == 2,  전체 목록 중 11 ~ 20  행 결과만 반환
		// 	   cp == 10, 전체 목록 중 91 ~ 100 행 결과만 반환
		/*
		 * [RowBounds 객체]
		 * - Mybatis 제공 객체
		 * - 지정된 크기(offset) 만큼 행을 건너 뛰고
		 *   제한된 크기(limit) 만큼의 행을 조회함
		 *   
		 * - 사용법 : Mapper 의 메서드 호출 시
		 *            2번째 이후 매개변수로 전달
		 *            (1번은 SQL에 전달할 파라미터가 기본값)
		 */
		int limit = pagination.getLimit();	// 10
		int offset = (cp - 1) * limit;
		
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Board> boardList = mapper.selectBoardList(boardCode, rowBounds);
		
		// 4. 목록 조회 결과 + Pagenation 객체를 Map으로 묶어서 반환
		Map<String, Object> map = new HashMap<>();
		map.put("boardList", boardList);
		map.put("pagination", pagination);
		
		// Map 생성 + 바로 데이터 담기
//		Map<String, Object> map2
//			= Map.of("boardList", boardList, "Pagination", pagination);
		
		return map;
	}

}

/*
 
  public class Pagination {
	
	private int currentPage;		// 현재 페이지 번호
	private int listCount;			// 전체 게시글 수
	
	private int limit = 10;			// 한 페이지 목록에 보여지는 게시글 수
	private int pageSize = 10;		// 보여질 페이지 번호 개수
	
	private int maxPage;			// 마지막 페이지 번호
	private int startPage;			// 보여지는 맨 앞 페이지 번호 
	private int endPage;			// 보여지는 맨 뒤 페이지 번호
	
	private int prevPage;			// 이전 페이지 모음의 마지막 번호 
	private int nextPage;			// 다음 페이지 모음의 시작 번호 
  
*/
