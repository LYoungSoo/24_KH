/* 화면에 존재하는 .box, .color-input 얻어와 변수에 저장 */

const boxList = document.querySelectorAll(".box");
const inputList = document.querySelectorAll(".color-input");
// const opacity = document.getElementsByClassName(".opacity-container");


/* 클릭할 버튼 요소 얻어오기 */
const changeBtn = document.querySelector("#changeButton");

/* 변경 버튼 클릭 시 */
changeBtn.addEventListener("click", function () {

  /*
    체크된 radio 버튼을 찾아 얻어오는 코드를 addEventListener() 안에 작성하는 이유 
    - 밖 : 페이지 로딩 시점에 체크된 요소 찾기 ==> 무조건 없음. 이후 수정하는 명령어 별도로 사용해야함
    - 안 : changeBtn 클릭 시점에 선언되어, 체크되어있는 요소 찾기 ==> 있거나 없음.
  */

  // 상태 선택자 : checked ==> 체크된 요소를 선택
  const checkBtn = document.querySelector("[name = opacity-btn]:checked");  //선택되지 않으면 null 반환
  console.log(checkBtn.value);

  let op;
  if(checkBtn === null) op = 1; // 체크가 안되어 있으면 1
  else                  op = checkBtn.value; // 체크된 값

  for (let i = 0; i < boxList.length; i++) {
    // input에 작성된 값을 얻어와 같은 index번째 box 요소에 배경색으로 대입
    boxList[i].style.backgroundColor = inputList[i].value;

    // 투명도 적용
    boxList[i].style.opacity = op;
  }
});


/* 
  input 관련 요소에 작성된 값 ==>  value

  나머지 요소 (div,p,span,...) 에 작성된 내용 ==> innerText, innerHTML
*/