package edu.kh.todo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.todo.dto.Todo;
import edu.kh.todo.dto.TodoMember;

@Mapper
public interface TodoMapper {

	int idCheck(String id);

	int signUp(TodoMember member);

	TodoMember login(TodoMember member);

	List<Todo> selectTodoList(int todoMemberNo);

	int insertTodo(Todo todo);

	int updateTodo(int todoNo);

	int deleteTodo(int todoNo);

}
