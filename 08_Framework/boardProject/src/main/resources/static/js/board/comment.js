// 댓글 목록이 출력되는 영역(ul을 감싸는 div)
const commentListArea = document.querySelector(".comment-list-area");

/* 댓글 목록 조회 함수(ajax) */
const selectCommentList = () => {

  // boardNo : 게시글 번호(boardDetail.js 전역 변수)
  fetch("/board/commentList?boardNo="+boardNo)    // GET 방식만 쿼리스트링 사용가능
  .then(response => {
    // response.ok : HTTP 응답 상태 코드가 200번대(성공) 이면 true
    if(response.ok) return response.text();
    throw new Error("댓글 목록 조회 실패")
  })
  .then(html => {
    // 매개 변수 html : 타임리프가 해석되어 완성된 html 코드
    console.log(html);

    // 타임리프가 해석된 html 코드를
    // .comment-list-area 의 내용으로 대입 후 HTML 코드 해석
    commentListArea.innerHTML = html;

    /* [주의사항] */
    // innerHTML 으로 새로 만들어진 요소에는
    // 이벤트 리스너가 추가되어 있지 않기 때문에
    // 답글, 수정, 삭제 등이 존재하지 않는다!!!

    addEventChildComment();   // 답글 버튼에 클릭 이벤트 추가
    addEventDeleteComment();  // 삭제 버튼에 클릭 이벤트 추가
    addEventUpdateComment();  // 수정 버튼에 이벤트 추가
  })
  .catch(err => console.error(err));
};

// ----------------------------------------------------------------------------------------------------

// 댓글 내용 요소
const commentContent = document.querySelector("#commentContent");

/**
 * 댓글 등록 함수 (AJAX)
 */
const insertComment = (parentCommentNo) => {  // 제출된 적 없으면 indefined

  // 서버에 제출할 값을 저장할 객체
  const data = {};

  data.boardNo = boardNo;   // 댓글이 작성된 게시글 번호
  data.commentContent = commentContent.value;   // 작성된 댓글 내용 , 답글내용이 아님!!

  // 매개 변수로 전달 받은 부모 댓글 번호가 있다면
  // == 답글
  if(parentCommentNo !== undefined) {
    data.parentCommentNo = parentCommentNo;

    // 답글에 작성된 내용 얻어오기
    data.commentContent = document.querySelector(".child-comment-content").value;
  }

  // Ajax
  fetch("/comment", {
    method : "POST",    // REST API 에서 Data 삽입 용도로 사용함
    headers : {"Content-Type" : "application/json"},
    body : JSON.stringify(data)     // JS 객체 ==> JSON (문자열)
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("댓글 등록 실패");
  })
  .then(commentNo => {
    if(commentNo == 0) {    // 등록 실패
      alert("댓글 등록 실패");
      return;
    }

    alert("댓글이 등록 되었습니다");
    commentContent.value = "";    // textarea에 작성한 댓글 내용 삭제
    selectCommentList();          // 댓글 목록 비동기 조회 후 출력

    // 알림 클릭 시 이동하는 url 에 ?cn=댓글번호 추가
    // ==> 알림 클릭 시 작성된 댓글 또는 답글 위치로 바로 이동

    // 댓글을 작성한 경우
    // ==> {닉네임} 님이 {게시글 제목} 게시글에 댓글을 작성했습니다
    if(parentCommentNo === undefined) {
      const content = `<strong>${memberNickname}</strong> 님이
            <strong>${boardDetail.boardTitle}</strong> 게시글에 댓글을 작성했습니다.`;
      
      // type, url, pkNo, content
      sendNotification(
        "insertComment",
        `${location.pathname}?cn=${commentNo}`,
        boardDetail.boardNo,
        content
      );
    }

    // 답글(대댓글)을 작성한 경우
    // ==> {닉네임}님이 답글을 작성했습니다
    else {
      const content = `<strong>${memberNickname}</strong> 님이
            <strong>${boardDetail.boardTitle}</strong> 게시글에 작성된 댓글에
            답글을 작성했습니다.`;
    
      // type, url, pkNo, content
      sendNotification(
      "insertChildComment",
      `${location.pathname}?cn=${commentNo}`,
      parentCommentNo,
      content
      );
    }
  })
  .catch(err => console.Error(err));
}

