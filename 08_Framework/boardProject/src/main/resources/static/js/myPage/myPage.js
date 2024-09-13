/* 다음 주소 API로 주소 검색하기 */

function findAddress() {
  new daum.Postcode({

      oncomplete: function(data) {
          // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

          // 각 주소의 노출 규칙에 따라 주소를 조합한다.
          // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
          var addr = ''; // 주소 변수

          //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
          if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
              addr = data.roadAddress;
          } else { // 사용자가 지번 주소를 선택했을 경우(J)
              addr = data.jibunAddress;
          }

          // 우편번호와 주소 정보를 해당 필드에 넣는다.
          document.getElementById('postcode').value = data.zonecode;
          document.getElementById("address").value = addr;
          // 커서를 상세주소 필드로 이동한다.
          document.getElementById("detailAddress").focus();
      }
  }).open();
}

/* 주소 검색 버튼 클릭 시 */
document.querySelector("#findAddressBtn")
    .addEventListener("click", findAddress);

// 함수명만 작성하는 경우
// 함수명() 작성하는 경우 : 함수에 정의된 내용을 실행

// ----------------------------------------------------------------------------------------------------

/* ==================== 유효성 검사 (Validation) ==================== */

// 입력 값이 유효한 형태인지 표시하는 객체(체크리스트)
const checkObj = {
    "memberNickname" : true
}

/* 닉네임 검사 */
// - 3글자 이상
// - 중복 x
const memberNickname = document.querySelector("#memberNickname");
memberNickname.addEventListener("input", () => {
    // input 이벤트 : 입력과 관련된 모든 동작 (JS를 이용한 값세팅 제외)
    // ==> key down / key up / click 이런거?

    // 입력된 값 얻어오기(양쪽 공백 제거)
    const inputValue = memberNickname.value.trim();

    if(inputValue.length < 3) {
        // 클래스 제거
        memberNickname.classList.remove("green");
        memberNickname.classList.remove("red");

        // 닉네임이 유요하지 않다고 기록
        checkObj.memberNickname = false;

        return;
    }

    // 비동기로 입력된 닉네임이
    // DB에 존재하는지 확인하는 Ajax 코드 작성

    // GET 방식 요청 (쿼리 스트링으로 파라미터 전달)
    fetch("/myPage/checkNickname?input=" + inputValue)
    .then(response => {
        if(response.ok) {           // 응답 상태코드 200(성공)인 경우
            return response.text(); // 응답 결과를 text 형태로 반환
        }
        throw new Error("중복 검사 실패 : " + response.status);
    })
    .then(result => {
        // result == 첫 번째 then 에서 return 된 값

        if(result > 0 ){ // 중복인 경우
            memberNickname.classList.add("red");
            memberNickname.classList.remove("green")
            checkObj.memberNickname = false;    // 체크리스트에 false 기록
            return;
        }

        // 중복이 아닌 경우
        memberNickname.classList.add("green");
        memberNickname.classList.remove("red");
        checkObj.memberNickname = true; // 체크리스트에 true 기록

    })
    
    .catch(err => console.error(err));

});

// ----------------------------------------------------------------------------------------------------

