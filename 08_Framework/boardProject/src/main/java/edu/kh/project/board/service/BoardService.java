package edu.kh.project.board.service;

import java.util.Map;

public interface BoardService {

	/**
	 * 게시글 목록을 조회
	 * @param boardCode
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

}
