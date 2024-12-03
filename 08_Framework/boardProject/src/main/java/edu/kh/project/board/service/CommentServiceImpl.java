package edu.kh.project.board.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.board.dto.Comment;
import edu.kh.project.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;

@Service		// 비즈니스 로직, Bean 등록 ==> 필요한 곳에 의존성 주입
@Transactional
@RequiredArgsConstructor		// final 필드 생성자 자동 완성
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper mapper;

	@Override
	public int commentInsert(Comment comment) {

		int result = mapper.commentInsert(comment);
		
		// 삽입 성공 시 댓글 번호 반환
		if(result > 0) return comment.getCommentNo();
		
		// 실패 시 0 반환
		return 0;
	}

	// 댓글 삭제
	@Override
	public int commentDelete(int commentNo, int memberNo) {
		return mapper.commentDelete(commentNo, memberNo);
	}
	
	// 댓글 수정
	@Override
	public int commentUpdate(Comment comment) {
		return mapper.commentUpdate(comment);
	}

}
