function check1() {
  //배열 선언
  const arr1 = new Array();
  const arr2 = new Array(3);
  const arr3 = [];
  const arr4 = ['사과', '딸기', '바나나'];

  console.log(arr1.arr2, arr3, arr4);

  /* 배열명.length : 배열의 길이 */
  console.log(arr1.length, arr2.length, arr3.length, arr4.length);

  /* 배열의 각 요소 접근 방법(index 이용) */
  // 배열명[index] ==> index 번째 요소에 저장된 값을 반환
  console.log('arr4[0] : ', arr4[0]); // 사과
  console.log('arr4[1] : ', arr4[1]); // 딸기
  console.log('arr4[2] : ', arr4[2]); // 바나나
  console.log('arr4[3] : ', arr4[3]); // undefined

  // 배열명[index] 값; ==> 해당 index 번째 요소애 값 대입
  arr2[0] = 123;
  arr2[1] = '덥다';
  arr2[2] = true;

  // (JS 배열은 여러 자료형 저장 가능)
  console.log("arr2 : ", arr2);

  // ====================================================

  /* JS 배열 특징 이용하기 */


  // 1. 길이 제한이 없다 ==> 원하는 만큼 배열 요소를 추가할 수 있다,
  arr1[0] = '가나다';
  arr1[1] = '라마바';
  arr1[2] = '사아자';
  console.log("값이 대입된 arr1 : ", arr1);

  // 2. 원하는 index에 값을 마음대로 추가할 수 있다
  //    ==> 중간에 건너뛴 index 요소는 빈칸으로 처리됨
  arr1[5] = '중간에 건너뜀';
  console.log("arr1 확인 : ", arr1);
}

/** 배열 X for문 확인 1 */
function check2() {
  const weeks = ['월', '화', '수', '목', '금', '토', '일'];
  // weeks.length == 7
  // weeks의 마지막 인덱스 번호 == 6 == weeks.length -1
  for (let i = 0; i < weeks.length; i++) {
    // 0부터 7 미만(==6 이하) 까지 1씩 증가
    console.log(weeks[i]);
  }
}

/** 배열 X for문 확인 2 */
function check3() {
  /* 배열 오소에 순차적으로 값 초기화하기 */

  const arr = []; // 빈 배열 생성
  for (let i = 0; i < 4; i++) {
    arr[i] = prompt(`${i}번째 인덱스 요소 값 입력`);
  }

  console.log(arr);

}

/** 배열 X for문 확인 3 */
/* 
  prompt를 이용해서 숫자 5개를 입력받아 배열에 저장 후
  저장된 값, 합계, 평균을 console에 출력하기

  ex) 1,2,3,4,5 입력
  저장된 값 : [1,2,3,4,5]
  합계 : 15
  평균 : 3
*/
function check4() {
  let sum = 0;
  const arr = [];
  for (let i = 0; i < 5; i++) {
    // 입력 받자마자 number type으로 변환하여 배열 요소에 저장
    arr[i] = Number(prompt(`${i}번째 인덱스 요소 값 입력`));
    sum += arr[i];    // 합계 누적
  }

  console.log("저장된 값 : " + arr);
  console.log("합계 : " + sum);
  console.log("평균 : " + (sum / arr.length));
}


/** 배열 for문 확인 4 */
/* 배열에 0부터 배열 길이 미만의 난수 저장하기 */
function check5() {

  const leng = Number(prompt("생성할 난수 범위 지정(미만, 최댓값)"));
  const arr = [];
  for (let i = 0; i < leng; i++) {
    //0 이상, leng 미만의 난수 발생
    const randomNumber = Math.floor(Math.random() * leng)
    arr[i] = randomNumber;
  }

  console.log("결과 : ", arr);
}

