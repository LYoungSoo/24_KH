// 색 입력 input 태그들
const inputList = document.querySelectorAll(".color-input");

// 변경 버튼
const changeButton = document.querySelector("#changeButton");

changeButton.addEventListener("click", () => {
  for (let i = 0; i < inputList.length; i++) {
    const box = inputList[i].previousElementSibling;
    box.style.backgroundColor = inputList[i].value;
    console.log(box.style.backgroundColor);
  }
});

/* 같은 인덱스를 이용하는 방법은 한계가 존재하기 때문에
  대상 요소를 확실하게 선택한 후 탐색 코드를 이용해서
  찾아가 원하는 코드/기능을 적용 하는 것이 좋다!!!
*/