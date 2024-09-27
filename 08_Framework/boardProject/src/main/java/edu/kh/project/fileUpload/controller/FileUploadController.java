package edu.kh.project.fileUpload.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.fileUpload.dto.FileDto;
import edu.kh.project.fileUpload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("fileUpload")
@RequiredArgsConstructor
public class FileUploadController {
	
	private final FileUploadService service;
	
	/**
	 * 파일 업로드 연습 메인 페이지 전환
	 * @return
	 */
	@GetMapping("main")
	public String fileUploadMain(Model model) {
		List<FileDto> fileList = service.selectFileList();
		model.addAttribute("fileList", fileList);
		return "fileUpload/main";
	}
	
	/*
	 * [Spring 에서 제출된 파일을 처리하는 방법]
	 * 
	 * 1) enctype = "multipart/form-data" 로 클라이언트가 요청
	 * 
	 * 2) Spring에 내장되어 있는 MultipartResolver 가
	 * 	  제출된 파라미터들을 분류함
	 * 
	 * 	  문자열, 숫자 => String
	 * 	  파일 ==> MultipartFile 인터페이스 구현한 객체
	 * 
	 * 3) 컨트롤러 메서드 매개 변수로 전달
	 * 	  (@RequestParam, @ModelAttribute)
	 * 
	 */
	
	/**
	 * 단일 파일 업로드
	 * @return
	 */
	@PostMapping("test1")
	public String test1(
		@RequestParam("uploadFile") MultipartFile uploadFile	// 임시 저장된 파일(메모리, 임시저장 폴더)을 참조하는 객체
	) throws IllegalStateException, IOException {
		String filePath = service.test1(uploadFile);
		
		log.debug("업로드한 파일 경로 : {}", filePath) ;
		
		return "redirect:main";
	}
	
	/**
	 * 단일 파일 업로드 + 일반 데이터
	 * @param uploadFile : 업로드 되어 임시저장된 파일을 참조하는 객체
	 * @param fileName   : 원본 이름으로 지정된 파일명
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("test2")
	public String test2(
		@RequestParam("uploadFile") MultipartFile uploadFile,
		@RequestParam("fileName") String fileName
	) throws IllegalStateException, IOException {
		String filePath = service.test2(uploadFile, fileName);
		log.debug("업로드된 파일 경로 : {}", filePath);
		
//		return "redirect:main";
		return "redirect:/";
	}
	
	/**
	 * 단일 파일 업로드 + 사용자 정의 예외를 이용한 예외 처리
	 * @param uploadFile
	 * @return
	 */
	@PostMapping("test3")
	public String test3(
		@RequestParam("uploadFile") MultipartFile uploadFile
	) {
		String filePath = service.test3(uploadFile);
		
		log.debug("업로드된 파일 경로 : {}", filePath);
		
		return "redirect:main";
	}
}

/* 
	1. 파일 O , 이름 O	: 둘다 정상 제출
	2. 파일 O , 이름 X	: 파일 정상 제출, 이름은 빈칸("")
	3. 파일 X , 이름 O	: 제출된 파일명 == ""(빈칸), size == -1 ==> 파일 제출 안됨 뜻
						  (파일이 선택되지 않으면 null이 아니라 빈칸, 비어있는 형태로 제출
	4. 파일 X , 이름 X	: 제출되지 않음 
*/
