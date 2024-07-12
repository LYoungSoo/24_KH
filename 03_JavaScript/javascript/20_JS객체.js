/* 
  JS 객체 생성 + 다루기
  - JS 객체 : { K:V, K:V, K:V, K:V, ... }
  - (중요!) K(key)는 무조건 string!!!
    ==> "Key" 또는 'Key' 또는 Key
      ===> "", '' 붙이지 않아도 알아서 string으로 해석
  
  - JS 객체를 콘솔에 출력하는 경우 입력한 순서와 관계없이 Key 오름차순으로 정렬되어 출력됨
*/

const btn1 = document.querySelector("#btn1");
btn1.addEventListener("click", () => {
  // 1. 객체 생성
  const product = {
    "productName": "새우깡",
    'brand': '농심',
    price: 1500
  };

  console.log(product);

  // 2. Key를 이용해 원하는 Value만 얻어오기

  // a) 객체명.key
  console.log('1. name  : ' + product.productName);
  console.log('2. brand : ' + product.brand);
  console.log('3. price : ' + product.price);

  // b) 객체명["key"]
  console.log('4. [name] : ' + product["productName"]);
  console.log('5. [brand] : ' + product['brand']);
  console.log('6. [price] : ' + product["price"]);

  // 3. 특정 key의 value 수정하기
  // 존재하는 key에 새로운 값 대입 ==> 값 덮어쓰기 == update
  product.price = 2000;
  console.log(product);

  // 4. 객체에 새로운 K:V 추가하기
  //    ==> 객체명. 새로운 key = 새로운Value
  //        또는 객체명["새로운Key"] = 새로운Value 
  product.discount = 0.2;
  product["store"] = 'GS25';
  product["store"] = "CU";
  console.log(product);

  // 5. 객체 내에서 특정 Key 삭제하기
  //    - delete 객체명.key ==> 객체에서 K:V 삭제

  delete product.productName; // productName:"새우깡" 삭제
  console.log(product);
});

// -----------------------------------------------

/* 메서드() 확인 */
const btn2 = document.querySelector("#btn2");
btn2.addEventListener("click", () => {
  const person = {};  // 비어있는 객체 생성

  // 비어있는 객체에 필드(== 변수) K:V 추가
  person.name = "홍길동";
  person.age = 22;

  console.log(person);
  console.log(person.name);
  console.log(person.age);

  /*
    this 참조 변수
    - 작성된 위치마다 의미가 다름
    1) 객체 내 함수(메서드)에서 this == 같은 객체 (여기, 이 객체)
    2)  <button onclick="testFn(this)"> 테스트 </button>
        여기서 this == 이벤트가 발생한 요소(클릭된 button)
    3) 화살표 함수 내에서 this == window 객체 (자바스크립트 객체 중 최상위이자 기본이 되는 객체)
       (화살표 함수 내에서 this를 쓸수 없다고 생각!!)

  */

  // 비어있는 객체에 메서드(== 함수) K:V 추가
  // person.introduce = () => { //화살표 함수 사용시 this.name, this.age가 고장남
  person.introduce = function () {
    const gender = '남자';

    // 홍길동은 20세 남자 입니다.
    alert(`${this.name}은 ${this.age}세 ${gender} 입니다`);
    alert(`?`);
    alert(`${person.name}은 ${person.age}세 ${gender} 입니다`);
  }

  // 메서드 수행(객체에 저장된 함수 호출)
  person.introduce();

  // 메서드는 객체에 종속된 (속한) 함수 ==> 꼭 객체명. 함수명() 형식으로 호출해야만 한다
  //  introduce(); // 함수명() 만으로 단독 호출 불가
});

