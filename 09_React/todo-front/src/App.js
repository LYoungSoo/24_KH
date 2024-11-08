import React, { useState } from 'react';

import TodoListContext from './contexts/TodoListContext';
import SignUp from './components/SignUp';
import Login from './components/Login';
import TodoList from './components/TodoList';

import './App.css';

function App() {

  /* 상태 변수 선언 */

  // 회원 가입 버튼 열기/닫기 제어용 변수
  const [signUpView, setSignUpView] = useState(false);

  // 로그인한 회원 정보 저장 변수
  const[loginMember, setLoginMember] = useState(null);

  /* 로그인한 회원의 할 일 목록을 저장할 변수 */
  const [todoList, setTodoList] = useState([]);

  return (
    <TodoListContext.Provider value={{loginMember, setLoginMember, todoList, setTodoList}}>

      {/* 버튼이 클릭 될 때 마다 signUpView 값을 변경(T/F) */}
      <button onClick={() => {setSignUpView(!signUpView)}}>
        {signUpView ? ('회원 가입 닫기') : ('회원 가입 열기')}
      </button>

      {/* 회원 가입 컴포넌트 */}
      <div className='signup-wrapper'>

        {/* 앞 부분이 True 면 && 뒤 구문이 실행됨 ==> 논리 연산은 비교 연산보다 우선순위가 낮음 */}
        {/* 조건식 && 결과 : 조건식이 true 이면 결과 출력 */}
        {signUpView === true && (<SignUp/>)}

      </div>

      <hr/>

      <h1>Todo List</h1>

      {/* 로그인 컴포넌트 */}
      <Login/>

      <hr/>

      {/* 로그인이 되어있을 때 TodoList 컴포넌트 출력 */}
      {/* {loginMember && (<TodoList/>)} 소괄호 넣던지 말던지 */}
      {loginMember && <TodoList/>}

    </TodoListContext.Provider>
  );
}

export default App;


/* 
  CORS(Cross-Origin Recource Sharing)
  - 서로 다른 도메인(다른 사이트/서버) 에서 리소스 요청할 수 있도록 허용하는 메커니즘

  react(80) ==> spring(8080) 자원 요청
  ===> Spring 에서 요청 허용을 하지 않아서 CORS 오류 발생
*/