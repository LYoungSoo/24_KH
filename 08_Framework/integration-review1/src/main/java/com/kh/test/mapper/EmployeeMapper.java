package com.kh.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.dto.Employee;

@Mapper
public interface EmployeeMapper {

	List<Employee> selectAll();

}
