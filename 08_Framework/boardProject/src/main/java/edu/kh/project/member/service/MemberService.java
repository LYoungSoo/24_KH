package edu.kh.project.member.service;

import edu.kh.project.member.dto.Member;

public interface MemberService {

	/**
	 * 로그인 서비스
	 * @param memberEmail
	 * @param memberPw
	 * @return loginMember 또는 null(email 또는 pw 불일치)
	 */
	Member login(String memberEmail, String memberPw);

	/**
	 * 회원 가입 수행
	 * @param inputMember
	 * @return
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
