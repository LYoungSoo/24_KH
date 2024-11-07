import React, { createContext, useContext } from 'react';

/* Context 객체 생성(createContext) */
const TestContext = createContext();



/* 후손 컴포넌트 */
// const GrandChild = (props) => {
const GrandChild = () => {
  // const {text} = props;

  // TestContext 에서 제공하는 값(value)을 얻어와 text 변수에 저장
  const text = useContext(TestContext);

  return(
    <>
      <h3>GrandChild Component</h3>
      <em>{text}</em>
    </>
  );
}
// <i></i> , <em></em> ==> italic 글씨체 태그


/* 자식 컴포넌트 */
// const Child = (props) => {
const Child = () => {
  // const {text} = props;

  return(
    <>
      <h3>Child Component</h3>
      {/* <GrandChild text={text}/> */}
      <GrandChild/>
    </>
  );
}

/* 부모 컴포넌트 */
const Parent = () => {
  return(

    /* Context 객체를 이용해서 하위 모든 컴포넌트에 value 제공 */
    <TestContext.Provider value='Parent 에서 전달한 값'>
      <h3>Parent Component</h3>
      {/* <Child text='Parent 에서 전달한 값'/> */}
      <Child/>
    </TestContext.Provider>
  );
}

export default Parent;