package edu.kh.project.myPage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

	/**
	 * 비밀번호 변경
	 * @param memberNo
	 * @param encPw
	 * @return result
	 */
	int changePw(
		@Param("memberNo") int memberNo,
		@Param("encPw")    String encPw
	);

	/**
	 * 회원 탈퇴
	 * @param memberPw
	 * @param memberNo
	 * @return result
	 */
	int secession(int memberNo);
	
	/*
	 * 마이바티스 Mapper 인터페이스 메서드 호출 시
	 * 별도의 어노테이션이 없다면
	 * 첫 번째 매개 변수만
	 * mapper.xml 파일에 전달되는 parameter로 인식된다!!
	 * 
	 * [해결 방법]
	 * 1. DTO, 컬렉션을 이용해 묶어서 전달
	 * 2. @Param 어노테이션을 이용해 파라미터로 인식
	 * 
	 * @Param("key") 자료형 변수명
	 * - SQL 중 #{key} 자리에 들어갈 값을 지정
	 */
	
}
