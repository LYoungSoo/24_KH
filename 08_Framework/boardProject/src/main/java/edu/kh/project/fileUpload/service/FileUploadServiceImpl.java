package edu.kh.project.fileUpload.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.fileUpload.dto.FileDto;
import edu.kh.project.fileUpload.mapper.FileUploadMapper;
import lombok.RequiredArgsConstructor;

// @Transactional
// - Unchecked Exception 발생 시 Rollback 수행

// @Transactional(rollbackFor = Exception.class)
// - Exception 이하 예외 발생 시 Rollbackk 수행 === Checked, Unchecked 가리지 않고 예외 발생 시 롤백
@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class FileUploadServiceImpl implements FileUploadService {
	
	private final FileUploadMapper mapper;
	
	// @PropertySource 에 적힌 경로 파일에서 값을 뽑아올것이다!
	// 인터넷 요청 주소 (/images/test/)
	@Value("${my.test.web-path}")
	private String testWebPath;
	
	// 파일 저장 폴더 경로 (C://uploadFiles/test/)
	@Value("${my.test.folder-path}")
	private String testFolderPath;

	// 단일 파일 업로드
	@Override
	public String test1(MultipartFile uploadFile) throws IllegalStateException, IOException {
		
		// MultipartFile이 제공하는 메서드
		
		// - getSize() : 파일 크기
		// - isEmpty() : 업로드한 파일이 없을 경우 true
		// - getOriginalFileName() : 원본 파일 명
		// - transferTo(경로) : 
		//   메모리 또는 임시 저장 경로에 업로드된 파일을
		//   원하는 경로에 전송(서버 어떤 폴더에 저장할지 지정)
		
		// 1) 업로드된 파일이 있는지 검사
		if(uploadFile.isEmpty()) {		// 업로드 X
			return null;
		}
		
		// 2) 지정된 경로에 파일 저장
//		String path = "C:/uploadFiles/";
		File folder = new File(testFolderPath);
		
		if(folder.exists() == false) {		// c:uploadFiles/ 폴더가 없을 경우
			folder.mkdirs();		// 폴더 생성
		}
		
		/* DB에 업로드 되는 파일 정보를 INSERT
		 * - DB INSERT ==> 파일 저장 순서로 동작
		 *   만약에 파일 저장 중 예외 발생
		 *   ==> @Transactional 어노테이션 Rollback 수행
		 *   ===> INSERT 취소
		 */
		
		// FileDto 객체를 만들어 INSERT 에 필요한 정보를 set
		FileDto file = FileDto.builder().fileOriginalName(uploadFile.getOriginalFilename())
										.fileRename(uploadFile.getOriginalFilename())	// 임시
										.filePath(testWebPath)
										.build();
		
		
		int result = mapper.fileInsert(file);
		
		// 업로드 되어 메모리 또는 임시 저장 폴더에 저장된 파일을
		// 지정된 경로(path)에 파일 저장
		uploadFile.transferTo(new File(testFolderPath + uploadFile.getOriginalFilename()));
		
		// 웹에서 접근 가능한 파일 경로(URL) 반환
		return testWebPath + uploadFile.getOriginalFilename();
	}

	// 파일 목록 조회
	@Override
	public List<FileDto> selectFileList() {
		return mapper.selectFileList();
	}

}
