package edu.kh.project.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.dto.Board;

public interface EditBoardService {

	/**
	 * 게시글 등록
	 * @param inputBoard
	 * @param images
	 * @return boardNo
	 */
	int boardInsert(Board inputBoard, List<MultipartFile> images);

}
