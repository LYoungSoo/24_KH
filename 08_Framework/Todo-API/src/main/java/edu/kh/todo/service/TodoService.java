package edu.kh.todo.service;

import java.util.List;
import java.util.Map;

import edu.kh.todo.dto.Todo;
import edu.kh.todo.dto.TodoMember;

public interface TodoService {

	int idCheck(String id);

	int signUp(TodoMember member);

	Map<String, Object> login(TodoMember member);

	List<Todo> selectTodo(int todoMemberNo);
	
	int insertTodo(Todo todo);

	int updateTodo(int todoNo);

	int deleteTodo(int todoNo);


}
