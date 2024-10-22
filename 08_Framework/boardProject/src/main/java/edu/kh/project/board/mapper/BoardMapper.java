package edu.kh.project.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import edu.kh.project.board.dto.Board;
import edu.kh.project.board.dto.Comment;

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

	/**
	 * 게시글 상세 조회
	 * @param map 
	 * @return board
	 */
	Board selectDetail(Map<String, Integer> map);

	/**
	 * 조회 수 1 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 게시글 좋아요 체크 or 해제
	 * 좋아요 누른 적 있어? 검사
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int checkBoardLike(
		@Param("boardNo") int boardNo,
		@Param("memberNo") int memberNo);

	/**
	 * 좋아요 테이블에 삽입 == 좋아요를 누른적이 없을때
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int insertBoardLike(
		@Param("boardNo") int boardNo,
		@Param("memberNo") int memberNo);

	/**
	 * 좋아요 테이블에서 삭제 == 좋아요를 누른적이 있을때
	 * @param boardNo
	 * @param memberNo
	 * @return
	 */
	int deleteBoardLike(
		@Param("boardNo") int boardNo,
		@Param("memberNo") int memberNo);

	/**
	 * 좋아요 개수 조회
	 * @param boardNo
	 * @return
	 */
	int getLikeCount(int boardNo);

	/**
	 * DB에서 모든 게시판 종류를 조회
	 * @return
	 */
	List<Map<String, String>> selectBoardTypeList();

	/**
	 * 댓글 목록 조회 - 이미 있음 - 계층형 쿼리
	 * @param boardNo
	 * @return commentList
	 */
	List<Comment> selectCommentList(int boardNo);

	/**
	 * 검색 조건이 맞는 게시글 수 조회
	 * @param paramMap (key, query, boardCode)
	 * @return count
	 */
	int getSearchCount(Map<String, Object> paramMap);

	/**
	 * 검색 목록 조회
	 * @param paramMap
	 * @param rowBounds
	 * @return boardList
	 */
	List<Board> selectSearchList(Map<String, Object> paramMap, RowBounds rowBounds);

	/**
	 * 현재 게시글이 속해있는 페이지 번호 조회하는 서비스
	 * @param boardCode
	 * @param boardNo
	 * @param limit
	 * @return cp
	 */
	int getCurrentPage(Map<String, Object> paramMap);
	
	
	
	
	
	
}
