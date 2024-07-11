// 기준이 되는 박스
const standard = document.querySelector("#standard");

const btns = document.querySelectorAll(".btn"); // 버튼들

let count = 1;  // 1씩 증가하면서 요소 내용으로 추가
// 모든 버튼에 클릭 이벤트 추가
for (let i = 0; i < btns.length; i++) {
  btns[i].addEventListener("click", () => {
    const str = btns[i].innerText;  // 작성된 버튼 내용
    console.log(str);

    switch (str) {
      case "prepend": const span1 = document.createElement("span");  // span 생성
        span1.innerText = count;  // span 내용으로 count 추가
        console.log(span1.innerText);   // span으로 추가한거라서 숫자가 옆으로 계속 늘어남
        span1.style.backgroundColor = "skyblue"
        span1.style.border = "1px dotted gray";
        standard.prepend(span1);  // #standard 첫 번째 자식으로 span1 추가
        break;
        
      case "append": const span2 = document.createElement("span");  //마지막 자식으로 추가할거
        span2.innerText = count;
        span2.style.backgroundColor = "lightcoral";
        span2.style.border = "1px dotted white";
        standard.append(span2);
        break;

      case "before": const div1 = document.createElement("div");  //네모앞에
        div1.innerText = count;
        div1.style.backgroundColor = "wheat";
        div1.style.border = "1px dotted black";
        div1.className = "box"; // box 클래스 추가
        standard.before(div1);
        break;

      case "after": const div2 = document.createElement("div");  //네모뒤에
        div2.innerText = count;
        div2.style.backgroundColor = "lightgoldenrodyellow";
        div2.style.border = "1px dotted gray";
        div2.className = "box";
        standard.after(div2);
        break;

      case "remove": document.querySelector(".container").remove();
        break;
    }
    count++;
  });
}