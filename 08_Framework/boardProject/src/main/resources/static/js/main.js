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

// ----------------------------------------------------------------------------------------------------

// tbody 태그
const memberList = document.querySelector("#memberList");

/* 메인 페이지 회원 목록 비동기 조회 함수 */
const selectMemberList = () => {

  // 1) 비동기로 모든 회원의
  //    회원번호, 이메일, 탈퇴 상태 조회하기
  fetch("/selectMemberList")
  .then(response => {
    // 응답 성공 시 JSON 형태의 응답 데이터를 JS 객체로 변경
    if(response.ok) return response.json();
    throw new Error("조회 오류")
  })
  .then(list => {
    // console.log(list);

    // 1) #memberList 기존 내용 없에기
    memberList.innerHTML = "";

    // 2) 조회 결과인 list를 반복 접근해서
    //    #memberList 에 조회된 내용으로 tr,td,th 만들어 넣기
    list.forEach(member => {
      // 매개 변수 member == 조회된 list에서 하나씩 꺼낸 요소

      // tr 요소 만들기
      const tr = document.createElement("tr");

      // th 만들어서 회원 번호 세팅
      const th1 = document.createElement("th");
      th1.innerText = member.memberNo;

      // td 만들어서 회원 이메일 세팅
      const td2 = document.createElement("td");
      td2.innerText = member.memberEmail;

      // th 만들어서 탈퇴 여부 세팅
      const th3 = document.createElement("th");
      th3.innerText = member.memberDelFl;

      // th > button 만들어서 "로그인" 글자 세팅
      const th4 = document.createElement("th");
      const loginBtn = document.createElement("button");
      loginBtn.innerText = "로그인";
      th4.append(loginBtn);

      // 만약 탈퇴 상태인 경우 로그인 버튼 비활성화
      if(member.memberDelFl === 'Y') {
        loginBtn.disabled = true;
      } else {

        // 탈퇴 상태가 아닌 경우
        // 만들어진 로그인 버튼에 클릭 이벤트 추가
        loginBtn.addEventListener("click", () => {

          // body 태그 제일 마지막에 form 태그를 추가해 
          // 제출하는 형식으로 코드 작성
          // 왜? POST 방식 요청을 하고 싶기 때문에 ==> form / ajax 만 post 요청이 가능
          // 비동기는 session에 데이터를 실시간으로 올려 화면에 적용할 수가 없다

          const form = document.createElement("form");
          form.action = "/directLogin";
          form.method = "POST";

          const input = document.createElement("input");
          input.type = "hidden";
          input.name = "memberNo";
          input.value = member.memberNo;

          form.append(input);   // form 자식으로 input 추가
          
          // body 태그 자식으로 form 추가
          document.querySelector("body").append(form);

          form.submit();    // 제출
        });
      }

      // th > button 만들어서 "비밀번호 초기화" 글자 세팅
      const th5 = document.createElement("th");
      const initBtn = document.createElement("button");
      initBtn.innerText = "비밀번호 초기화";
      th5.append(initBtn);

      // 비밀번호 초기화 버튼 클릭 시
      initBtn.addEventListener("click", () => {
        fetch("/resetPw" , {
          method : "POST",
          headers : {"Content-Type": "application/json"},
          body : member.memberNo
        })
        .then(response => {
          if(response.ok) return response.text();
          throw new Error("비밀번호 초기화 오류")
        })
        .then(result => {
          if(result > 0) alert("비밀번호가 초기화 되었습니다");
          else           alert("비밀번호 초기화 실패...");
        })
        .catch(err => console.error(err));
      });

      // th > button 만들어서 "탈퇴 상태 변경" 글자 세팅
      const th6 = document.createElement("th");
      const changeBtn = document.createElement("button");
      changeBtn.innerText = "탈퇴 상태 변경";
      th6.append(changeBtn);

      // 탈퇴 상태 변경 버튼 클릭 시
      changeBtn.addEventListener("click", () => {
        fetch("/changeStatus" , {
          method : "PUT",
          headers : {"Content-Type": "application/json"},
          body : member.memberNo
        })
        .then(response => {
          if(response.ok) return response.text();
          throw new Error("탈퇴 상태 변경 오류")
        })
        .then(result => {
          if(result > 0) alert("탈퇴 상태가 변경되었습니다");
          else           alert("탈퇴 상태 변경 실패...");
          selectMemberList();
        })
        .catch(err => console.error(err));
      })

      // tr에 만든 th,td 모두 추가
      tr.append(th1,td2,th3,th4,th5,th6);

      // #memberList에 tr 추가
      memberList.append(tr);
    })


  })
  .catch(err => console.error(err));

}

/* 페이지 로딩(랜더링)이 끝난 후 수행 */
document.addEventListener("DOMContentLoaded", () => {
  selectMemberList();
});

// ----------------------------------------------------------------------------------------------------

// 로그인 : id와 pw가 일치하는 회원정보를 db에서 찾아 session에 올려놓는 작업

/*
  비밀번호 초기화 버튼 클릭 시
  - Ajax 를 이용해서 /resetPw (POST) 요청
  - 서버로 제출되어야 하는 값 : memberNo
  - 서버 : "pass01!"를 암호화 해서 memberNo가 일치하는 회원의 비밀번호를 update
  - 성공 시 :  alert("비밀번호가 초기화 되었습니다")
*/

/*
  탈퇴 버튼 클릭 시
  - Ajax 를 이용해서 /changeStatus (PUT) 요청
  - 서버로 제출되어야 하는 값 : memberNo
  - 서버 : memberNo 가 일치하는 회원의 탈퇴 상태 컬럼 (MEMBER_DEL_FL) 값을 'N' <-> 'Y'
  - 성공 시 :  화면에 출력되어있는 회원 목록 다시 조회해서 출력
*/