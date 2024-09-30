package com.kh.test.service;

import java.awt.print.Book;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.test.mapper.BookMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
	
	private final BookMapper mapper;
	
	@Override
	public List<Book> selectAllList() {
		return mapper.selectAllList();
	}
}
