import React, {Component} from "react";
// ==> node_modules 폴더에서 'react' 관련 모듈 중
// React, Component를 얻어와 사용
// (사용시 이름도 React, Component)

/*
  [클래스형 컴포넌트 만들기]
  1. extends Component (import 받은 Component 상속하기)
  2. render() 함수 작성하기(필수)  으으으으 유니티 생각나 으으으으으으으으으으으으으으으으으으
  3. 만든 클래스를 export default 
*/

class ComponentEx extends Component {

  // 생성자 함수
  constructor(props) {
    super(props);
    this.state = {count:0};
  }

  /* 이벤트 리스너 : 이벤트 발생 감지기 */
  /* 이벤트 핸들러 : 이벤트 발생 감지 시 할거 */
  clickHandler = () => {
    this.setState({ count : this.state.count + 1});
  }

  // renderer() 함수
  // - return 되는 HTML 코드를 화면에 출력하는 함수
  render() {
    return(
      <>
        {/* <></> == frangment(해석되면 사라짐, 묶음 용도).
            내부에 랜더링(이미지처럼 표시) 할 코드를 작성하면 된다 */}
        <h2>클래스형 컴포넌트 입니다</h2>
        <h2>{this.state.count}</h2>
        <button onClick = {this.clickHandler}>1 증가</button>
      </>
    );
  }
}

export default ComponentEx;
// ==> 다른 js/jsx 에서 import 해서 사용 가능!