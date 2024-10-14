package com.kh.test.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.test.service.ProductService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ProductService service;
	
	@Override	// 전처리
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1) application scope 객체 얻어오기
		ServletContext application = request.getServletContext();
		
		if(application.getAttribute("categoryList") == null) {
			
			
			List<Map<String, Object>> categoryList 
				= service.selectCategoryList();
			
			// 조회 결과를 application 객체에 세팅
			application.setAttribute("categoryList", categoryList);
		}

		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
}