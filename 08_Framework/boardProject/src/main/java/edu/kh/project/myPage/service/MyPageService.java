package edu.kh.project.myPage.service;

import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.member.dto.Member;

public interface MyPageService {

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

	/**
	 * 비밀번호 변경
	 * @param currentPw
	 * @param newPw
	 * @param loginMember
	 * @return
	 */
	int changePw(String currentPw, String newPw, Member loginMember);

	/**
	 * 회원 탈퇴
	 * @param memberPw
	 * @param loginMember
	 * @return
	 */
	int secession(String memberPw, Member loginMember);

	/**
	 * 로그인한 회원의 프로필 이미지 수정
	 * @param profileImg
	 * @param memberNo
	 * @return
	 */
	String profile(MultipartFile profileImg, int memberNo);

}
