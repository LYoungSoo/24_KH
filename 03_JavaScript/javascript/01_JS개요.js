// .js 파일 내부를 <script> 태그 내부라고 생각

// 함수 선언(기능)
function externalFn() {
  // 함수 정의(기능 동작방식)
  alert("External 확인 버튼이 클릭됨");
  alert("이번엔 alert 두번");
}


// JS 맛보기
// button 클릭시 body 태그의 글자색, 배경색을 변경
const body = document.querySelector("body"); // HTML 문서의 body 태그 부분을 document에 연결

// 라이트모드 변경 함수(기능) 선언
function lightMode() {
  // 함수 정의
  body.style.color = "black";
  body.style.backgroundColor = "white";
}
// 다크모드 변경 함수(기능) 선언
function darkMode() {
  body.style.color = "white";
  body.style.backgroundColor = "gray";
}