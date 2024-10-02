package com.kh.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
	private int    memberNo;
//	private String memberName;
	private String name;
//	private String memberId;
	private String address;
//	private String memberPassword;
	private int    age;
}

// db 와 dto의 data type이 일치하지 않음