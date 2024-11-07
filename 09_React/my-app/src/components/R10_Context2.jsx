import React, { createContext, useContext, useState } from 'react';

/* Context 객체 생성 */
const Ctx = createContext();

const GrandChild = () => {

  // Context를 이용해서 제공된 값
  // ==> {"number" : number, "setNumber" : setNumber};
  const {number, setNumber} = useContext(Ctx);
  // ==> number, setNumber는 Parent의 상태 변수
  // ===> 상태 변수 값이 변하면 컴포넌트(Parent) 리랜더링 수행

  return(
    <>
      <h3>GrandChild Component</h3>
      <div>
        Parent로 전달할 값 : 
        <input type='number'
               value={number}
               onChange={e => {setNumber(e.target.value)}}
        /> 
      </div>
    </>
  );
}

const Child = () => {
  return(
    <>
      <h3>Child Component</h3>
      <GrandChild />
    </>
  );
}

const Parent = () => {

  // 상태 변수 선언
  const [number, setNumber] = useState(0);

  /*
    Context 객체를 이용해서 여러 값을 제공하고 싶을 경우
    JS 객체 {}를 이용
   */

  // const obj = {"number" : number, "setNumber" : setNumber};

  // 리액트에서 {number, setNumber} 형태로 작성하면
  // 자동으로 변수명이 key, 값이 value로 변환됨
  return(
    // <Ctx.Provider value={obj}>
    /* 해당 Provider 내부에서 사용가능한 static 느낌의 변수로 number 를 사용중이다.
       ==> 다 같은 주소의 number다 */
    <Ctx.Provider value={{number, setNumber}}>
      <h3>
        Parent Component :
        <span className='red'> {number}</span>
      </h3>
      <Child />
    </Ctx.Provider>
  );
}

export default Parent;