/** 로또 번호 생성기 */
/* 1 ~ 45 사이 난수 6개 생성.   단, 중복 X, 오름차순 정렬 */
function check6() {
  const result6 = document.getElementById("result6");
  let count = 0;
  const lotto = [];   // 난수 저장할 배열 생성
  for (let i = 0; i < 6; i++) {
    // 1 ~ 45 사이 난수
    const randomNumber = Math.floor(Math.random() * 45) + 1;

    //일단 배열에 난수 대입
    lotto[i] = randomNumber;

    count++;  //무한루핑 방지 1

    // ==> 앞에 중복되는 숫자 있는지 확인
    if (count >= 101) return 0;
    for (let x = 0; x < i; x++) {
      // [중복 검사]
      // i번째 저장하려는 값이 x번째(앞쪽)에 존재하는가?
      if (lotto[i] === lotto[x]) {  // if 중복값이 존재
        // -> 있으면 현재 index에 머물기
        // 난수를 저장하기 위한 index를 지정하는 i값을 1 감소 
        // -> 바깥쪽 for문의 증감 i++에 의해 다시 1 증가
        // -> 결과적으로 i값 변화 X
        i--;
        break; // 불필요한 중복검사 멈춤 (중복값이 발견되면, 그 뒷 부분은 검사할 필요가 없음)
      }
      // 중복값 없으면 다음 index로 이동
    }
  }
  // 배열 내 숫자 정렬 방법
  lotto.sort(function (a, b) { return a - b; }) //이건 무슨 정렬인고?
  // sort = 맨앞자리 숫자 기준 정렬 ==> 1,13,19,2,3,594,66,71, ...
  console.log(lotto);
  console.log("count : " + count);

  result6.innerText = lotto;
}

/** 점심 메뉴 뽑기 */
function selectMenu() {

  // 점심 메뉴로 초기화된 배열 생성
  const menus = ["김밥", "제육볶음", "치즈돈가스", "맘스터치", "KFC", "짜장면", "화목순대국", "닭갈비", "편의점", "냉면", "일식", "순두부찌개", "빵", "서브웨이"]

  // menus 배열에서 랜덤으로 하나 뽑아서 화면에 #menuResult에 출력하기
  const random = Math.floor(Math.random() * menus.length);
  document.getElementById("menuResult").innerText = menus[random];

  //====확인용 콘솔
  console.log(menus);
  console.log(random);
  console.log(menus[random]);
}

function check7() {
  // 2차원 배열 선언 및 초기화
  const arr = [
    [10, 20, 30, 40],      // arr[0]
    [100, 200, 300, 400],  // arr[1]
    [90, 100]            // arr[2]
  ];
  console.log("2차원 배열 arr의 길이(행의 개수) : ", arr.length);
  console.log("arr의 0번째 인덱스 길이 : ", arr[0].length);
  console.log("arr의 1번째 인덱스 길이 : ", arr[1].length);
  console.log("arr의 2번째 인덱스 길이 : ", arr[2].length);

  // arr 모든 요소(행)을 for문을 이용해 접근
  for (let i = 0; i < arr.length; i++) {
    // console.log(arr[i]);

    let str = ''; //문자열 누적

    // arr[i] 요소에 저장된 배열의 모든 요소 접근
    for (let x = 0; x < arr[i].length; x++) {
      // arr[i].length : i번째 요소(배열)의 길이 ==> 4,4,2 순서
      str += `(${arr[i][x]})\t`;
    }
    console.log(str);
  }
}

/** 2차원 배열 확인 2 */
function check8() {
  let count = 0;
  // 4행 4열짜리 2차원 배열에 0~99 사이 난수 배치
  // 배열 생성
  const arr = [];
  for (let row = 0; row < 4; row++) {
    arr[row] = []; // 행 생성
    for (let col = 0; col < 4; col++) {
      // 난수 생성
      const randomNumber = Math.floor(Math.random() * 10);
      // 2차원 배열에 대입
      arr[row][col] = randomNumber;
    }
  }

  // 콘솔창 출력용 2중 for문
  for (let row = 0; row < arr.length; row++) {
    let str = '';
    for (let col = 0; col < arr[row].length; col++) {
      str += `${arr[row][col]}\t`;
    }
    console.log(str);
  }

  console.log(arr);
  const search = Number(prompt(`찾으려는 값 입력 : `));
  // 2차원 배열(arr) 내에서 입력 값(search) 찾기
  for (let row = 0; row < arr.length; row++) {
    for (let col = 0; col < arr[row].length; col++) {
      if (arr[row][col] === search) {
        console.log(`${row}행 ${col}열에 ${search}가 존재함`);
        count++;
      }
    }
  }
  console.log(search + " 는 " + count + " 개 있음");
}