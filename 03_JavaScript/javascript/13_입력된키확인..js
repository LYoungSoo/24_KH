// 준비한 이미지 파일명을 저장할 배열
const images = ["cat1.jpg","cat2.jpg","cat3.jpg","cat4.jpg"];
const key = document.querySelectorAll(".block");

let index; // 인덱스를 저장할 변수

/* 문서 전체에 keydown 이벤트 리스너 추가*/
document.addEventListener("keydown", function (e) {
  // e : 이벤트 객체
  // e.key : 입력한 key
  // e.code : 키보드의 물리적 입력값(버튼값) = q, Q, ㅂ, ㅃ 모두 "KeyQ"
  console.log(e, e.key, e.code);

  switch (e.code) {
    // case "KeyQ": key[0].style.backgroundColor = "gray"; break;
    case "KeyQ": key[0].style.backgroundImage = "url(../images/dogs/dog4.jpg)"; break;
    case "KeyW": key[1].style.backgroundColor = "gray"; break;
    case "KeyE": key[2].style.backgroundColor = "gray"; break;
    // case "KeyR": key[3].style.backgroundColor = "gray"; break;
    case "KeyR": key[3].style.backgroundImage = `url(../images/cats/${images[3]})`; break;
  }
  key[1].style.color="red";
});
  // 문자열.toUpperCase() : 문자열을 대문자로 변환
  // 문자열.toLowerCase() : 문자열을 소문자로 변환

  /* ver2 */
document.addEventListener("keyup", function (e) {
  console.log(e, e.key, e.code);
  switch (e.key.toLowerCase()) {
    case "q": index = 0; break;
    case "w": index = 1; break;
    case "e": index = 2; break;
    case "r": index = 3; break;
    default : return;
  }
  key[index].style.color="transparent";
  key[index].style.backgroundColor = "white";
  key[index].style.backgroundImage = null;
  key[index].style.backgroundImage = "none";
});
// 자동완성으로 안되어서 코드가 잘못된줄 알았는데
// ai가 존나 빡대가리라서 
// 다른거만 추천 계속해줘서 안되었던것
// 하던대로 하자


/* 이미지가 마우스 따라 다니게 하기 */
// 숨겨놓은 이미지
const cursorImage = document.querySelector("#cursorImage");

// 문서 전체에 "mousemove"(마우스가 움직였을 때) 감지 추가
document.addEventListener("mousemove", function(e) {
  // console.log(e);
  // e.pageX : 가로 방향 마우스 위치(왼쪽에서 떨어진 px)
  // e.pageY : 세로 방향 마우스 위치(왼쪽에서 떨어진 px)
  cursorImage.style.left = `${e.pageX}px`;
  cursorImage.style.top = `${e.pageY}px`;
});
