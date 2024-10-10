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

	/**
	 * 게시글 삭제
	 * @param memberNo
	 * @param boardNo
	 * @return
	 */
	int boardDelete(int memberNo, int boardNo);

	/**
	 * 게시글 수정
	 * @param inputBoard
	 * @param images
	 * @param deleteOrderList
	 * @return result
	 */
	int boardUpdate(Board inputBoard, List<MultipartFile> images, String deleteOrderList);

}
