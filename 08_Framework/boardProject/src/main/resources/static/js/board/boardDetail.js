// 현재 상세 조회한 게시글 번호
const boardNo = location.pathname.split("/")[3];

/* 좋아요 하트 클릭 시 */
const boardLike = document.querySelector("#boardLike");
boardLike.addEventListener("click", e => {

  // 1. 로그인 여부 검사
  if(loginCheck === false) {  // loginCheck <== boardDetail 맨 아래에 전역변수 형식으로 작성
    alert("로그인 후 이용해 주세요");
    return;
  }

  // 2. 비동기로 좋아요 요청
  fetch("/board/like", {
    method : "POST",
    headers : {"Content-Type" : "application/json"},
    body : boardNo

  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("좋아요 실패");
  })
  .then(result => {
    // console.log("result : ", result);

    // 좋아요 결과가 담긴 result 객체의 check 값에 따라
    // 하트 아이콘을 비우기/ 채우기 지정
    if(result.check === 'insert') {
      boardLike.classList.add("fa-solid");
      boardLike.classList.remove("fa-regular");

      // 게시글 작성자에게 알림 보내기
      // const content = `<strong>샘플2</strong> 님이 <strong>제목</strong> 게시글을 좋아합니다`;
      const content = `<strong>${memberNickname}</strong> 님이 <strong>${boardDetail.boardTitle}</strong> 게시글을 좋아합니다`;

      // type, url, pkNo, content
      sendNotification(
        "boardLike",
        location.pathname,  // 게시글 상세 조회 페이지 주소
        boardDetail.boardNo,
        content
      );

    } else {    // 비우기
      boardLike.classList.add("fa-regular");
      boardLike.classList.remove("fa-solid");
    }

    // 좋아요 하트 다음 형제 요소의 내용을
    // result.count 로 변경
    boardLike.nextElementSibling.innerText = result.count;
  })
  .catch (err => console.error(err));

})

// ----------------------------------------------------------------------------------------------------

/*
  * 삭제 버튼 클릭 시 *
  - 삭제 버튼 클릭 시 "정말 삭제 하시겠습니까?" confirm()

  - /editBoard/delete, POST 방식, 동기식 요청
    ==> form 태그 생성 + 게시글 번호가 세팅된 input
    ==> body 태그 제일 아래 추가해서 submit()

  - 서버(Java - 백엔드) 에서 로그인한 회원의 회원 번호를 얻어와
    로그인한 회원이 작성한 글이 맞는지 SQL에서 검사
*/

const deleteBtn = document.querySelector("#deleteBtn");

// 이벤트 리스너 ==> 특정 행동 감지기
// 삭제 버튼이 존재할 때 이벤트 리스너 추가
deleteBtn?.addEventListener("click", () => {

  if(confirm("정말 삭제 하시겠습니까?") == false) {
    return;
  }

  // 요청 주소
  const url = "/editBoard/delete"; 
  // 게시글 번호 == 전역 변수 boardNo

  // form 태그 생성
  const form = document.createElement("form");
  form.action = url;
  form.method = "POST";

  // input 태그 생성
  const input = document.createElement("input");
  input.type = "hidden";
  input.name = "boardNo";
  input.value = boardNo;

  form.append(input); // form 자식으로 input 추가

  document.querySelector("body").append(form);

  form.submit();  // 제출

});

// ----------------------------------------------------------------------------------------------------

/* 
  * 수정 버튼 클릭 시 *
  - /editBoard/{boardCode}/{boardNo}, POST, 동기식
  ==> form 태그 생성
  ==> body 태그 제일 아래 추가해서 submit()

  - 서버(Java) 에서 로그인한 회원의 회원 번호를 얻어와
    로그인한 회원이 작성한 글이 맞을 경우
    해당 글을 상세 조회 한 후
    수정 화면으로 전환
*/

const updateBtn = document.querySelector("#updateBtn");

updateBtn?.addEventListener("click", () => {
  
  const form = document.createElement("form");

  //    /editBoard/{boardCode}/{boardNo}/update
  form.action = location.pathname.replace("board", "editBoard") + "/updateView";
  form.method = "POST";

  document.querySelector("body").append(form);
  form.submit();
});

// ----------------------------------------------------------------------------------------------------

/* 목록으로 버튼 클릭 시 */
const goToListBtn = document.querySelector("#goToListBtn");

goToListBtn.addEventListener("click", () => {

  // 페이지 당 게시글 수
  const limit = 10;

  // location.href = location.pathname + "/goToList" + location.search + "?limit=" + limit;
  let url = location.pathname + "/goToList?limit=" + limit;

  // location.search : 쿼리스트링 반환
  // URLSearchParams 객체 : 쿼리스트링 관리하는 객체
  const params = new URLSearchParams(location.search);

  if(params.get("key") !== null) {
    // `` : String template 문자열을 만드는 기본 양식 제공해주는 기능
    url += `&key=${params.get("key")}&query=${params.get("query")}`;
  }

  location.href = url;
  // /board/{boardCode}/{boardNo}/goToList?limit=10
  // /board/{boardCode}/{boardNo}/goToList?limit=10&key=t&query=검색어

});



