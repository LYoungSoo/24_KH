import React, {useState} from "react";

// 함수형 컴포넌트
// 1. 
// 2. 
// 3. 만든 함수를 export default 지정하기
function FunctionEx() {

  // const obj = {a:10, b:20};
  // const {a,b} = obj;
  // a == 10, b == 20
  // 구조 분해 할당

  // const array = [11,22,33];
  // const [first, second, third] = array;
  // first == 11, second == 22, third == 33;

  // 구조 분해 할당
  // - 배열, 객체에 저장된 값을 각각의 변수에 꺼내서 할당하는 기술
  // ex) 객체
  // const obj = {a:10, b:20};
  // const {a,b} = obj;
  // a == 10, b == 20

  // ex) 배열
  // const arr = [100, 200];
  // const [first, second] = arr;
  const [count, setCount] = useState(0); // [0, count 값을 바꿀 수 있는 메서드]

  // 클릭 시 count 1 감소
  const clickHandler = () => {
    setCount( () => {return count - 1} )
  }

  return (
    <>
      <h2>함수형 컴포넌트 입니다~~</h2>
      <h2>{count}</h2>
      <button onClick={clickHandler}>1 감소</button>
    </>
  );
}
export default FunctionEx;