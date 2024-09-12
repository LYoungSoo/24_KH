package edu.kh.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.test.dto.Student;

@Controller
@RequestMapping("student")
public class StudentController {

	@PostMapping("select")
	public String selectStudent(Model model, @ModelAttribute Student student) {
		model.addAttribute("stdName", student.getStdName());
		model.addAttribute("stdAge", student.getStdAge());
		model.addAttribute("stdAddress", student.getStdAddress());
		return "student/select";
	}
	
}

/*
1. StudentController.java
public class StudentController {  바로 윗부분에
요청을 응답 / 제어할 @Controller 어노테이션 미작성 => Bean이 등록되지 않음

@RequestMapping("student") 미작성 ==> "student" 이름으로 시작하는 요청을
 controller에 매핑하지 않음
2. index.html과 select.html 의 객체 이름과 java의 객체 이름이 일치하지 않음​
-> @ModelAttribute를 이용하여 Student 객체에 제출된 파라미터를 자동 세팅하기 위해서는
파라미터의 key값(name 속성 값)과 필드명이 같아야 하는데 같지 않아 필드 값이 없는 문제 발생

->  조회 결과 페이지에서 세팅된 값을 얻어와 출력하는 Spring EL 구문 ker값이
컨트롤러 Model에 세팅할 때의 key값과 일치하지 않아 출력되지 않는 문제 발생.

- 문제 원인 설명이 미흡하게 작성됨(-12.5)
*/