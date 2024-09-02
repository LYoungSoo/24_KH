/* 제목이 작성되지 않은 경우 #addForm 제출 막기 */

const addForm = document.querySelector("#addForm");
const title = document.querySelector("[name=title]");

// addForm이 제출 될때
addForm.addEventListener("submit", e => {

  // e : 이벤트 객체

  // 제목 입력 값 가져와서 양쪽 공백 제거
  const input = title.value.trim(); 

  // 제목이 입력되지 않았을 때
  if(input.length === 0){

    e.preventDefault(); // form 제출 이벤트 막기

    alert("제목을 입력 해주세요");
    title.focus();
  }

});