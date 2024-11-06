import './App.css';
import ClassComponent from './components/R01_ClassComponent';
// ==> R01_ClassComponent.jsx 에서 export 된 HTML 요소를 Class Component 라고 부르겠다
// ===> <ClassComponent/> 형태로 사용 가능

// import FunctionEx from './components/R02_FunctionComponent';
import FunctionComponent from './components/R02_FunctionComponent';

// import 여기서 사용할 이름 from '경로';
import Props1 from './components/R03_Props1';
import Props2 from './components/R04_Props2';
import Props3 from './components/R05_Props3';

import State1 from './components/R06_State1';
import State2 from './components/R07_State2';
import State3 from './components/R08_State3';

// 기본적으로 //, /* */ 주석 사용 가능(JS니까!!)
// 단, HTML 코드가 작성되는 영역에서는 {/*  */} 주석 사용

function App() {
  return (
    <>
      {/* JSX(JS에 HTML 코드 포함) 주석 */}

      {/* 클래스형 컴포넌트 */}
      {/* <ClassComponent/> */}

      {/* 함수형 컴포넌트 ==> 함수명으로 import 해서 사용해도 되는건지, import 문 자체가 메서드느낌인지 */}
      {/* <FunctionEx/> */}
      {/* <FunctionComponent/> */}

      {/* Props1 */}
      <Props1 num='1' name='홍길동'/>
      <Props1 num='2' name='고길동'/>
      <Props1/>

      <hr/>

      <Props2 name='강감찬'
              age='72'
              address='낙성대'
              gender='남자'/>

      <Props2 name='고영희'
              age='8'
              address='강남'
              gender='여자'/>

      <hr/>

      <Props3 productName='라면' price='3000'/>

      {/* State */}
      <State1/>
      
      <br/>

      <State2 init='0'/>

      <br/>

      <State3/>

    </>
  );
}

export default App;


/* 
<div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
*/