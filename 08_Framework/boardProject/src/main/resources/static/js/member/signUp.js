/* 다음 주소 API로 주소 검색하기 */
function findAddress() {
  new daum.Postcode({

      oncomplete: function(data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var addr = ''; // 주소 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              addr = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              addr = data.jibunAddress;
          }

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById('postcode').value = data.zonecode;
          document.getElementById("address").value = addr;
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById("detailAddress").focus();
      }
  }).open();

}

// 주소 검색 버튼 클릭 시
const searchAddress = document.querySelector("#searchAddress");
searchAddress.addEventListener("click", findAddress);

// ----------------------------------------------------------------------------------------------------

/***** 회원 가입 유효성 검사 *****/

/* 필수 입력 항목의 유효성 검사 여부를 체크하기 위한 객체(체크리스트) */
const checkObj = {
  "memberEmail"     : false,
  "memberPw"        : false,
  "memberPwConfirm" : false,
  "memberNickname"  : false,
  "memberTel"       : false
};

/* ----- 이메일 유효성 검사 ----- */

// 1) 이메일 유효성 검사에 필요한 요소 얻어오기
const memberEmail = document.querySelector("#memberEmail");
const emailMessage = document.querySelector("#emailMessage");

// 2) 이메일 메시지를 미리 작성
const emailMessageObj = {};   // 빈 객체
emailMessageObj.normal      = "메일을 받을 수 있는 이메일을 입력해주세요.";
emailMessageObj.invalid     = "올바른 이메일 형식으로 작성해 주세요";
emailMessageObj.duplication = "이미 사용중인 이메일 입니다";
emailMessageObj.check       = "사용 가능한 이메일 입니다.";

// 3) 이메일이 입력될 때 마다 유효성 검사를 수행
memberEmail.addEventListener("input", e => {

  // 입력된 값 얻어오기
  const inputEmail = memberEmail.value.trim();

  // 4) 입력된 이메일이 없을 경우
  if(inputEmail.length === 0) {

    // 이메일 메시지를 normal 상태 메시지로 변경
    emailMessage.innerText = emailMessageObj.normal;

    // #emailMessage 에 색상 관련 클래스를 모두 제거
    emailMessage.classList.remove("confirm","error");

    // checkObj에서 memberEmail을 false로 변경
    checkObj.memberEmail = false;

    memberEmail.value = "";   // 잘못 입력된 값(띄어쓰기) 제거

    return;
  }

  // 5) 이메일 형식이 맞는지 검사(정규 표현식)

  // 이메일 형식 정규 표현식 객체
  const regEx = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  console.log("inputEmail : ", inputEmail);

  // 입력 값이 이메일 형식이 아닌 경우
  if(regEx.test(inputEmail) === false) {
    emailMessage.innerText = emailMessageObj.invalid;   //유효 X 메시지
    emailMessage.classList.add("error");  // 빨간 글씨 추가
    emailMessage.classList.remove("confirm"); // 초록 글씨 제거
    checkObj.memberEmail = false  // 유효하지 않다고 체크
    return;
  }

  // 6) 이메일 중복 검사 (AJAX)
  fetch("/member/emailCheck?email=" + inputEmail)
  .then(response => {
    if(response.ok) {   // http 응답 상태 코드 200번(응답 성공)
      return response.text();   // 응답 경과를 text 로 파싱
    }
    throw new Error ("이메일 중복 검사 에러");
  })
  .then(count => {
    // 매개 변수 count : 첫 번째 then 에서 return된 값이 저장된 변수

    if(count == 1) {  // 중복인 경우
      emailMessage.innerText = emailMessageObj.duplication;   // 중복 메시지
      emailMessage.classList.add("error");  // 빨간 글씨 추가
      emailMessage.classList.remove("confirm"); // 초록 글씨 제거
      checkObj.memberEmail = false;
      return;
    }

    // 중복이 아닌 경우
    emailMessage.innerText = emailMessageObj.check;   // 중복X 메시지
    emailMessage.classList.add("confirm"); // 초록 글씨 추가
    emailMessage.classList.remove("error");  // 빨간 글씨 제거
    checkObj.memberEmail = true;  // 유효한 이메일임을 기록
  })
  .catch(err => console.error(err));

});

