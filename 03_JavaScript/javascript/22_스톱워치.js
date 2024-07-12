/* 스톱워치 관련 요소 모두 얻어오기 */
const display = document.querySelector("#display");
const startBtn = document.querySelector("#startBtn");
const recordBtn = document.querySelector("#recordBtn");
const resetBtn = document.querySelector("#resetBtn");
const recordContainer = document.querySelector("#recordContainer");

// [0] : 분 , [1] : 초, [2] : 1/100초
const timeList = document.querySelectorAll("#display > span");

let count = 0; // 1/100초 마다 1씩 증가한 값을 저장할 변수
let currentInterval;  // START 버튼을 눌렀을 때 반복되는 setInterval을 저장할 용도의 변수

// =====================================================================

/* Start 버튼 클릭 시 10ms 마다 count 1 증가 + 화면 시간 변경 */
startBtn.addEventListener("click", () => {
  if (startBtn.innerText === "PAUSE") {
    clearInterval(currentInterval); // 실행중인 경우 중복실행 방지!!!
    startBtn.innerText = "START";
    // 일시 정지 상태가 유지 되어야 하기 때문에 이벤트 핸들러를 여기서 종료 시켜 아래 setInterval()을 수행 못하게 함!
    // RECORD 버튼 활성화를 비활성화로 변경
    recordBtn.disabled = true;
    return;
  }

  currentInterval = setInterval(() => {
    count++;
    output();
  }, 10);

  // START 버튼 클릭 시 버튼의 내용을 PAUSE로 변경
  startBtn.innerText = "PAUSE";

  // RECORD 버튼 비활성화를 활성화로 변경
  recordBtn.disabled = false;
});


/* 시계 화면 출력 함수 */
function output() {
  // 화면에 출력될 시간을 저장할 변수
  let minute = Math.floor(count / 100 / 60);
  let second = Math.floor(count / 100 % 60);
  let ms = count % 100;


  // 0을 붙이는 함수 호출
  minute = attachZero(minute);
  second = attachZero(second);
  ms = attachZero(ms);

  // 화면 출력
  // minute에 저장된 값과 화면에 출력된 "분"이 다른 경우 ==> 1분 증가
  if (minute != timeList[0].innerText) {
    timeList[0].innerText = minute;
  }

  // second에 저장된 값과 화면에 출력된 "초"가 다른 경우 ==> 1초 증가
  if (second != timeList[1].innerText) {
    timeList[1].innerText = second;
  }

  timeList[2].innerText = ms;
}

/** 전달 받은 수가 10 미만인 경우 앞에 "0" 붙여서 반환  */
function attachZero(num) {
  if (num < 10) return "0" + num;
  return num;
}

/*
RESET 버튼이 클릭 되었을 때
- 0.01초 마다 반복되는 setInterval()을 멈춤
- 화면에 출력된 시간을 00:00.00으로 초기화
*/
resetBtn.addEventListener("click", () => {
  /*
  [window.] clearInterval( setInterval()이 저장된 변수 )
  - 전달 인자에 작성된 setInterval을 멈춤
  */
  clearInterval(currentInterval);
  count = 0;
  output();
  startBtn.innerText = "START";
  recordContainer.innerHTML = '';
  // RECORD 버튼 활성화를 비활성화로 변경
  recordBtn.disabled = true;
  i = 1;
});

//=====================================================================================
/* RECORD 버튼 클릭 시 현재 출력되는 시간을 얻어와 li 태그로 만들어 #recordContainer의 첫 번쨰 자식으로 추가 */

let i = 1;
recordBtn.addEventListener("click", () => {
  // #display에 작성된 내용 얻어오기

  // innerHTML : HTML 문서에 작성된 내용 그대로 얻어옴
  // 태그와 함께 텍스트를 가져오거나, 해당 요소에 태그와 함께 텍스트 지정
  // console.log(display.innerHTML);

  // innerText : 태그, 주석 무시하고 Text(내용)만 얻어옴
  // 순수 텍스트를 가져오거나, 해당 요소에 텍스트 지정
  // console.log(display.innerText);

  // textContent : Text를 얻어오는데 공백 문자도 포함
  // 텍스트를 가져오되, 화면에 보이는대로가 아니라 소스에 있는대로 가져옴 
  // (화면에서 감춘 요소에서도 내용을 가져올 수 있고, 소스에 공백이 여러 개일 경우 그 공백도 모두 가져옴)
  // console.log(display.textContent);


  // 1번이 맨 위고 아래로 생성되는
  // recordContainer.innerHTML += `<li>${i}. ${display.innerText}</li>`;

  // 1번이 맨 아래
  /* li 태그 생성 */
  const li = document.createElement("li");

  // 내용으로 현재 출력 시간 대입
  li.innerText = i + '. ' + display.innerText;

  // #recordContainer의 첫 번째 자식으로 추가
  recordContainer.prepend(li);
  // recordContainer.apppend(li); //이걸로 하면 1번이 맨위고 생성은 아래로

  i++;
});

