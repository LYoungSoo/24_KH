package com.kh.test.controller;

import java.awt.print.Book;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.test.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor	// final 구문이 작성된 구문을 자동 의존성 주입
@RequestMapping("book")
public class BookController {
	
//	private BookService service;
	private final BookService service;		// (의존성 주입)
	
	@ResponseBody	//세션새로고침안쓰니까
	@GetMapping("selectAllList")
	public List<Book> selectAllList() {
		return service.selectAllList();
	}
	
}