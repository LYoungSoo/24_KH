package edu.kh.project.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.dto.Member;

// @Mapper
// - Mybatis 제공 어노테이션
// - 해당 인터페이스를 상속 받은 클래스 자동 구현 + Bean 등록

@Mapper
public interface MemberMapper {

	/**
	 * memberEmail 이 일치하는 회원 정보 조회
	 * @param memberEmail
	 * @return loginMember 또는 null
	 */
	Member login(String memberEmail);

	/**
	 * 회원 가입
	 * @param inputMember
	 * @return result
	 */
	int signUp(Member inputMember);

	/**
	 * 이메일 중복 검사(비동기)
	 * @param email
	 * @return count
	 */
	int emailCheck(String email);

	/**
	 * 닉네임 중복 검사(비동기)
	 * @param nickName
	 * @return count
	 */
	int nickNameCheck(String nickName);

}
