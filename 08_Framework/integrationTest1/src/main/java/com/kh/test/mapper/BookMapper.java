package com.kh.test.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import com.kh.test.dto.Book;

@Mapper
public interface BookMapper {

	List<Book> selectAllList();

}