/**
 * 답글 버튼 클릭 시 해당 댓글에 답글 작성 영역을 추가하는 함수
 * @param btn : 클릭된 답글 버튼
 */
const showChildComment = (btn) => {

  /* 로그인이 되어 있지 않은 경우 */
  if(loginCheck === false){
    alert("로그인 후 이용해주세요");
    return;
  }

  // ** 답글 작성 textarea가 한 개만 열릴 수 있도록 만들기 **
  const temp = document.getElementsByClassName("child-comment-content");

  if (temp.length > 0) { // 답글 작성 textara가 이미 화면에 존재하는 경우

    if (confirm("다른 답글을 작성 중입니다. 현재 댓글에 답글을 작성 하시겠습니까?")) {
      temp[0].nextElementSibling.remove(); // 버튼 영역부터 삭제
      temp[0].remove(); // textara 삭제 (기준점은 마지막에 삭제해야 된다!)

    } else {
      return; // 함수를 종료시켜 답글이 생성되지 않게함.
    }
  }

  // 클릭된 답글 버튼이 속해있는 댓글(li) 요소 찾기
  // closest("태그") : 부모 중 가장 가까운 태그 찾기
  const li = btn.closest("li");
  // console.log(li);

  // 답글이 작성되는 댓글(부모 댓글) 번호 얻어오기
  const parentCommentNo = li.dataset.commentNo;
  // console.log(parentCommentNo); -----------------------------

  // 답글을 작성할 textarea 요소 생성
  const textarea = document.createElement("textarea");
  textarea.classList.add("child-comment-content");

  li.append(textarea);

  // 답글 버튼 영역 + 등록/취소 버튼 생성 및 추가
  const commentBtnArea = document.createElement("div");
  commentBtnArea.classList.add("comment-btn-area");

  const insertBtn = document.createElement("button");
  insertBtn.innerText = "등록";

  /* 등록 버튼 클릭 시 댓글 등록 함수 호출(부모 댓글 번호 전달)  */
  insertBtn.addEventListener("click", () => insertComment(parentCommentNo));

  const cancelBtn = document.createElement("button");
  cancelBtn.innerText = "취소";
  // cancelBtn.setAttribute("onclick", "insertCancel(this)");

  /* 취소 버튼 클릭 시 답글 작성 화면 삭제 */
  cancelBtn.addEventListener("click", () => {

    // console.log(li.lastElementChild);
    li.lastElementChild.remove(); // 제일 아래에는 답글의 등록버튼과
    li.lastElementChild.remove(); // 답글의 textarea가 있다. 취소를 눌러서 두번 지우면 두개 다 지워지는
  });

  // 답글 버튼 영역의 자식으로 등록/취소 버튼 추가
  commentBtnArea.append(insertBtn, cancelBtn);

  // 답글 버튼 영역을 화면에 추가된 textarea 뒤쪽에 추가
  textarea.after(commentBtnArea);
}

/**
 * 댓글 삭제 함수(ajax)
 * @param btn : 삭제버튼
 */
const deleteComment = (btn) => {

  // confirm() 취소 시
  if(confirm("정말 삭제 하시겠습니까?") === false) {
    return;
  }
  
  // 삭제할 댓글 번호 얻어오기
  const li = btn.closest("li");   // 댓글 하나를 나타내는 li 
  const commentNo = li.dataset.commentNo;   // 댓글 번호

  fetch("/comment", {
    method : "DELETE",
    headers : {"Content-Type" : "application/json"},   // 잘못쓰면 415번 에러 미디어타입
    body : commentNo
  })
  .then(response => {
    if(response.ok) return response.text();
    throw new Error("댓글 삭제 실패");
  })
  .then(result => {
    if(result > 0) {
      alert("댓글이 삭제 되었습니다");
      commentContent.value = "";    // textarea에 작성한 댓글 내용 삭제
      selectCommentList();          // 댓글 목록 비동기 조회 후 출력
    } else {
        alert("댓글 삭제 실패");
    }
  })
  .catch(err => console.Error(err));
}

