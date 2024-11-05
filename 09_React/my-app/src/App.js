import './App.css';
import ClassComponent from './components/R01_ClassComponent';
// ==> R01_ClassComponent.jsx 에서 export 된 HTML 요소를 Class Component 라고 부르겠다
// ===> <ClassComponent/> 형태로 사용 가능

// import FunctionEx from './components/R02_FunctionComponent';
import FunctionComponent from './components/R02_FunctionComponent';

// 기본적으로 //, /* */ 주석 사용 가능(JS니까!!)
// 단, HTML 코드가 작성되는 영역에서는 {/*  */} 주석 사용

function App() {
  return (
    <>
      {/* JSX(JS에 HTML 코드 포함) 주석 */}

      {/* 클래스형 컴포넌트 */}
      <ClassComponent/>

      {/* 함수형 컴포넌트 ==> 함수명으로 import 해서 사용해도 되는건지, import 문 자체가 메서드느낌인지 */}
      {/* <FunctionEx/> */}
      <FunctionComponent/>
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