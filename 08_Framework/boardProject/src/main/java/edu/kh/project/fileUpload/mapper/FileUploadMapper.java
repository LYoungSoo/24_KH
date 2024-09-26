package edu.kh.project.fileUpload.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.fileUpload.dto.FileDto;

@Mapper
public interface FileUploadMapper {

	/**
	 * 파일 1개 정보 DB에 삽입
	 * @param file
	 * @return
	 */
	int fileInsert(FileDto file);

	/**
	 * 파일 목록 조회
	 * @return fileList
	 */
	List<FileDto> selectFileList();

}
