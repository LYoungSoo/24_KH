import React, { useState } from 'react';

/* 초기 값 얻어와 1씩 증가/감소 */
const StateEx2 = (props) => {

  // 상태(State) 변수 선언
  // 상태 변수 count 값이 변할 때 마다 컴포넌트 re rendering
  const[count, setCount] = useState(props.init);

  // + 버튼 클릭 시
  const plusHandler = () => {
    setCount(Number(count) + 1);  // 1 증가
  }

  // - 버튼 클릭 시
  const minusHandler = () => {
    setCount(Number(count) - 1);  // 1 감소
  }

  
  return(
    <div className='count-container'>

      <button className='minus-btn btn'
              onClick={minusHandler}>-</button>

      <h1>{count}</h1>
      
      <button className='plus-btn btn'
              onClick={plusHandler}>+</button>
      
    </div>
  );

}
export default StateEx2;