/* 백업된 댓글을 저장할 변수 */
let beforeCommentRow;

/**
 * 댓글 수정 화면으로 전환
 * @param btn : 수정 버튼
 */
const showUpdateComment = (btn) => {

  /* 댓글 수정 화면이 한 개만 열려 있을 수 있게 하기 */
  // == 이미 열려있는 수정 화면이 있으면 닫아버리기
  const temp = document.querySelector(".update-textarea");

  if(temp != null) {  // 이미 열려있는 수정 화면이 있을 경우

    if(confirm("수정 중인 댓글이 있습니다. 현재 댓글을 수정 하시겠습니까?") === true) {
      const commentRow = temp.parentElement;    // 열려있는 댓글 행
      commentRow.after(beforeCommentRow);       // 백업본을 다음 요소로 추가
      commentRow.remove();                      // 열려있던 행 삭제

      // 백업본 버튼에 이벤트 추가
      const childeCommentBtn = beforeCommentRow.querySelector(".child-comment-btn");
      const updateCommentBtn = beforeCommentRow.querySelector(".update-comment-btn");
      const deleteCommentBtn = beforeCommentRow.querySelector(".delete-comment-btn");

      childeCommentBtn.addEventListener("click", () => showChildComment(childeCommentBtn));
      updateCommentBtn.addEventListener("click", () => showUpdateComment(updateCommentBtn));
      deleteCommentBtn.addEventListener("click", () => deleteComment(deleteCommentBtn));
    } else {
      return;   // confirm 에서 취소 누를경우 수정창 열지않기
    }
  }

  // 1. 수정하려는 댓글(li) 요소 얻어오기
  const commentRow = btn.closest("li");
  const commentNo = commentRow.dataset.commentNo;   // 댓글 번호

  // 2. 취소 버튼 동작에 대비하여 현재 댓글(commentRow) 의 요소를 복제해서 백업
  beforeCommentRow = commentRow.cloneNode(true);

  /* 요소.cloneNode(true);
      - 요소 복제하여 반환
      - 매개 변수 true : 복제 하려는 요소의 하위 요소들도 복제
  */
  
  // 3. 기존 댓글에 작성된 내용만 얻어오기
  let beforeContent = commentRow.children[1].innerText;

  // 4. 댓글 행 내부를 모두 삭제
  commentRow.innerHTML = "";

  // 5. textarea 생성 + 클래스 추가 + 내용 추가
  const textarea = document.createElement("textarea");
  textarea.classList.add("update-textarea");
  textarea.value = beforeContent;

  // 6. 댓글 행에 textarea 추가
  commentRow.append(textarea);

  // 7. 버튼 영역 생성
  const commentBtnArea = document.createElement("div");
  commentBtnArea.classList.add("comment-btn-area");

  // 8. 수정 버튼 생성
  const updateBtn = document.createElement("button");
  updateBtn.innerText = "수정";

  // 9. 취소 버튼 생성
  const cancelBtn = document.createElement("button");
  cancelBtn.innerText = "취소";

  cancelBtn.addEventListener("click", () => {

    // 취소를 취소 => 수정 계속 진행
    if(confirm("취소 하시겠습니까?") === false) return;

    // 현재 댓글 행 다음 위치에 백업한 원본 댓글 추가
    commentRow.after(beforeCommentRow);
    commentRow.remove();  // 수정 화면으로 변환된 행 삭제
    // 그러니까 있던거 뒤에 원래꺼 추가하고, 있던거 날리면 알아서 당겨와지면서 원래것처럼

    /* 원상 복구된 댓글의 버튼에 이벤트 추가하기 */
    const childCommentBtn  = beforeCommentRow.querySelector(".child-comment-btn");
    childCommentBtn.addEventListener("click", () => {
      showChildComment(childCommentBtn);
    });

    const updateCommentBtn = beforeCommentRow.querySelector(".update-comment-btn");
    updateCommentBtn.addEventListener("click", () => {
      showUpdateComment(updateCommentBtn);
    });

    const deleteCommentBtn = beforeCommentRow.querySelector(".delete-comment-btn");
    deleteCommentBtn.addEventListener("click", () => {
      deleteComment(deleteCommentBtn);
    });

  });

  // 10. 버튼 영역에 수정/취소 버튼 추가 후
  //     댓글 행에 버튼 영역 추가
  commentBtnArea.append(updateBtn, cancelBtn);
  commentRow.append(commentBtnArea);











  
}


// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------

/* 이벤트 추가 구문을 모아두는 영역 */

// 댓글 등록 버튼 클릭 동작 추가
const addComment = document.querySelector("#addComment");
addComment.addEventListener("click", () => {

  // 1) 로그인 여부 검사(boardDetail.html 의 loginCheck 전역 변수)
  if(loginCheck == false) {
    alert("로그인 후 이용해주세요");
    return;
  }

  // 2) 댓글 작성 여부 검사
  if(commentContent.value.trim().length === 0){
    alert("내용 작성 후 등록 버튼을 클릭해 주세요");
    return;
  }

  // 3) 1,2 통과 시 댓글 등록 함수 호출
  insertComment();
});

/* 화면에 존재 하는 답글 버튼을 모두 찾아 이벤트 리스너 추가 */
const addEventChildComment = () => {
  
  const btns = document.querySelectorAll(".child-comment-btn");

  btns.forEach(btn => {
    btn.addEventListener("click", () => {
      showChildComment(btn);    // 답글 작성 화면 출력 함수 호출
    });
  });
}


/**
 * 화면에 존재하는 모든 댓글 삭제 버튼에 이벤트 리스너 추가하는 함수
 */
const addEventDeleteComment = () => {
  const btns = document.querySelectorAll(".delete-comment-btn");

  btns.forEach(btn => {
    btn.addEventListener("click", () => {
      deleteComment(btn);   // 클릭 시 deleteComment() 함수 호출
    });
  });
}


/* 화면에 존재하는 댓글 수정 버튼에 이벤트 리스너 추가 */
const addEventUpdateComment = () => {
  const btns = document.querySelectorAll(".update-comment-btn");

  btns.forEach(btn => {
    btn.addEventListener("click", () => {
      showUpdateComment(btn);
      // 수정 버튼 클릭 시 showUpdateComment() 호출
    });
  });

}


/* 화면 코드 해석 완료 후 */
// 화면에 들어가는 모든 요소가 메모리에 적재가 완료 == 화면 로딩 끝 == 화면 나올때
document.addEventListener("DOMContentLoaded", () => { 
  addEventChildComment();   // 답글 버튼에 이벤트 추가
  addEventDeleteComment();  // 삭제 버튼에 이벤트 추가
  addEventUpdateComment();  // 수정 버튼에 이벤트 추가
});

// ----------------------------------------------------------------------------------------------------

/* REST(REpresentational State Transfer)  API

- 자원(데이터,파일)을 이름(주소)으로 
  구분(representational) 하여
  자원의 상태(State)를 주고 받는 것(Transfer)

 -> 자원의 이름(주소)를 명시하고
   HTTP Method(GET,POST,PUT,DELETE) 를 이용해
   지정된 자원에 대한 CRUD 진행

  자원의 이름(주소)는 하나만 지정 (ex. /comment)
   
  삽입 == POST    (Create)
  조회 == GET     (Read)
  수정 == PUT     (Update)
  삭제 == DELETE  (Delete)

  이걸 잘 지키면 RestFul ==> 얼마나 잘 지켰느냐!

  *** 비동기 일때만 가능!! ***
*/

