/* 계산 하기 */
const calc = document.querySelector("#calc");

calc.addEventListener("click", () => {

  // 계산 버튼 클릭 시점에 존재하는 .input-number 모두 얻어오기
  const numbers = document.querySelectorAll(".input-number");

  let sum = 0;

  for (let i = 0; i < numbers.length; i++) {
    sum += Number(numbers[i].value);
  }

  alert(`합계 : ${sum}`);
});

/* 추가 버튼 */
const addBtn = document.querySelector("#add");  // 버튼

// 감싸는 요소
const container = document.querySelector(".container");

addBtn.addEventListener("click", () => {

  // 추가 버튼 클릭 시 div, input, span 요소를 생성해서 알맞게 조립 후 .container의 마지막 자식으로 추가

  // 1) div   만들기
  const div = document.createElement("div");
  div.className = "row";  // row 클래스 추가

  // 2) input 만들기
  const input = document.createElement("input");
  input.type = "text";
  input.className = "input-number";

  // 3) span  만들기
  const span = document.createElement("span");
  span.className = "remove-row";
  span.innerHTML = "&times;";   // HTML 특수문자는 innerHTML을 이용해야 한다
  // 여기서 addEventListener로 추가를 해주어야 지울수가 있음
  // x버튼(span)이 만들어 질 때 X버튼이 클릭되었을 때 수행할 동작을 같이 정의해줌
  span.addEventListener("click", () => {
    // 클릭된 X버튼의 부모(div.row)를 remove하겠다!
    span.parentElement.remove();
  });
  // ***************

  // 4) 조립하기
  // - 요소.append(A,B,C) : 마지막 자식으로 추가
  div.append(input, span);

  // 5) 조립된 div를 .container의 마지막 자식으로 추가
  container.append(div);





});


  //처음 로딩될 때 있던 X만 판정을 받고
  // 추가된것들은 안지워지고 X도 안됨

  // 아아아아아아아 밖에 쓰면 안되는구나
  // 최외곽에 적은 코드라서 페이지 로딩 시점에 존재하는 X버튼에만 적용이 된다.
/*   const removeBtn = document.querySelector(".remove-row");

  document.querySelector(".remove-row").addEventListener("click", () => {
    document.querySelector(".row").remove();
  }); */


  // 페이지 로딩 시점에 존재하는 .remove-row를 모두 얻어오기
// -> 처음에 x버튼이 없어서 아래 for문 실행 X
/* 
  const xBtns = document.querySelectorAll(".remove-row");
  for(let i=0 ; i < xBtns.length ; i++){
    xBtns[i].addEventListener("click", ()=>{
      console.log("x클릭됨")
    })
  }
*/