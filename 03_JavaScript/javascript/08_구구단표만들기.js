/* 구구단 표 만들기 */
function createTable_() {
  // 시작 단
  const startInput = document.getElementById("start");

  // 종료 단
  const endInput = document.getElementById("end");

  // 테이블 머리(제목)를 작성하는 영역(행)
  const theadRow = document.getElementById("thead-row");

  // 테이블 몸통(내용, 데이터)를 작성하는 부분
  const tbody = document.getElementById("tbody");

  // ========================================
  // 이전 #thead-row, #tbody에 작성된 내용을 모두 삭제
  theadRow.innerHTML = '';  // 빈칸을 대입해서 내용을 삭제한 효과
  tbody.innerHTML = '';
  // ========================================

  /* 입력 받은 단을 number 타입으로 변경 */
  /* 시작종료 값 반전을 위해 let으로 일시적 변경 */
  let start = Number(startInput.value);
  let end = Number(endInput.value);
  // ================================================== 이하 장난태그 한칸
  if (start > end) {
    console.log("변경 전 start :" + start + ", b : " + end);
    start = start ^ end;
    end = start ^ end;
    start = start ^ end;
    console.log("변경 후 a : " + start + ", b : " + end);
  }
  // ========================================

  // 단(제목) 만들기
  // 시작 단 ~ 종료 단 반복문
  let outdan = '';
  for (let dan = start; dan <= end; dan++) {
    theadRow.innerHTML += `<th>${dan} 단</th>`;
    let indan = '';

    for(let x = 1; x <= 9; x++) {
      indan += `<tr>${x}</tr>`
    }
    outdan += `<td>${indan}</td>`

    //body.innerHTML += `<tr>${dan}</tr>`;
    //indan += `<td>${start + x -1} X ${x}</td>`;
  }
  console.log(outdan);
  console.log("=======================");
  tbody.innerText = outdan;
  console.log(tbody.innerText);
  console.log("=======================");
  tbody.innerHTML = outdan; //이새끼가 범인이에요 여러분 아어류하너오라너오라ㅓㄴㅇㄹ
  console.log(tbody.innerHTML);
}

/* 

      <tbody id="tbody">
        <tr>
          <td>2 x 1 = 2</td>
          <td>3 x 1 = 3</td>
          <td>4 x 1 = 4</td>
        </tr>

        <tr>
          <td>2 x 2 = 4</td>
          <td>3 x 2 = 6</td>
          <td>4 x 2 = 8</td>
        </tr>


*/