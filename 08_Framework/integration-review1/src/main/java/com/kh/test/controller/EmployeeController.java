package com.kh.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.test.dto.Employee;
import com.kh.test.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor	// final 구문이 작성된 구문을 자동 의존성 주입
@RequestMapping("employee")
public class EmployeeController {
	
	private final EmployeeService service;
	
	@ResponseBody
	@GetMapping("selectAll")
	public List<Employee> selectAll(
	) {
		return service.selectAll();
	}

}
