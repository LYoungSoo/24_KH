// 쿠키에 저장된 여러 값 중 key가 일치하는 value 반환
function getCookie(key) {

  // 1. cookie 전부 얻어오기(string)
  const cookies = document.cookie;  //"K=V; K=V; ..."
  // console.log(cookies);   //////////////

  // 2.";"을 구분자로 삼아서 배열 형태로 쪼개기(split)
  const arr = cookies.split(";");   // ["K=V", "K=V"]
  // console.log(arr);   /////////////

  // 3. 쪼개진 배열 요소를 하나씩 꺼내서
  // JS 객체에 K:V 형태로 추가

  const cookieObj = {}; // 빈 객체 생성

  for(let entry of arr) {
    // entry == "K+V"
    // ==> "=" 기준으로 쪼개기 (split)
    const temp = entry.split("=");  // ["K", "V"]

    cookieObj[temp[0]] = temp[1];
  }
  // console.log(cookieObj);

  // 매개변수로 전달 받은 key와 일치하는 value 반환
  return cookieObj[key];
}

// HTMP 로딩(랜더링)이 끝난 후 수행
document.addEventListener("DOMContentLoaded", () => {

  const saveEmail = getCookie("saveEmail");   // 쿠키에 저장된 Email 얻어오기

  // 저장된 이메일이 없을 경우
  if(saveEmail == undefined) return;

  const memberEmail = document.querySelector("#loginForm input[name=memberEmail]");

  const checkbox = document.querySelector("#loginForm input[name=saveEmail]");

  // 로그인 상태인 경우 함수 종료
  if(memberEmail == null) return;

  // 이메일 입력란에 저장된 이메일 출력
  memberEmail.value = saveEmail;

  // 이메일 저장 체크박스를 체크 상태로 바꾸기
  checkbox.checked = true;
});