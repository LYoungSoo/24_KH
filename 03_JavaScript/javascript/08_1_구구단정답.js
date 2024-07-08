/* 구구단표 만들기 */
function createTable(){

  // 시작 단
  const startInput = document.getElementById("start");
  
  // 종료 단
  const endInput = document.getElementById("end");

  // 테이블 머리(제목)를 작성하는 영역(행)
  const theadRow = document.getElementById("thead-row");

  // 테이블 몸통(내용, 데이터)를 작성하는 부분
  const tbody = document.getElementById("tbody");


  // -------------------------------------------------------

  // 이전 #thead-row, #tbody에 작성된 내용을 모두 삭제
  theadRow.innerHTML = ''; // 빈칸을 대입해서 삭제한 효과
  tbody.innerHTML = '';

  // -------------------------------------------------------

  /* 입력 받은 단을 number 타입으로 변경 */
  const start = Number(startInput.value);
  const end   = Number(endInput.value);

  // -------------------------------------------------------

  // 단(제목) 만들기

  // 시작 단 ~ 종료 단 반복문
  for(let dan = start ; dan <= end ; dan++){
    theadRow.innerHTML += `<th>${dan}단</th>`;
  }

  // -------------------------------------------------------

  // 구구단(내용) 만들기

  // 구구단에 곱해지는 수(1~9) 반복 == 행
  for(let num = 1 ; num <= 9 ; num++){

    let tr = "<tr>";   // 한 줄을 저장할 변수 선언

    for(let dan = start ; dan <= end ; dan++){
      tr += `<td>${dan} x ${num} = ${dan * num}</td>`;
    }
    
    tr += "</tr>";

    // 만들어진 한 줄을 #tbody에 누적
    tbody.innerHTML += tr;
  }
}