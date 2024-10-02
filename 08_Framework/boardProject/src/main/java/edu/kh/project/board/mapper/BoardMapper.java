package edu.kh.project.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.dto.Board;

@Mapper
public interface BoardMapper {

	/**
	 * 1. boardCode가 일치하는 게시글의 전체 개수 조회
	 *    (조건 : 삭제되지 않은 글만 카운트!)
	 * @param boardCode
	 * @return
	 */
	int getListCount(int boardCode);
	
	/**
	 * 3. 지정된 페이지 분량의 게시글 목록 조회
	 * @param boardCode
	 * @param rowBounds
	 * @return
	 */
	List<Board> selectBoardList(int boardCode, RowBounds rowBounds);
	
	
	
	
	
	
}