// ----------------------------------------------------------------------------------------------------

/* ----- 닉네임 유효성 검사 ----- */
// 1) 닉네임 유효성 검사에 사용되는 요소 얻어오기
const memberNickname = document.querySelector("#memberNickname");
const nickMessage = document.querySelector("#nickMessage");

// 2) 닉네임 관련 메시지 작성
const nickMessageObj = {};
nickMessageObj.normal      = "한글, 영어, 숫자로만 3 ~ 10 글자";
nickMessageObj.invalid     = "유효하지 않은 닉네임 형식입니다";
nickMessageObj.duplication = "이미 사용중인 닉네임 입니다";
nickMessageObj.check       = "사용 가능한 닉네임 입니다.";

// 3) 닉네임 입력 시 마다 유효성 검사
// memberNickname.addEventListener("input" , () => {
memberNickname.addEventListener("keydown" , () => {

  // 입력 받은 닉네임
  const inputNickname = memberNickname.value.trim();

  // 4) 입력된 닉네임이 없을 경우
  if(inputNickname.length === 0) {

    // 닉네임 메시지를 normal 상태 메시지로 변경
    nickMessage.innerText = nickMessageObj.normal;

    // #nickMessage 에 색상 관련 클래스를 모두 제거
    nickMessage.classList.remove("confirm","error");

    // checkObj에서 memberNickname false로 변경
    checkObj.memberNickname = false;

    memberNickname.value = "";   // 잘못 입력된 값(띄어쓰기) 제거

    return;
  }
  
  // 5) 닉네임 유효성 검사(정규 표현식)
  const regEx = /^[a-zA-Z0-9가-힣]{3,10}$/;   // 한글, 영어, 숫자로만 3 ~ 10 글자
  console.log("inputNickname : ", inputNickname);

  // 입력 값이 올바른 닉네임 형식이 아닌 경우
  if(regEx.test(inputNickname) === false) {
    nickMessage.innerText = nickMessageObj.invalid;   //유효 X 메시지
    nickMessage.classList.add("error");  // 빨간 글씨 추가
    nickMessage.classList.remove("confirm"); // 초록 글씨 제거
    checkObj.memberNickname = false  // 유효하지 않다고 체크
    return;
  }
  
  // 6) 닉네임 중복 검사
  fetch("/member/nickNameCheck?nickName=" + inputNickname)
  .then(response => {
    if(response.ok) {   // http 응답 상태 코드 200번(응답 성공)
      return response.text();   // 응답 경과를 text 로 파싱
    }
    throw new Error ("닉네임 중복 검사 에러");
  })
  .then(count => {
    // 매개 변수 count : 첫 번째 then 에서 return된 값이 저장된 변수

    if(count == 1) {  // 중복인 경우
      nickMessage.innerText = nickMessageObj.duplication;   // 중복 메시지
      nickMessage.classList.remove("confirm"); // 초록 글씨 제거
      nickMessage.classList.add("error");  // 빨간 글씨 추가
      checkObj.memberNickname = false;
      return;
    }

    // 중복이 아닌 경우
    nickMessage.innerText = nickMessageObj.check;   // 중복X 메시지
    nickMessage.classList.remove("error");  // 빨간 글씨 제거
    nickMessage.classList.add("confirm"); // 초록 글씨 추가
    checkObj.memberNickname = true;   // 유효한 닉네임 임을 기록
  })
  .catch(err => console.error(err));
});

// ----------------------------------------------------------------------------------------------------

/* 전화번호 유효성 검사 */
const memberTel = document.querySelector("#memberTel");
const telMessage = document.querySelector("#telMessage");

const telMessageObj = {};
telMessageObj.normal = "전화번호를 입력해주세요. (-제외)";
telMessageObj.invalid = "유효하지 않은 전화번호 형식입니다.";
telMessageObj.check = "유효한 전화번호 형식입니다.";

