package edu.kh.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor		// 기본생성자
@AllArgsConstructor
@ToString
public class Member {
	
	private String memberId;
	private String memberPw;
	private String MemberName;
	private int	   memberAge;
	

}
