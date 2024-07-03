/* 변수 선언 */
// 메모리(RAM)에 값을 저장할 공간을 만들고 이름을 붙이는 것
// 작성법 : 변수종류 변수명;

var number1;
var number2;

/* 변수에 값 대입 */
// 선언된 변수(공간)에 값을 대입(집어 넣음)
// 작성법 : 변수명 = 값

// =(등호)  : 대입연산자
// A = B   ==> B를 A에 대입(오른쪽 값을 왼쪽 변수에 대입)

number1 = 10; // number1 변수에 10 대입
number2 = 20; // number2 변수에 20 대입

/* 변수 사용하기 */
// 필요한 곳에서 변수명 작성
console.log("number1 변수에 저장된 값 : ", number1);
console.log("number2 변수에 저장된 값 : ", number2);
console.log("\n");  //줄바꿈

function operator() {
  console.log("number1 + number2 = ", number1 + number2);
  console.log("number1 - number2 = ", number1 - number2);
  console.log("number1 * number2 = ", number1 * number2);
  console.log("number1 / number2 = ", number1 / number2);
  console.log("number1 % number2 = ", number1 % number2);
  console.log("number1 ** number2 = ", number1 ** number2); // 이게 제곱
  console.log("number1 ^ number2 = ", number1 ^ number2);   // 이건 XOR
}
// 선언된 변수는 콘솔에서도 사용 가능

// (에러) number3 is not defined ==> number3라는 변수가 정의되지 않았다 == number3 선언 안됨 / 존재 X

/* 1. var  */
/* 변수, 변수명 중복 (덮어쓰기), 함수 레벨 범위(scope) */
function varCheck() {
  var num = 0;

  var menu = "콩국수";
  console.log("초기의 menu : ", menu, num++);
  menu = "초밥";
  console.log("변경된 menu : ", menu, "(var X)", num++);
  var menu = "국밥";
  console.log("변경된 menu : ", menu, "(var O)", num++);
  // var를 이용할 경우 변수명이 중복되면 이전 값을 새로운 값으로 덮어쓰기 함
  // 문제점 : 이전에 대입한 값을 이용할 수 있다.
  // ==> 나중에 이전 값을 사용하고 싶은데 나도 모르게 새로운 값을 대입해 지워버리는 경우가 생김
}

/* 2. let */
/* 변수, 변수명 중복 X, 블록 {} 레벨 범위 */
function letCheck() {
  let num = 0;

  let name = "홍길동";
  console.log("초기의 name : ", name, num++);
  // let name = "김미영";
  // 02_변수와자료형.js:59 Uncaught SyntaxError: Identifier 'name' has already been declared ==> name 변수가 이미 선언됨
  // ==> 해결방법
  // 1. 변수에 대입된 값을 변경하는 것이 목적 ==> let 제거
  // name = "김미영";
  // 2. 새로운 변수 생성이 목적 ==> 변수명 변경
  // let name2 = "김미영"


  name = "김미영";
  console.log("변경된 name : ", name, num++)
}

/* 3. const */
/* 상수(constant, 항상 같은 수), 변수명 중복 X, 블록 {} 레벨 범위 */
function constCheck() {
  const nationalCode = 82;
  console.log("국가 코드 : ", nationalCode);

  // nationalCode++; // const에 값을 대입해도 빨간줄 안뜸
  // console.log("국가 코드 : ", nationalCode); 
  //TypeError: Assignment to constant variable.
  // ==> 상수 변수에 값이 할당됨. 상수는 값을 다시 대입(할당) 할 수 없어서 오류 발생
  /* 해결 방법 : const를 let으로 변경하여 수정 가능한 함수로 */
  // const에 '값'을 넣지 말고 '변수'를 대입하면 const에 저장된 값도 변경이 가능 ==> 연결관계를 수정하면 안될때 사용할것
  // at constCheck (02_변수와자료형.js:80:15)   //80번째 줄 15번째 글자(letter)에서 오류발생
  // at HTMLButtonElement.onclick (02_변수와자료형.html:41:48)
}

