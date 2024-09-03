package edu.kh.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

// Parameter	: 매개 변수(메서드 수행 시 전달 받은 값)
// Argument		: 전달 인자(메서드 호출 시 전달하는 값)

// Controller 	: 요청, 응답 제어 역할
// Bean 		: Spring 이 만든 객체

@Slf4j						// 필드 부분에 logger 가 생성됨
@Controller					// 컨트롤러 임을 명시 + Bean 등록
@RequestMapping("param")	// param 으로 시작하는 요청을 해당 Controller 에 매핑
public class ParameterController {
	
	@RequestMapping("main")	// /param/main 요청을 처리하는 메서드
	public String paramMain() { 
		
		// classpath:/templates/param/param-main.html 로 forward 하기
		
		// * View Resolver (뷰 해결사)가 
		//   접두사, 접미사를 붙여서 자동으로 forward 를 진행
		// thymeleaf 의 접두사 : classpath:/templates
		// thymeleaf 의 접미사 : .html
		
		return "param/param-main";
	}
	
	
	// /param/test1 GET 방식 요청을 처리하는 메서드
	
	/* 1. HttpServletRequest.getParameter("key") 사용하기 */
	// HttpServletRequest
	// - 요청한 클라이언트의 정보, 제출된 파라미터 등을 저장한 객체
//	@RequestMapping(value = "test1", method = RequestMethod.GET)
	@GetMapping("test1")
	public String test1(HttpServletRequest req) {
		
		// 매개 변수 HttpServletRequest req 사용 가능할까???
		// ==> 가능! 왜?
		// ==> Spring 의 ArgumentResolver 때문에 가능
		
		// ArgumentResolver(전달 인자 해결사)	==> 매개 변수가 null 이 아니어야한다
		// ==> Controller 메서드에 작성된
		//     매개 변수 타입에 맞는 객체가 존재하면 바인딩, 없으면 생성해서 바인딩	
		
		//  https://docs.spring.io/spring-framework/reference/web/webflux/controller/ann-methods/arguments.html
		
		String inputName = req.getParameter("inputName");
		String inputAge = req.getParameter("inputAge");
		String inputAddress = req.getParameter("inputAddress");

		System.out.println(inputName);
		System.out.println(inputAge);
		System.out.println(inputAddress);
		
		// request scope에 K:"message", V:"메시지 입니다" 세팅
		req.setAttribute("message","메시지 입니다");
		
		// classpath:/templates/param/result1.html로 forward
		return "param/result1";
		
		// forward란?
		// - 클라이언트에 요청에 대한 응답 처리를 대신 해달라고 넘기는 것(요청 위임)
		
		// - 위임 시 HttpServletRequest, HttpServletResponse를 같이 위임
	}
	
	/* 2. @RequestParam 어노테이션 - 낱개 (한 개, 단 수) 개 파라미터 얻어오기
	 * 
	 * - request 객체를 이용한 파라미터 전달 어노테이션
	 * - 매개 변수 앞에 해당 어노테이션을 작성하면, 매개변수에 값이 주입됨.
	 * - 주입되는 데이터는 매개 변수의 타입이 맞게 형변환/파싱이 자동으로 수행됨!
	 * 
	 * [기본 작성법]
	 * @RequestParam("key")자료형 매개변수명
	 * 
	 * [속성 추가 작성법]
	 * @RequestParam(value="name", required="false", defaultValue="1")
	 * 
	 * value : 전달 받은 input 태그의 name 속성값
	 * 
	 * required : 입략된 name 속성값 파라미터 필수 여부 지정(기본값 true)
	 *  ==> required = true 인 파라미터가 존재하지 않는다면 400 Bad Request 에러 발생
	 *  ==> required = true 인 파라미터가 null 인 경우에도 400 Bad Request
	 *  
	 * defaultValue : 파라미터 중 일치하는 name 속성 값이 없을 경우에 대입할 값 지정.
	 *  ==> required = false 인 경우 사용 
	 */
	
	/* 2. @RequestParam 이용하기
	      - 낱개(단수, 하나, 독립된) 파라미터 컨트롤러에 얻는 방법 */
	
	// /param/test2 POST 방식 요청을 처리하는 메서드
	@PostMapping("test2")
	public String test2 (
		@RequestParam("title")  	String t,
		@RequestParam("writer") 	String w,
		@RequestParam("price")  	int    p,
		@RequestParam("publisher")	String ps
	) {
	
		// 파라미터 중 price를 int로 저장할 수 있는 이유!!!! (빠밤)
		// ==> HTML에서 얻어오는 모든 값은 기본적으로 String 형태이나
		// 	   Spring이 매개 변수에 기입된 타입으로 자동으로 형변환/파싱을 수행해 준다!!!
		
		/* log를 이용해서 파라미터 출력하기
		 * 1) 클래스 위에 @Slf4j 어노테이션 추가 (Lombok 제공) 
		 * 2) log.debug() 또는 log.info() 등을 이용해서 log 출력
		 * */
		
		log.debug("title : {}", t);		// {} 안에 t 값이 대입되어 출력됨
		log.debug("writer : {}", w);
		log.debug("price : {}", p);
		log.debug("publisher : {}", ps);
		
		// classpath:/templates/param/result2.html
		// forward
		return "param/result2";
	}
	
	/* 3. @RequestParam 이용하기
	 *    - 여러 개(복수, 다수) 파라미터 컨트롤러에서 얻는 방법
	 */
	
	// 1) String[]
	// 2) List<자료형>
	// 3) Map<String, Object>
	
	@PostMapping("test3")	// /param/test3 POST 방식 처리 메서드
	public String test3(
		@RequestParam(value="color", required = false) String[] colorArr,
		// 파라미터 중 "color" 만 모아서 String[]로 전달받기
		// 단, 필수는 아니다(null을 받겠다 ==> required = false 를 작성을 하였기 때문에 가능)
		
		@RequestParam(value="fruit", required = false) List<String> fruitList,
		@RequestParam Map<String, Object> paramMap
		// ==> Map 형식으로 파라미터를 얻어오는 경우
		// ==  특정 key만 얻어오는 것이 아닌, 모든 파라미터를 얻어옴
		// 	   단, key가 중복인 경우 value 덮어쓰기가 이루어짐!! == 데이터 손실?
			
	) {
		
		log.debug("colorArr : {}", Arrays.toString(colorArr));
		log.debug("fruitList : {}", fruitList);
		log.debug("paramMap : {}", paramMap);
		
		return "param/result3";	// forward
		//Thymeleaf 가 접두사 접미사를 자동으로 붙여주어서 경로가 완성됨
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
