package com.kh.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.test.dto.Member;
import com.kh.test.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {
	
//	private MemberService service;
	private final MemberService service;	// RequiredArgsConstructor 어노테이션 의존성 주입용 final
	
	@ResponseBody		// 새로고침 없이 적용할것이니 추가
	@GetMapping("selectAllList")
	public List<Member> selectAllList() {
		return service.selectAllList();
	}
	
}