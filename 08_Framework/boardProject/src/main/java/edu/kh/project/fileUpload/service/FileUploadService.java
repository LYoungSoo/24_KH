package edu.kh.project.fileUpload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.fileUpload.dto.FileDto;

public interface FileUploadService {

	/**
	 * 단일 파일 업로드
	 * @param uploadFile
	 * @return
	 */
	String test1(MultipartFile uploadFile) throws IllegalStateException, IOException;

	/**
	 * 파일 목록 조회
	 * @return fileList
	 */
	List<FileDto> selectFileList();

}
