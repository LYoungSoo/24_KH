package pkg3.dto;

import lombok.Getter;
import lombok.Setter;

// Lombok 라이브러리
// - 자바 애플리케이션 개발 시 자주 사용하는 구문을 자동으로 생성하도록 만든 라이브러리
// - @(어노테이션)을 작성하면 컴파일 단계에서 자동으로 코드가 추가됨
//
//

@Getter
@Setter
public class Member {
	
	private String id;
	private String pw;
	private String name;
	private int age;
	
}
