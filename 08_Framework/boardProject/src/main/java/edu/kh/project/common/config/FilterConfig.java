package edu.kh.project.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.project.common.filter.LoginFilter;
import edu.kh.project.common.filter.SignUpFilter;



/* 사용할 필터 클래스를 Bean 으로 등록 + 적용될 요청 주소 설정 */

@Configuration		// 서버 실행 시 내부 메서드 모두 수행 ==> 설정 적용
public class FilterConfig {
	
	@Bean		// 반환된 객체를 Bean 으로 등록
	public FilterRegistrationBean<SignUpFilter> signUpFilter() {
		
		// FilterRegistrationBean : 필터를 Bean 으로 등록하는 객체
		
		FilterRegistrationBean<SignUpFilter> filter = new FilterRegistrationBean<>();
		
		// 동작할 코드가 doFilter() 메서드에 작성된
		// 필터 객체(SignUpFilter) 생성
		SignUpFilter signUpFilter = new SignUpFilter();
		
		filter.setFilter(signUpFilter);			// 필터 등록
		
		// 필터가 동작할 요청 경로 패턴 지정
		String[] filteringUrl = {"/member/signUp"};
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		
		// 필터 이름 지정
		filter.setName("signUpFilter");
		
		// 필터 순서 지정
		filter.setOrder(1);
		
		return filter;
	}
	
	
	@Bean
	public FilterRegistrationBean<LoginFilter> loginFilter() {
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		
		// LoginFilter 객체를 생성해서 filter에 세팅
		LoginFilter loginFilter = new LoginFilter();
		filter.setFilter(loginFilter);
		
		// 필터가 동작할 url 패턴 작성
		String[] filteringUrl = {"/myPage/*", "/editBoard/*"};
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		
		filter.setName("loginFilter");
		filter.setOrder(2);
		
		return filter;
	}
	
// ----------------------------------------------------------------------------------------------------
	
}