/* var, let, const의 scope 확인 */
/* 선수 지식
  1. 전역 변수 : 어디서든 사용 가능한 변수

  2. 지역 변수 : 특정 지역에서 사용 가능한 변수
    1) 함수 레벨 범위
    - function 함수명(){}   
      함수 정의 부분에서 {} 내부에
      var로 선언된 변수는 
      함수 내부 어디서든 사용 가능
    
    2) 블록 레벨 범위
*/

// 함수 선언
function scopeTest1() {
  // 함수 정의
  var num1 = 100;
  // 만약 num1에 저장된 값이 100과 같다면
  // {} 내부 코드를 실행
  if (num1 == 100) {
    var num2 = 200; //var를 이용해서 num2 변수 선언
  }
  // var로 선언되었기 때문에 함수 {}레벨 지역 변수 ==> if문이 끝나도 함수가 끝난게 아니기 때문에 함수{} 안에서 어디서든 사용 가능
  console.log("num2 : " + num2); //num2 : 200 출력됨

  let num3 = 300;
  if (num3 == 300) {
    let num4 = 400; // 블록 {} 레벨 지역 변수 == 블록을 벗어나면 사용할 수 없다 == 블록 내부에서만 사용 가능하다
    console.log("if내부 num4 : ", num4);
  }
  console.log("num4 : " + num4);
  // num4 is not defined ==> num4가 정의되지 않음
}

function scopeTest_1() {
  num2++;
}

/* 변수 선언 위치에 따른 범위 */
// 전역 변수 선언
const global1 = "전역 변수 1";
const global2 = "전역 변수 2";

function scopeTest2() {
  const fn1 = "함수 지역변수 1";  // 상수(값 변경 X)
  let fn2 = "함수 지역변수 2";    // 변수(값 변경 O)
  const temp = 123; // 테스트를 위한 임시 값 (함수 지역변수)
  //const temp = 456; // [변수명 중복] 블록 범위 변수 'temp'을(를) 다시 선언할 수 업습니다.

  console.log("if 실행 전 fn2 : ", fn2); // 함수 지역 변수 2
  console.log("if 실행 전 temp : ", temp); // 함수 지역 변수 temp

  if (1 == 1) {
    const local1 = "if 지역 변수 1";

    // 넓은 범위(함수)에서 선언된 변수는 좁은 범위(함수 내 if)에서 사용 가능!!
    // ==> 상위 레벨에서 하위 레벨로 내려오는건 가능, 상속이랑 관련있는건지?
    console.log("if 내부에서 fn1: ", fn1);
    console.log("if 내부에서 local1: ", local1);
    fn2 = "변경된 fn2";
    //if {} 내부(좁은 범위)에서 함수 {} 내부{}(넓은 범위)의 변수 값을 변경

    const temp = 456;   //함수 지역변수 const temp와 같은 이름의 if 지역변수 temp를 선언
    console.log("if 내부 temp : ", temp); // 함수 지역 변수 temp == 456
  }
  console.log("if 실행 후 fn2: ",fn2);  //변경된 fn2
  console.log("if 실행 후 temp : ", temp); // 함수 지역 변수 temp  == 123
  // 1) 범위가 다르면 변수명 중복 O
  // 2) 중복되는 변수 사용 시 현재 범위의 변수가 호출된다

  //console.log("local1: ", local1);
  //if문 지역변수 local1을 if문 밖에서 호출하면 "정의되지 않음" 오류 발생
  const global2 = "전역인척 변수 2";
  console.log("function 내부 선언 global1: ", global1 + "/" + global2);
}

