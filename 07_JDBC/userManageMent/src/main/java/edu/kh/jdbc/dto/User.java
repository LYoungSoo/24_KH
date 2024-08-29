package edu.kh.jdbc.dto;

// DTO (Data Transfer Object) : 값을 묶어서 전달하는 용도의 객체
// ==> DB에 데이터를 전달하거나, 가져올 때에도 사용
// == DB 특정 테이블의 한 행의 데이터를 저장할 수 있는 형태로 class 작성

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter // 컴파일 시 getter 구문을 자동 추가
@Setter // 컴파일 시 setter 구문을 자동 추가
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor // 전체 필드 매개 변수 생성자
@ToString   // toString() 오버라이딩
public class User {
    
    private int userNo;
    private String userId;
    private String userPw;
    private String userName;
    private String enrollDate;
    // ==> enrollDate는 왜 java.sql.Date가 아니라 String 인가??
    // ===> DB 조회 시 날짜 데이터를 원하는 형태의 문자열로 변화하여 조회할 예정!
    // ====> TO_CHAR() 이용
    
}
