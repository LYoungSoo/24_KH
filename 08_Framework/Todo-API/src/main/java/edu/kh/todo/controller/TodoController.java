package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.todo.dto.Todo;
import edu.kh.todo.dto.TodoMember;
import edu.kh.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* 
	CORS(Cross-Origin Recource Sharing)
	- 서로 다른 도메인(다른 사이트/서버) 에서 리소스 요청할 수 있도록 허용하는 메커니즘
	
	react(80) ==> spring(8080) 자원 요청
	===> Spring 에서 요청 허용을 하지 않아서 CORS 오류 발생
*/

/* 
 * @CrossOrigin
 *  - 해당 컨트롤러로 매핑되는 외부 요청을 허용하는 어노테이션
 *  - () 내부에 자세한 필터링 작성 가능
 *  
 *  - origins : 요청을 허용할 외부 요청 주소
 *  - allowedHeaders : 허용할 요청 헤더
*/
@CrossOrigin(origins = {"http://localhost"} , allowedHeaders = "*")

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("todo")
public class TodoController {

	private final TodoService service;
	
	/** 아이디 중복 검사
	 * @param id
	 * @return 중복 : 1, 사용 가능 : 0
	 */
	
	@GetMapping("idCheck")
	public ResponseEntity<Integer> idCheck(@RequestParam("id") String id) {
		return ResponseEntity.ok(service.idCheck(id));
	}
	
	/** 회원 가입 
	 * @param member
	 * @return 성공 : 1, 실패 : 0
	 */
	@PostMapping("signUp" )
	public ResponseEntity<Integer> signUp(@RequestBody TodoMember member) {
		return ResponseEntity.ok(service.signUp(member));
	}
	
	
	/** 로그인
	 * @param member
	 * @return 성공 : 회원 정보/todoList, 실패 : null
	 */
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody TodoMember member){
		return ResponseEntity.ok(service.login(member));
	}
	
	
	/** 할 일 조회
	 * @param todo
	 * @return todoList
	 */
	@PostMapping("selectTodoList")
	public ResponseEntity<List<Todo>> selectTodoList(@RequestBody TodoMember member) {
		List<Todo> todoList = service.selectTodo(member.getTodoMemberNo());
		System.out.println("ddd");
		log.debug(todoList.toString());
		return ResponseEntity.ok(todoList);
	}
	
	/** 할 일 추가
	 * @param todo
	 * @return 성공 todoNo(시퀀스) : 실패 0
	 */
	@PostMapping("")
	public ResponseEntity<Integer> insertTodo(@RequestBody Todo todo) {
		return ResponseEntity.ok(service.insertTodo(todo));
	}
	
	
	/** 할 일 완료 여부 수정
	 * @param todo
	 * @return 성공 1 : 실패 0
	 */
	@PutMapping("")
	public ResponseEntity<Integer> updateTodo(@RequestBody Todo todo) {
		return ResponseEntity.ok(service.updateTodo(todo.getTodoNo()));
	}
	
	
	/** 할 일 삭제
	 * @param todoNo
	 * @return 성공 1 : 실패 0
	 */
	@DeleteMapping("")
	public ResponseEntity<Integer> delete(@RequestBody Todo todo) { // delete 요청 시 본문에 담겨있는 json 데이터를 받아위해 Todo
		return ResponseEntity.ok(service.deleteTodo(todo.getTodoNo()));
	}
	
}
