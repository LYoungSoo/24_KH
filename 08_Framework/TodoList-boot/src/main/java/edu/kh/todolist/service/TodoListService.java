package edu.kh.todolist.service;

import java.util.Map;

import edu.kh.todolist.dto.Todo;

public interface TodoListService {

	/**
	 * 할 일 목록 조회 + 완료된 할 일 개수
	 * @return map
	 */
	Map<String, Object> selectTodoList();

	/**
	 * 할 일 추가
	 * @param todo
	 * @return result
	 */
	int todoAdd(Todo todo);

	/**
	 * 할 일 상세조회
	 * @param todoNo
	 * @return todo
	 */
	Todo todoDetail(int todoNo);

	/**
	 * 완료 여부 변경
	 * @param todoNo
	 * @return result
	 */
	int todoComplete(int todoNo);
	
	/**
	 * 할 일 수정
	 * @param todo
	 * @return result
	 */
	int todoUpdate(Todo todo);
	
	/**
	 * 할 일 삭제
	 * @param todoNo
	 * @return result
	 */
	int todoDelete(int todoNo);


}
