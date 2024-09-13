package edu.kh.project.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.dto.Member;

@Mapper		// 해당 인터페이스를 상속 받은 클래스 자동 구현 + Bean 등록
public interface MyPageMapper {

	/**
	 * 회원 정보 수정
	 * @param inputMember
	 * @return result
	 */
	int updateInfo(Member inputMember);

	/**
	 * 닉네임 중복 검사
	 * @param input
	 * @return result
	 */
	int checkNickname(String input);
	
}