/* 생성자 함수 */
// 학생(Student) 생성자 함수 정의
function Student(inputGrade, inputBan, inputNumber, inputName) {

  // 여기서 this == 생성되는 Student 객체

  // 필드 (객체의 변수)
  this.grade = inputGrade;  // 전달받은 inputGrade를 생성되는 객체 grade의 value로 대입
  this.ban = inputBan;
  this.number = inputNumber;
  this.name = inputName;

  // 메서드(객체의 함수)
  this.information = function () {
    let str = `${this.grade}학년 ${this.ban}반 ${this.number}번 ${this.name}`;
    return str; // 호출한 곳으로 문자열 반환
  }
}


/* 입력 값을 이용해 Student 객체를 생성하여 배열에 저장 */
/* 객체 배열 : 배열 내 요소가 모두 객체(객체를 저장한 배열) ==> [ {}, {}, {}, {} ] */

const inputGrade = document.querySelector("#inputGrade");
const inputBan = document.querySelector("#inputBan");
const inputNumber = document.querySelector("#inputNumber");
const inputName = document.querySelector("#inputName");

const addStudent = document.querySelector("#addStudent");

// 생성된 Student 객체를 저장할 배열 선언
const studentList = []; // 생성시 이 배열에 추가

// 추가 버튼 클릭 시
addStudent.addEventListener("click", () => {
  // input에 작성된 값을 이용해 학생 객체 생성
  const std = new Student(inputGrade.value, inputBan.value, inputNumber.value, inputName.value);

  // 생성된 Student 객체를 studentList에 저장
  // * 배열명.push(값) : 배열의 마지막 요소로 값을 추가
  if (inputGrade.value != '' || inputBan.value != '' || inputNumber.value != '' || inputName.value != '') {
    studentList.push(std);
    console.log(studentList);
  }

  // 이전 입력 값 삭제
  inputGrade.value = '';    // 빈 문자열을 value에 대입해 삭제한 효과
  inputBan.value = '';
  inputNumber.value = '';
  inputName.value = '';
});

// 학생 목록 출력하기
const printStudent = document.querySelector("#printStudent");
const result = document.querySelector("#result")

// 현재 학생 목록 출력 버튼 클릭 시
printStudent.addEventListener("click", () => {
  /* 이전 #result에 작성되어 있는 내용 삭제 */
  result.innerHTML = '';

  // studentList에 존재하는 모든 학생 객체 접근하기
  for (let i = 0; i < studentList.length; i++) {

    // 학생 정보를 한 줄로 반환하는 메서드 호출
    const info = studentList[i].information();

    // li 태그 생성
    const li = document.createElement("li");

    // li의 내용으로 info 대입
    li.innerText = info;

    // li를 result의 마지막 자식으로 추가
    result.append(li);
  }
});

/* JSON 확인 */
const checkJSON = document.querySelector("#checkJSON");
checkJSON.addEventListener("click", () => {
  // JS 객체 생성
  const obj = {};
  obj.id = "user01";
  obj.pw = "pass01";
  obj.email = "user01@naver.com";
  obj.point = 10000;

  console.log("obj 객체 : ", obj);

  // JS객체 ==> JSON (문자열) 반환 (STRING ify)
  const json1 = JSON.stringify(obj);
  console.log("json 확인 : ", json1, typeof json1);

  const obj1 = JSON.parse(json1);
  console.log("obj1 : ", obj1);


  // JSON 생성 (JS 객체 모양의 문자열)
  const json2 = '{"menu":"김밥","price":4000}'
  console.log("json2 확인 : ", json2, typeof json2);

  // JSON (문자열) ==> JS 객체
  const obj2 = JSON.parse(json2);
  console.log("obj2 : ", obj2);

  // 기상청 서버에 7/12 전국 미세먼지 농도를 JSON으로 받아오기
  fetch('https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustFrcstDspth?serviceKey=BTjX5Rqk5SZSwrW687YxxqoqoH7FbYV/BKqfde1PPn0jiIoOy6aYAUb1MuK7h9izWzM/YX6SVOjBBUMIuwRRIg==&returnType=JSON&numOfRows=100&pageNo=1&searchDate=2024-07-12&InformCode=PM10')
  .then( response => response.json())
  .then( result => {
    console.log(result)
  } );


});