// 로그아웃 버튼 클릭 시
document.querySelector("#logout").addEventListener("click", () => {
  
  // 서버에 /logout GET방식 요청
  location.href = "/logout";
})