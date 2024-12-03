package edu.kh.project.board.service;

import edu.kh.project.board.dto.Comment;

public interface CommentService {

	/**
	 * 댓글 등록
	 * @param comment
	 * @return commentNo
	 */
	int commentInsert(Comment comment);

	/**
	 * 댓글 삭제
	 * @param commentNo
	 * @param memberNo
	 * @return result
	 */
	int commentDelete(int commentNo, int memberNo);
	
	/** 댓글 수정
	 * @param comment
	 * @return result
	 */
	int commentUpdate(Comment comment);
}
