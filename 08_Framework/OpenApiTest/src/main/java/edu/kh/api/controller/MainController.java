package edu.kh.api.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller		// 컨트롤러 역할 명시 + Bean(Spring Container 가 관리하는 객체 (IOC))
@Slf4j			// Logger log 필드 자동 생성(Lombok 제공)
public class MainController {
	/**
	 * 에어코리아 대기오염정보 - 시도별 실시간 측정정보 조회
	 *
	 * @param location : 지역명(시, 도 이름)
	 * @throws IOException
	 */

	@GetMapping("/")
//	public String airPollution(@RequestParam("cityName") String cityName) throws IOException, URISyntaxException {			이건 뭐라쓰냐???
	public String airPollution(@RequestParam(name="cityName", required=false, defaultValue="서울") String cityName) throws IOException, URISyntaxException {

	// API 개인 인증키
	    
	    String serviceKey = "XXbHmMVPnofdOHgdIfy3pphBo6HMg1PZbPbwTLXXAX5NTO2hUsFS3i5a2PHufmnixHRnsUNyF9KatS2ZZ6WqfQ%3D%3D";
	    
// @@   String requestUrl = "";			주소 등록
	    String requestUrl = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
	    
	    StringBuilder urlBuilder = new StringBuilder(requestUrl);
	    urlBuilder.append("&" + URLEncoder.encode("sidoName", "UTF-8") + "=" + URLEncoder.encode(cityName, "UTF-8"));
	    urlBuilder.append("&" + URLEncoder.encode("returnType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));

	    // 공공데이터 요청 및 응답
	    URI uri = new URI(urlBuilder.toString());
	    URL url = uri.toURL();
	    
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/json");
	    
	    
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
	    log.debug(sb.toString());
	    
	    return "air";
	}
	
}
