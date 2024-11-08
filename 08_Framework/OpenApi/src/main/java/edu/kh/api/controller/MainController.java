package edu.kh.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	) throws IOException, URISyntaxException, JSONException {
		
		/* Server(Java) 에서 공공데이터 요청하기 */
		

		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty"); /* URL */
	    urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + encodingServiceKey); /* Service Key */
	
	    /* 시도 이름(전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종) */
	    urlBuilder.append("&" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode(cityName, "UTF-8"));
	
	    /* xml 또는 json */
	    urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
	    
	    /* 한 페이지 결과 수 */
	    urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	
	    /* 페이지번호 */
	    urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
	    
	    /* 버전별 상세 결과 참고 */
	    urlBuilder.append("&" + URLEncoder.encode("ver", "UTF-8") + "=" + URLEncoder.encode("1.0", "UTF-8")); 


	   
	    /* 제공 샘플과 다른 부분*/ 
	    URI uri = new URI(urlBuilder.toString());
	    URL url = uri.toURL();
	
	
	    /* 제공 샘플과 다른 부분 */
	    // 샘플 코드 수행 시 응답 데이터에서 한글이 깨지는 문제 발생
	    // ==> charset=UTF-8을 추가하여 응답 데이터의 문자 인코딩을 UTF-8로 지정
	    // ===> 한글 깨짐 해결
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json; charset=UTF-8");
	    System.out.println("Response code: " + conn.getResponseCode());
	    BufferedReader rd;
	    if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	      rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    } else {
	      rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	    }
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = rd.readLine()) != null) {
	      sb.append(line);
	    }
	    rd.close();
	    conn.disconnect();
	
	    // 응답 받은 데이터(JSON)를 로그에 출력
	    String response = sb.toString();
	    log.debug(response);
		
			
		// Jackson 라이브러리
		// ==> JSON 데이터를 Java 객체 (Map, DTO)로 변환할 수 있는 라이브러리
		// spring-starter-web dependency 에 Jackson data-bind 포함되어있음
		
		// JSON을 쉽게 다루거나 변환할 수 있는 객체
		ObjectMapper objectMapper = new ObjectMapper();
		
		JsonNode rootNode = objectMapper.readTree(response);
		
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
