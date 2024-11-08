package edu.kh.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoMember {
	private int todoMemberNo;
	private String todoMemberId;
	private String todoMemberPw;
	private String todoMemberName;
	
}
