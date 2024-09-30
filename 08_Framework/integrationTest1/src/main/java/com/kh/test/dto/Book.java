package com.kh.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {		// DB 컬럼명이랑 일치해야하는데
	private int    bookNo;
//	private String bookTitle;
	private String title;
//	private String bookWriter;
	private String writer;
//	private int    bookPrice;
	private int    price;
	
}