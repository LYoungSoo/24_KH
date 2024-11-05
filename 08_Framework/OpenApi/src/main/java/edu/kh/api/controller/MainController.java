package edu.kh.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.kh.api.dto.ItemDto;
import lombok.extern.slf4j.Slf4j;

@Controller		// 컨트롤러 역할 명시 + Bean(Spring Container 가 관리하는 객체 (IOC))
@Slf4j			// Logger log 필드 자동 생성(Lombok 제공)
@PropertySource("classpath:/config.properties")
public class MainController {
	
	// 서비스키 얻어오기
	@Value("${api.serviceKey.encoding}")
	private String encodingServiceKey;
	
	@Value("${api.serviceKey.decoding}")
	private String decodingServiceKey;
	
	
	@GetMapping("/")
	public String mainPage(
			@RequestParam(name="cityName", required=false, defaultValue="서울") String cityName,
			Model model
	) {
		/* Server(Java) 에서 공공데이터 요청하기 */
		
		// 시도별 미세먼지 농도 조회 요청 주소
		String url = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
		// Uniform Resource Location ==> 자원의 경로
		
		// 파라미터 : ServiceKey(필드), sidoName(매개변수), returnType, numOfRows, pageNo, ver
		String returnType = "json";
		int numOfRows = 1;
		int pageNo = 1;
		String ver = "1.0";
//		double ver = 1.0;
		
		// UriComponentBuilder
		// - Spring 에서 제공하는 URI 관련 객체
		// - 주소 + 쿼리스트링 조합을 쉽게 할 수 있는 기능을 제공함
		// - 자동으로 URL 인코딩 처리하여 안전하게 요청 가능
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("serviceKey", "{serviceKey}")
				.queryParam("sidoName", cityName)
				.queryParam("returnType", returnType)
				.queryParam("numOfRows", numOfRows)
				.queryParam("pageNo", pageNo)
				.queryParam("ver", ver);
		// ==> 자동으로 url + 쿼리스트링이 합쳐진 주소가 생성
		
		String uriString = uriBuilder.build().toUriString();
		log.debug("uriString : {}", uriString);
		
		uriString = uriString.replace("{serviceKey}", decodingServiceKey);
		log.debug("uriString : {}", uriString);
		
		// 요청 헤더
		HttpHeaders headers = new HttpHeaders();		//	fetch(주소){method: "POST" headers : '여기' 
		headers.set("Content-Type", "application/json");
//		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Accept", "*/*;q=0.9"); // HTTP_ERROR 방지
		
		// Http 요청 헤더와 바디를 묶어주는 객체
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		// Spring 제공 HTTP 클라이언트
		// Spring 에서 외부로 HTTP 요청을 보내고 응답을 받는 역할의 객체
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			ResponseEntity<String> response = restTemplate.exchange(	// 요청을 보내면 응답을 교환받겠다
					uriString,		// 요청 주소 + 쿼리스트링(여기서 한번 더 URL 인코딩 수행)
					HttpMethod.GET,	// GET 방식 요청
					entity,			// HTTP 헤더, 바디
					String.class);	// 응답 데이터의 형태
			
			String responseBody = response.getBody();
			
			log.debug("responseBody {} : " + responseBody);
			
			log.debug("uriString : {}", uriString);
			
			
			// Jackson 라이브러리
			// ==> JSON 데이터를 Java 객체 (Map, DTO)로 변환할 수 있는 라이브러리
			// spring-starter-web dependency 에 Jackson data-bind 포함되어있음
			
			// JSON을 쉽게 다루거나 변환할 수 있는 객체
			ObjectMapper objectMapper = new ObjectMapper();
			
			JsonNode rootNode = objectMapper.readTree(responseBody);
			
			// json 데이터 중 items 찾기
			JsonNode itemNode = rootNode.path("response").path("body").path("items");
			
			// 찾은 items를 List 형태로 변환
			List<ItemDto> items = objectMapper.readValue(itemNode.toString(),	// items JSON 반환(문자열)
					new TypeReference<List<ItemDto>>() {/* 미완성 메서드 오버라이딩 */});
			// items JSON 데이터를 읽어올 때 List<ItemDto>를 참조해서 형변환
			
			log.debug("items.get(0) : {}", items.get(0));
			
//			model.addAttribute("item", items.get(0));
			
			/* 데이터 가공 */
			String[] gradeEmoji = {"😄", "🙂", "😷", "🤢"};
			String[] gradeText = {"좋음", "보통", "나쁨", "매우나쁨"};
			
			ItemDto item = items.get(0);
			model.addAttribute("pm10Grade", gradeEmoji[item.getPm10Grade() -1]);
			model.addAttribute("pm10GradeText", gradeText[item.getPm10Grade() -1]);
			model.addAttribute("pm10Value", String.format("미세먼지 농도 : %s ㎍/㎥", item.getPm10Value()));
			
			model.addAttribute("pm25Grade", gradeEmoji[item.getPm25Grade() -1]);
			model.addAttribute("pm25GradeText", gradeText[item.getPm25Grade() -1]);
			model.addAttribute("pm25Value", String.format("미세먼지 농도 : %s ㎍/㎥", item.getPm25Value()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		// classpath:/templates/main.html 로 forward ==> 해석된 html 코드가 클라이언트에게 전달(응답)
		// ==> Thymeleaf 가 html 을 만들어 Client 에게 응답하는데, java 데이터를 Thymeleaf로 전송하여
		return "main";
	}
	
	
	/**
	 * ResponseEntity : @ResponseBody + 응답 상태 코드 ==> @ResponseBody는 응답에 실패(500) 해도 200번나옴
	 * 
	 * - HTTP 응답을 표현할 수 있는 객체
	 * - 비동기 응답 서비스(RESTful API) 에서 유용하게 사용
	 * 
	 * @return
	 */
	@PostMapping("getServiceKey")
	public ResponseEntity<?> getServiceKey() {		// <?> ==> 아무거나 들어올 수 있다, 조건없이?
		
		try {
			return new ResponseEntity<String>(encodingServiceKey, HttpStatus.OK);
//			return new ResponseEntity<String>(encodingServiceKey, HttpStatus.NOT_FOUND);
		} catch(Exception e) {
			return new ResponseEntity<String>("에러", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
