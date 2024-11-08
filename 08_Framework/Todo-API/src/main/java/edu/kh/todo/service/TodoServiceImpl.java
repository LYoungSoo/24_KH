package edu.kh.todo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.todo.dto.Todo;
import edu.kh.todo.dto.TodoMember;
import edu.kh.todo.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{
	
	private final TodoMapper mapper;

	@Override
	public int idCheck(String id) {
		return mapper.idCheck(id);
	}

	@Override
	public int signUp(TodoMember member) {
		return mapper.signUp(member);
	}

	@Override
	public Map<String, Object> login(TodoMember member) {
	TodoMember loginMember = mapper.login(member);
		
		Map<String, Object> map = new HashMap<>();
		map.put("loginMember", loginMember);

		if(loginMember != null) {
			List<Todo> todoList = mapper.selectTodoList(loginMember.getTodoMemberNo());
			map.put("todoList", todoList);
		}
		
		return map;
	}
	
	@Override
	public List<Todo> selectTodo(int todoMemberNo) {
		return mapper.selectTodoList(todoMemberNo);
	}

	@Override
	public int insertTodo(Todo todo) {
		int result = mapper.insertTodo(todo);
		return result > 0 ? todo.getTodoNo() : 0;
	}

	@Override
	public int updateTodo(int todoNo) {
		return mapper.updateTodo(todoNo);
	}

	@Override
	public int deleteTodo(int todoNo) {
		return mapper.deleteTodo(todoNo);
	}
	
}