function typeCheck() {
  // 함수 정의
  /* typeof 연산자 : 변수 / 값의 자료형을 확인해주는 연산자 */
  /* undefined : 정의되지 않은 변수 / 값 대입 X 또는 존재 X 변수 */
  let undef; //undef 라는 이름의 변수를 선언만 했음
  console.log("undef\t: ", undef, "\t| 자료형 : "+ typeof undef);
  //            문자열   저장된값   자료형
  
  /* string(문자열) : "" 또는 '' 내부에 작성된 값 */
  /* ex) "홍길동" == '홍길동' */
  const name = "홍길동";
  const phone = "01012341234";

  console.log("name\t: ", name, "\t\t| 자료형 : " + typeof name);
  console.log("phone\t: ", phone, "\t| 자료형 : " + typeof phone);

  /* number(정수, 실수 (숫자)) */
  const age = 25;
  const height = 180.4; //0180.4 로 해도 180.4로
  const sight = -4.0;

  console.log("age\t\t: ", age, "\t\t\t| 자료형 : " + typeof age);
  console.log("height\t: ", height, "\t\t| 자료형 : " + typeof height);
  console.log("sight\t: ", sight, "\t\t\t| 자료형 : " + typeof sight);

  /* boolean(논리형) : ture / false */
  console.log("true \t: ", true, "\t\t| 자료형 : " +typeof true);
  console.log("1 \t\t: ", 1, "\t\t\t| 자료형 : " + typeof true);
  console.log("false \t: ",false, "\t\t| 자료형 : " +  typeof false);
  console.log("0 \t\t: ", 0, "\t\t\t| 자료형 : " + typeof false);

  /* object(객체) */
  /* (쉬운 버전 설명) 값을 여러 개 저장할 수 있는 형태 */

  // 1) 배열 : 변수의 묶음
  const numbers = [10, 20, 30]; //배열 생성
  console.log("numbers : ", numbers, typeof numbers);

  // 배열에 저장된 값을 하나씩 꺼냈을 때의 타입 검사
  console.log("numbers 배열의 값 중 [0] 번째 : ", numbers[0], typeof numbers[0]);
  console.log("numbers 배열의 값 중 [1] 번째 : ", numbers[1], typeof numbers[1]);
  console.log("numbers 배열의 값 중 [2] 번째 : ", numbers[2], typeof numbers[2]);

  // 2) JS 객체
  // 값을 K:V 형식으로 여러 개 저장하는 형태 ==> { K:V, K:V, K:V }
  // (쉽게 이해하는 방법) K(Key) == 변수명 / V(Value) == 변수에 저장된 값

  const user = {
    id : "user01",    // let id = "user01";
    pw : "pswd01",    // let pw = "pswd01";
    point : 1200      // let point = 1200;
  };

  console.log("user : ", user, typeof user);
  // user 객체에 저장된 값 하나씩 꺼내기
  /* .(점) 연산자 : 내부/하위 접근 연산자 */
  console.log("user.id \t: ", user.id, typeof user.id);
  console.log("user.pw \t: ", user.pw, typeof user.pw);
  console.log("user.point\t: ", user.point, typeof user.point);

  /* 함수(function) */
  /* 
    함수도 변수에 저장 가능!!!
    [작성법] : const 함수명 = function(){}
  */

    const sumFn = function(a, b){ return a + b; }

    console.log( typeof sumFn ); // function
    console.log( sumFn );
    console.log( sumFn(1, 3) ) // 1 + 3 = 4

}

function typeCheck2() {
  let undef;
  undef = 100;
  console.log("100 \t:", undef, "\t\t| 자료형 : "+ typeof undef);
  undef = '200';
  console.log("'200'\t:", undef, "\t\t| 자료형 : "+ typeof undef);
  undef = "AAA";
  console.log("'AAA'\t:", undef, "\t\t| 자료형 : "+ typeof undef);
  undef = true;
  console.log("true\t:", undef, "\t\t| 자료형 : "+ typeof undef);
  undef = Boolean(0);
  console.log("Boolean(0)\t:", undef, "| 자료형 : "+ typeof undef);
  undef = null;
  console.log("null\t:", undef, "\t\t| 자료형 : "+ typeof undef);
}


// ====================================== 이하 개인적 장난질
var vnumCount = 0;
let lnumCount = 0;
function constCheck2() {
  const vclickCount = vnumCount++;
  const lclickCount = lnumCount++;
  console.log("카운트 : ", vclickCount, lclickCount);
}

function varTest() {
  console.log("전역| var카운트 : ", vnumCount++, "let카운트 : ", lnumCount++);
}

function letTest() {
  console.log("블록| var카운트 : ", vnumCount++, "let카운트 : ", lnumCount++);
}

function letTest2() {
  var vnumCount = 8;
  let lnumCount = 8;
  console.log("블록| var카운트 : ", vnumCount++, "let카운트 : ", lnumCount++);
}