memberTel.addEventListener("input", () => {
  const inputTel = memberTel.value.trim();
  
  if(inputTel.length === 0) {
    telMessage.innerText = telMessageObj.normal;
    telMessage.classList.remove("confirm", "error");
    checkObj.memberTel = false;
    memberTel.value = "";
    return;
  }
  
  const regEx = /^010[0-9]{8}$/;  // 010으로 시작, 이후 숫자 8개 (총 11자)
  
  if(regEx.test(inputTel) === false) {
    telMessage.innerText = telMessageObj.invalid;
    telMessage.classList.remove("confirm");
    telMessage.classList.add("error");
    checkObj.memberTel = false;
    return;
  }
  
  telMessage.innerText = telMessageObj.check;
  telMessage.classList.remove("error");
  telMessage.classList.add("confirm");
  checkObj.memberTel = true;
});

// ----------------------------------------------------------------------------------------------------

/* 비밀번호 유효성 검사 */
const memberPw = document.querySelector("#memberPw");
const memberPwConfirm = document.querySelector("#memberPwConfirm");
const pwMessage = document.querySelector("#pwMessage");

const pwMessageObj = {};
pwMessageObj.normal = "영어,숫자,특수문자 1글자 이상, 6~20자 사이.";
pwMessageObj.invalid = "유효하지 않은 비밀번호 형식입니다.";
pwMessageObj.valid = "유효한 비밀번호 형식입니다.";
pwMessageObj.error = "비밀번호가 일치하지 않습니다.";
pwMessageObj.check = "비밀번호가 일치 합니다.";

memberPw.addEventListener("input", () => {
  
  const inputPw = memberPw.value.trim();

  if(inputPw.length === 0){ // 비밀번호 미입력
    pwMessage.innerText = pwMessageObj.normal;
    pwMessage.classList.remove("confirm", "error");
    checkObj.memberPw = false;
    memberPw.value = "";
    return;
  }

  // 비밀번호 정규표현식 검사
  const regEx = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#_\-])[A-Za-z\d!@#_\-]{6,20}$/;
/*   const lengthCheck = newPw.value.length >= 6 && newPw.value.length <= 20;
  const letterCheck = /[a-zA-Z]/.test(newPw.value); // 영어 알파벳 포함
  const numberCheck = /\d/.test(newPw.value); // 숫자 포함
  const specialCharCheck = /[\!\@\#\_\-]/.test(newPw.value); // 특수문자 포함 */

  // 조건이 하나라도 만족하지 못하면
  if (regEx.test(inputPw) === false) {
    pwMessage.innerText = pwMessageObj.invalid;
    pwMessage.classList.remove("confirm");
    pwMessage.classList.add("error");
    checkObj.memberPw = false;
    return;
  }

  pwMessage.innerText = pwMessageObj.valid;
  pwMessage.classList.remove("error");
  pwMessage.classList.add("confirm");
  checkObj.memberPw = true;

  // 비밀번호 확인이 작성된 상태에서 비밀번호가 입력된 경우
  if(memberPwConfirm.value.trim().length > 5) {
    checkPw();
  }

});

/* ----- 비밀번호, 비밀번호 확인 같은지 검사하는 함수 ----- */
function checkPw() {

  // 같은 경우
  if(memberPw.value === memberPwConfirm.value) {
    pwMessage.innerText = pwMessageObj.check;
    pwMessage.classList.add("confirm");
    pwMessage.classList.remove("error");
    checkObj.memberPwConfirm = true;
    return;
  }

  // 다른 경우
  pwMessage.innerText = pwMessageObj.error;
  pwMessage.classList.add("error");
  pwMessage.classList.remove("confirm");
  checkObj.memberPwConfirm = false;
}

/* ----- 비밀번호 확인이 입력 되었을 때  ----- */
memberPwConfirm.addEventListener("input", () => {

  if(memberPwConfirm.value.length < 6) {
    pwMessage.classList.remove("error");
    pwMessage.classList.remove("confirm");
    pwMessage.innerText = ""
    return;
  }

  // 비밀번호 input에 작성된 값이 유효한 형식일때만 비교
  if(checkObj.memberPw === true) {
    checkPw();
    return;
  }

  // 비밀번호 input에 작성된 값이 유효하지 않은 경우
  checkObj.memberPwConfirm = false;
});

// ----------------------------------------------------------------------------------------------------