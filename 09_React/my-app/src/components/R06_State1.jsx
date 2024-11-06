import React, { useState } from 'react';    // imrs

/*
  State : 컴포넌트의 상태(컴포넌트 단위의 전역 변수)

  useState : 함수형 컴포넌트에서 State 사용을 위해서 import 하는 객체
*/
const StateEx1 = () => {

  /*
    일반 변수 사용 시 ==> test 변수에 저장된 값을 A,B 변하고 있으나,
    화면은 리랜더링 되지 않음 (단순히 변수 값이 변한 것이라고 인식)

  */
  let test1 = 'A';

  /* 상태 변수(State) 이용 */
  const [test, setTest] = useState('A');

  // useState('A') 의 반환값 === [  'A' , setTest  ]
  //                              초기값, 0번 인덱스의 값을 바꿀 수 있는 함수(Setter)

  // 클릭 시 수행되는 함수(이벤트 핸들러)
  const clickHandler1 = () => {
    // A <-> B 왔다 갔다 동작

    if(test1 === 'A') test1 = 'B';
    else test1 = 'A';
    console.log("test(let) : ", test1)
  }

  // 클릭 시 수행되는 함수(이벤트 핸들러)
  const clickHandler = () => {
    // A <-> B 왔다 갔다 동작

    if(test === 'A') setTest('B');
    else setTest('A');
    console.log("test(State) : ", test)
  }

  return(
    <>
      {/* 초기값으로 랜더링 된 이후 고정상태가 기본값임 */}
      <h1>{test1}</h1>
      <button onClick={clickHandler1}>1. Change!!</button>

      <h1>{test}</h1>
      <button onClick={clickHandler}>2. Change!!</button>
    </>
  );
}

export default StateEx1;