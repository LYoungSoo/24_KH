/* 전역 변수 (JS 전체에서 공용으로 사용할 변수) */

// 잔액이 출력되는 span 요소
const output = document.getElementById("output");
// 금액이 입력되는 input 요소
const amount = document.getElementById("amount");

// 잔액 기록 변수(초기값 10000)
let balance = 10000;

// 비밀번호 저장 변수(초기 비밀번호 1234)
let password = "1234"; // (비밀번호 변경 기능 추가)

// ---------------------------------------------------

// 함수 내부에 작성되지 않은 코드 ==> HTML 페이지 로딩 시 자동으로 해석되어 수행된다!
output.innerText = balance;

// 아이디가 amount인 input요소에 작성된 값을 삭제
amount.value = '0';  // 빈칸을 대입해서 삭제하는 효과

function test() {
  // prompt 연습

  // prompt("내용")
  // - alert()처럼 알림창이 브라우저에 열리면서
  //   추가적으로 문자열 입력칸 + 확인 + 취소 버튼이 구성된 형태
  // - 확인 클릭 : 입력한 내용 반환
  // - 취소 클릭 : null 반환

  const pw = prompt("비밀번호를 입력하세요");
  console.log("password : ", pw);

  if (pw === null) { // 취소를 누른 경우
    alert("비밀번호 입력 취소됨");
    return;
  }

  // pw       : 입력 받은 값
  // password : 전역 변수 ("1234")
  if (pw !== password) { // 입력된 비밀번호가 password와 같지 않다면
    alert("비밀번호 불일치");
    return;
  }

  // 입력한 비밀번호가 password와 일치한 경우
  if (pw === password) {
    alert("출금 기능 수행");
  }
}


// 금액에 숫자만 입력이되어야하는데?
// 금액의 범위는 양수만.
// 입력하면 amount.val 초기화

/** 입금 */
function deposit() {

  // 입력된 금액을 읽어와 숫자로 변환
  const value = Number(amount.value);

  // 전역 변수 balance(잔액)에 누적
  balance += value;  // balance = balance + value;

  // 화면에 잔액을 출력
  output.innerText = balance;

  // 입력된 금액 삭제
  amount.value = '';
}


/** 출금 */
function withdrawal() {

  const pw = prompt("비밀번호를 입력하세요");

  // 취소
  if (pw === null) {
    alert("비밀번호 입력 취소됨");
    return;
  }

  // 입력 받은 비밀번호가 password와 불일치
  if (pw !== password) {
    alert("비밀번호가 일치하지 않습니다");
    return;
  }

  // 입력된 금액을 읽어와 숫자로 변환
  const value = Number(amount.value);

  // 입력된 금액이 잔액보다 큰 경우
  if (value > balance) {
    alert("출금 금액이 잔액보다 클 수 없습니다");
    return;
  }

  // 전역 변수 balance(잔액)에 차감
  balance -= value;  // balance = balance - value;

  // 화면에 잔액을 출력
  output.innerText = balance;

  // 입력된 금액 삭제
  amount.value = '';

  alert(`${value}원이 출금 되었습니다. 남은 잔액 : ${balance}원`);
}

/** 비밀번호 체크 분리 */
function pwCheck_() {
  const pw = prompt("비밀번호를 입력하세요");
  console.log("password : ", pw);

  if (pw !== password) {
    if (pw === null) alert(`비밀번호 입력 취소됨`);
    else alert(`비밀번호 불일치`);
    return false;
  }
}

// 다 자르려니까 너무 쓸모없게 쪼개짐.
// 더 줄일수 있나?
function bankFn(val) {
  const money = Number(amount.value);

  if (money >= 0) {
    if (val === 'deposit') {
      balance += money;
      alert(`${money} 원이 입금되어 잔액이 ${balance} 원 남았습니다.`);
    } else if (val === 'withdrawal') {
      if (pwCheck_() === false) return;
      if (money > balance) {
        alert(`잔액이 부족합니다. ${money - balance}원 부족`);
      } else {
        balance -= money;
        alert(`${money} 원이 출금되어 잔액이 ${balance} 원 남았습니다.`);
      }
    } else {
      alert(`버튼입력전달에러`);
    }
  } else if (money < 0) {
    alert(`0보다 큰 금액을 입력하십시오`);
  } else {
    alert(`올바른 금액을 입력하십시오 (0 이상의 숫자)`);
  }
  output.innerText = balance; // 잔액 업데이트
  amount.value = '0';         // 금액 초기화
}
//33줄 ==> 26줄


/** 비밀번호 변경 */
function pwChange() {
  //let password = "1234"; 비밀번호 저장 변수(초기 비밀번호 1234)
  //현재 비밀번호를 입력하십시오
  if(pwCheck_() == false) return;
  //바꿀 비밀번호를 입력하십씨오!
  const pw1 = prompt("변경할 비밀번호를 입력하세요");
  console.log("pw1 : ", pw1);
  if (pw1 === null) {
    alert(`비밀번호 입력 취소됨`);
  } 



  //바꾼 비밀번호를 다시 입력하십씨요!!
  const pw2 = prompt("변경된 비밀번호를 입력하세요");
  console.log("pw2 : ", pw2);
  if (pw2 !== pw1) {
    if (pw2 === null) alert(`비밀번호 입력 취소됨`);
    else alert(`비밀번호 불일치`);
  } else if (pw2 === pw1) {
    console.log("password : ", password);
    password = pw1;
    console.log("password : ", password);
    alert(`비밀번호 변경완료!`)
  }
}









/*
//==== 비밀번호 입력부분을 분리시켰고, 나머지 자잘한거 다 따로작성된 코드

function deposit_() {
  const money = Number(amount.value);

  if (money >= 0) {
    balance += money;
    alert(`${money} 원이 입금되어 잔액이 ${balance} 원 남았습니다.`);
  } else if (money < 0) {
    alert(`0보다 큰 금액을 입력하십시오`);
  } else {
    alert(`올바른 금액을 입력하십시오 (0 이상의 숫자)`);
  }
  output.innerText = balance; // 잔액 업데이트
  amount.value = '0';         // 금액 초기화
}

function withdrawal_() {
  if (pwCheck_() === false) return;
  const money = Number(amount.value);

  if (money >= 0) {
    if (money > balance) {
      alert(`잔액이 부족합니다. ${money - balance}원 부족`);
    } else {
      balance -= money;
      alert(`${money} 원이 출금되어 잔액이 ${balance} 원 남았습니다.`);
    }
  } else if (money < 0) {
    alert(`0보다 큰 금액을 입력하십시오`);
  } else {
    alert(`올바른 금액을 입력하십시오 (0 이상의 숫자)`);
  }
  output.innerText = balance; // 잔액 업데이트
  amount.value = '0';
}
*/