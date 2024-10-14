package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.project.common.interceptor.BoardNameInterceptor;
import edu.kh.project.common.interceptor.BoardTypeInterceptor;

@Configuration		// 서버 실행 시 내부 메서드 모두 실행
public class InterceptorConfig implements WebMvcConfigurer{
	
	// BoardTypeInterceptor 클래스를 Bean으로 등록
	@Bean	// 메서드에서 반환된 객체를 Bean으로 등록
	public BoardTypeInterceptor boardTypeInterceptor() {
		return new BoardTypeInterceptor();
	}
	
	// BoardNameInterceptor 클래스를 Bean으로 등록
	@Bean
	public BoardNameInterceptor boardNameInterceptor() {
		return new BoardNameInterceptor();
	}
	
	// 요청/응답을 가로채서 동작할 인터셉터를 추가
	// == 해당 메서드에 작성된 인터셉터 객체가 동작 == 완성된 두 인터셉터를 동작
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// BoardTypeInterceptor Bean 을 인터셉터로 등록
		registry.addInterceptor(boardTypeInterceptor())		// 전처리
				.addPathPatterns("/**")		// "/" 이하 모든 요청 가로챔 (이하 / 까지 싹다.)
				.excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico");
									// 가로채지 않을 요청 주소 작성 ==> css, js, images, favicon
		
		// /board 또는 /editBoard 이하 모든 요청을 가로채서
		// 동작하는 boardNameInterceptor 등록
		registry.addInterceptor(boardNameInterceptor())		// 후처리
				.addPathPatterns("/board/**", "/editBoard/**");
	}

}
