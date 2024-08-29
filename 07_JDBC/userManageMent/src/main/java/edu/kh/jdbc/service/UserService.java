package edu.kh.jdbc.service;

import edu.kh.jdbc.dto.User;

public interface UserService {

	/**
	 * 사용자 등록
	 * @param user
	 * @return result : 1 || 0
	 * @throws Exception
	 */
	int insertUser(User user) throws Exception;

	
	/**
	 * 아이디 중복 여부 확인
	 * @param userId
	 * @return result(1:중복O, 0:중복X)
	 * @throws Exception
	 */
	int idCheck(String userId) throws Exception;

	
	/**
	 * 로그인
	 * @param userId
	 * @param userPw
	 * @return
	 * @throws ExceptionloginUser
	 */
	User login(String userId, String userPw) throws Exception; 
}
