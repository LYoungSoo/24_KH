/** break 확인 */
function check1() {
  // 1
}
/** break 확인 */
function check1() {
  // 1부터 10까지 1씩 증가하는 반복문 ==> 단, 5를 출력한 후 멈춤
  for (let num = 1; num <= 10; num++) {
    console.log(num);
    if (num === 5) {
      break;
    }
  }
}

/** 무한 반복 멈추기 */
function check2() {
  let sum = 0;  //합계 저장용 변수

  // while문은 () 내 조건식이 true일 때 반복을 수행
  //  ==> true라고 작성하면 조건식이 false가 되는 경우가 없음
  //  ==> 무한 반복 === 루핑 == 김현호 교수님이 책 모서리로 내려치러 오실만한 행동
  // * 무한 반복하는 코드에는 break를 포함한 if문을 작성해서 종료 조건을 만들어 두어야만 한다!!!
  while (true) { //이거 영구반복 아니냐
    const value = prompt("숫자 입력"); //취소 ==> null

    // 취소 클릭 시 반복 종료
    if (value === null) break;

    // 확인 클릭 시 sum에 입력한 숫자를 누적
    sum += Number(value);
  }
  alert("합계 : " + sum);
}

/** continue 확인 */
function check3() {
  // 1부터 20까지 1씩 증가하는 반복문
  // 단, 3의 배수는 건너뛰기

  for (let num = 1; num <= 20; num++) {
    if (num % 3 === 0) continue;
    console.log(num);
  }
}

/** continue + 중첩 반복문에서의 분기문 */
function check4() {
  // 123456789를 5줄 출력. 단, n번 줄에서는 n 제외 (1번 줄에서는 1 제외, ...)
  for (let row = 1; row <= 5; row++) {  // 행 제어
    let str = ''; // 문자열 형태로 누적하기 위한 변수 선언
    for (let col = 1; col <= 9; col++) {  // 열 제어

      // n번 줄인 경우 같은 n열 제외
      if (col === row) continue;  //감싸고 있는 for문만 건너뜀
      str += col + " "  // dldjTmrl
    }
    console.log(str); // 한 줄 출력
  }
}

/* UP & DOWN GAME */
function startGame() {

  // 맞춰야되는 난수(1~200) 발생
  const answer = Math.floor(Math.random() * 500) + 1;

  // 테스트 후 삭제
  console.log("정답 : ", answer);

  // --------------------------------------------

  // 정답 시도 횟수를 세기 위한 변수 선언
  let count = 0;
  let up = 0;
  let down = 500;


  // --------------------------------------------
  let str = "1부터 200사이 숫자 입력"
  while (true) { // 무한 반복
    let far = '';
    // 숫자만 정상 입력한다고 가정
    let input = prompt(str);
    console.log(input);

    if (input === null) {
      alert(`${count}회 시도만에 취소누름.`)
      break;
    }

    /* 숫자 입력 후 확인 클릭 시 */
    const value = Number(input); // 입력 받은 값 숫자로 변환.

    /* 잘못 입력한 경우 */
    // NaN (Not a Number : 숫자가 아니다)
    // isNaN(값) : 값이 NaN이면 true
    if (isNaN(value)) { // 숫자가 아닌 값을 입력한 경우
      alert("숫자만 입력해 주세요");
      continue;
    } else if (value < 1 || value > 500) { // 범위 초과
      alert("1 ~ 200 사이 값만 작성해 주세요");
      continue;
    }

    // 정답을 맞추기 위한 시도를 했기 때문에 count 증가
    count++;
    /* 정답인 경우 */
    if (value === answer) {
      alert(`정답 !!! (${answer}) / 시도 횟수 : ${count}`);
      break;
    }
    if ((value - answer) / 100 < -1 || (value - answer) / 100 > 1) far = "+";

    /* 정답이 아닌 경우 */
    if (value < answer) {
      if (up < value) up = value;
      str = `${value} [UP${far}] / 시도 횟수 : ${count}회 / ${up} ~ ${down}`;
    } else {
      if (down > value) down = value;
      str = `${value} [Down${far}] / 시도 횟수 : ${count}회 / ${up} ~ ${down}`;
    }
  }
}