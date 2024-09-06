package edu.kh.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.demo.dto.UserDto;
import edu.kh.demo.mapper.TestMapper;

// @Service
// - Service 역할(비즈니스 로직 처리)임을 명시
// - Bean 등록 (== Spring이 관리하는 객체 == IOC)

@Service
public class UserServiceImpl implements UserService {

	/*
	 * @AutoWired - 등록된 Bean 중에서 자료형이 같은 Bean을 얻어와 필드에 대입 == DI(의존성 주입)
	 */
	@Autowired
	private TestMapper mapper;

	// 사용자 이름 조회
	@Override
	public String selectUserName(int userNo) {
		return mapper.selectUserName(userNo);
	}

	// 사용자 전체 조회
	@Override
	public List<UserDto> selectAll() {

		// mapper : 의존성 주입(DI) 받은 TestMapper를 상속받아
		// 구현된 클래스로 만들어진 Bean(객체)
		return mapper.selectAll();
	}

	// userNo가 일치하는 사용자 조회
	@Override
	public UserDto selectUser(int userNo) {
		return mapper.selectUser(userNo);
	}

	// 사용자 정보 수정(DML)
	// ==> DML 수행하면 트랜잭션 제어 처리
	// @Transactional : 
	// - 해당 메서드 수행 중 RuntimeException 발생 시 rollback 수행
	// - 예외가 발생하지 않으면 메서드 종료 후 commit 수행
	@Transactional		// 설명에 Exception 처리가 참 많아서 못알아먹겠다... 무슨말인지...
	@Override
	public int updateUser(UserDto user) {
		return mapper.updateUser(user);
	}

	// 사용자 정보 삭제
	@Transactional	// 예외 발생시 rollback, 메서드 종료 시 commit 하는 트랜잭션
	@Override
	public int deleteUser(int userNo) {
		return mapper.deleteUser(userNo);
	}

	// 사용자 정보 등록
	@Transactional
	@Override
	public int insertUser(UserDto user) {
		return mapper.insertUser(user);
	}
	
	

}
