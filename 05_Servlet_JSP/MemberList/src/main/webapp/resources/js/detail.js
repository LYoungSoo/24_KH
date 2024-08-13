// 쿼리스트링 값 얻어오기

// location.search : 쿼리스트링만 얻어오기
// URLSearchParams : 쿼리스트링을 객체 형태로 다룰 수 있는 객체
const params = new URLSearchParams(location.search);

// 쿼리스트링 중 key가 "index"인 파라미터의 값 얻어오기
// -> 할 일 완료 여부 변경, 수정, 삭제 시 사용
const index = params.get("index");



const goToList = document.querySelector("#goToList"); // 목록으로 버튼

// 목록으로 버튼이 클릭된 경우
goToList.addEventListener("click", () => {
  // "/" (메인 페이지) 요청 (GET 방식)
  location.href = "/";
});




// 완료 여부 변경 버튼 클릭 시
const completeBtn = document.querySelector("#completeBtn");
completeBtn.addEventListener("click", () => {

  // 현재 보고있는 Todo의 완료 여부 
  // (true)O <-> X(false) 변경 요청
  location.href = "/todo/complete?index=" + index;
});




// 삭제 버튼 클릭 시
const deleteBtn = document.querySelector("#deleteBtn");
deleteBtn.addEventListener("click", () => {

  // 1. 정말 삭제할 것인지 confirm()을 이용해서 확인

  // 취소 클릭 시
  if( !confirm("정말 삭제 하시겠습니까?") ) return;

  // 2. confirm() 확인 클릭 시 
  //    /todo/delete?index=인덱스번호 GET 방식 요청 보내기

  location.href = "/todo/delete?index=" + index;
});




// 수정 버튼 클릭 시 
// 수정할 수 있는 화면을 요청
const updateBtn = document.querySelector("#updateBtn");
updateBtn.addEventListener("click", () => {
  // GET 방식 요청
  location.href = "/todo/update?index=" + index;
})
