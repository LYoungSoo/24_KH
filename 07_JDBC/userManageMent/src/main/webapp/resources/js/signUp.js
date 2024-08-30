// 아이디가 userId, check인 요소를 얻어와 변수에 저장
const userId = document.querySelector("#userId");
const check = document.querySelector("#check");
const signUpBtn = document.querySelector("#signUpBtn");


// #userId 에 입력(input)이 되었을 때
userId.addEventListener("input", e => {
  // 비동기로 아이디 중복 여부를 얻어오는 ajax 코드 작성 예정

  // - ajax : 서버와 비동기 통신을 하기위한 JS 기술
  // - fetch() API : JS에서 제공하는 asax 를 쉽게 쓰는 코드

  fetch("/signUp/idCheck?userId="+ e.target.value)
  .then (resp => resp.text())   // 응답 값을 text로 변환
  .then (result => {
    // result : 첫 번째 then에서 resp.text()를 통해 변환된 값
    // console.log(result);

    if(result == 0) {   // 중복 X ==> 사용 가능
      check.classList.add("green");   // green 클래스 추가
      check.classList.remove("red");  // red   클래스 제거
      check.innerText = "사용 가능한 아이디 입니다";
      signUpBtn.disabled = false;
    } else {
      check.classList.add("red");
      check.classList.remove("green");
      check.innerText = "이미 사용중인 아이디 입니다";
      signUpBtn.disabled = true;
    }

  });
  
});

document.addEventListener("input", e => {
  const list = document.querySelectorAll(".a");

  for(let input of list){
    if(input.value.trim().length == 0){
      signUpBtn.disabled = true;
      return;
    }
  }
  // list.forEach(input => {  })

  if(result == 0) signUpBtn.disabled = false;
});


// 아 진짜 검사할거 개많고 순서 어렵고
// 머리아파서 생각이 안난다....
// 2년 젊은 영수 데려와
// 안드로이드 장인 데려와...