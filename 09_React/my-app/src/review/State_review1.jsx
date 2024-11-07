import React, { useState } from 'react';

const StateReview1 = () => {


  // 상태 변수 : 값이 변하면 컴포넌트를 리랜더링하는 변수
  const [str, setStr] = useState('');   // 구조 분해 할당을 이용

  // 상태 변수로 배열 선언
  const [strList, setStrList] = useState([]);

  // 배열 사용 연습
  const tempList = ['aaa', 'bbb', 'ccc', 'ddd'];

  const temp2 = tempList.map((item, index) => {
    return `${index} : ${item}`;    // 0 : aaa
  });

  console.log(tempList);
  console.log(temp2);

  /**
   * input에 입력된 값을 화면에 그대로 출력
   * @param {*} e : 이벤트 객체(발생한 이벤트 관련 정보가 담긴 객체)
   */
  const inputStringHandler = (e) => {
    setStr(e.target.value);   // 상태 변수 str 값을 입력된 값으로 변경
  }


  /**
   * 엔터가 입력된 경우 수행할 함수
   * @param {*} e : 이벤트 객체(어떤 키가 입력 되었는지 포함)
   */
  const enterHandler = (e) => {
    console.log(e.key)

    if(e.key === 'Enter') {     // 엔터 키가 입력된 경우

      // 상태 변수 strList의 값을 변경
      // setStrList( [e.target.value] )
      setStrList( [...strList, str] )

      e.target.value = '';      // input에 작성된 값 삭제
      setStr('');               // 상태 변수에 '' 대입 ==> 상태변수 값 변경으로 인해 re rendering
    }
  }

  return(
    <div>
      <h2>State 복습 1</h2>
      <div>
        <label htmlFor="inputString">문자열 입력 : </label>
        <input type="text"
               id="inputString"
               onChange={inputStringHandler}
               onKeyUp={enterHandler}
        />
      </div>

      {/* 위 input에 입력된 값이 출력됨 */}
      <h3> 입력된 값 : {str} </h3>
      
      {/* 버튼 클릭 시 strList 값에 [] 를 대입 ==> 빈 배열로 바뀌었으므로 리 랜더링 ==> 초기화 */}
      <button onClick={() => {setStrList([])}}>초기화</button>

      <ul>
        <li>문자열 입력 후 엔터 입력 시 ul 태그에 누적</li>

        {/* {tempList.map( (item, index) => {
          return(
            <li>{index} : {item}</li>
          );
        })} */}

        {/* strList에 저장된 값을 이용해 li태그를 만들어 출력 */}
        {strList.map((item, index) => {
          return(
            <li key={index}>{item}</li>
          );
        })}
      </ul>

    </div>
  );
}

export default StateReview1;

/* 
  전개 연산자(...)
  - 배열 또는 객체의 가장 바깥쪽 괄호를 푸는 연산자

  - 배열 : 요소 추가, 복사, 배열 병합
  - 객체 : 복사, 병합, 속성 덮어쓰기(객체 값 중 일부만 변경)

  ex) const temp = [100, 200, 300];
      ...temp; ==> 100,200,300

      const obj = {"name":홍길동, "age":20};
      ...obj;  ==> "name":홍길동, "age":20


  // 베열.map( (item, index) => {} )
  // - 매개변수에 작성된 함수에서 반환된 값을 이용해 새로운 배열을 만드는 배열 메서드

  // - 향상된 for문 처럼 배열 요소 순차 접근

  // - item  : 요소를 하나씩 저장하는 변수
  // - index : 현재 인덱스

*/