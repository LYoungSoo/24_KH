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
if(document.querySelector("#findAddressBtn") !== null) {
    document.querySelector("#findAddressBtn")
        .addEventListener("click", findAddress);
}
// 함수명만 작성하는 경우
// 함수명() 작성하는 경우 : 함수에 정의된 내용을 실행

// ----------------------------------------------------------------------------------------------------

/* ==================== 유효성 검사 (Validation) ==================== */

// 입력 값이 유효한 형태인지 표시하는 객체(체크리스트)
const checkObj = {
    "memberNickname" : true,
    "memberTel"      : true
}

/* 닉네임 검사 */
// - 3글자 이상
// - 중복 x
const memberNickname = document.querySelector("#memberNickname");

// 객체?.속성 ==> ? : 안전 탐색 연산자
// - 객체가 null 또는 undefined가 아닐때만 수행

// 기존 닉네임
const originalNickname = memberNickname?.value;
memberNickname?.addEventListener("input", () => {
    // input 이벤트 : 입력과 관련된 모든 동작 (JS를 이용한 값세팅 제외)
    // ==> key down / key up / click 이런거?

    // 입력된 값 얻어오기(양쪽 공백 제거)
    const inputValue = memberNickname.value.trim();

    if(inputValue.length < 3) {     // 3글자 미만
        // 클래스 제거
        memberNickname.classList.remove("green");
        memberNickname.classList.remove("red");

        // 닉네임이 유요하지 않다고 기록
        checkObj.memberNickname = false;

        return;
    }

    // 입력된 닉네임이 기존 닉네임과 같을 경우
    if(originalNickname === inputValue) {
        //클래스 제거
        memberNickname.classList.remove("green");
        memberNickname.classList.remove("red");

        // 닉네임이 유요하다고 기록
        checkObj.memberNickname = true;
        return;
    }

    /* 닉네임 유효성 검사 */
    // - 영어 또는 숫자 또는 한글만 작성 가능
    // - 3글자 ~ 10글자
    const lengthCheck = inputValue.length >= 3 && inputValue.length <= 10;
    const validCharactersCheck = /^[a-zA-Z0-9가-힣]+$/.test(inputValue); // 영어, 숫자, 한글만 허용
    
    // ^  : 문자열 시작
    // $  : 문자열 끝
    // [] : 한 칸(한 문자)에 들어갈 수 있는 문자 패턴 기록
    // +  : 1개 이상
    
    // 조건이 하나라도 false 인 경우 == 모두 참이 아닌 경우
    if((lengthCheck && validCharactersCheck) === false) {
        memberNickname.classList.remove("green");
        memberNickname.classList.add("red");

        // 닉네임이 유효하지 않다고 기록
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

/* 전화번호 유효성 검사 */
const memberTel = document.querySelector("#memberTel");
memberTel?.addEventListener("input", () => {

    // 입력된 전화번호
    const inputTel = memberTel.value.trim();

    // 전화번호 정규식 검사
    // 010으로 시작하고 11글자
    const validFormat = /^010\d{8}$/;

    // 입력 받은 전화번호가 유효한 형식이 아닌 경우 == 010으로 시작하지 않거나, 11글자 미만인 경우
    if(!validFormat.test(inputTel)) {
        memberTel.classList.add("red");
        memberTel.classList.remove("green");
        checkObj.memberTel = false;
        return;
    }

    // 유효한 경우
    memberTel.classList.add("green");
    memberTel.classList.remove("red");
    checkObj.memberTel = true;
});

// ----------------------------------------------------------------------------------------------------
/* 내 정보 수정 form 제출 시 */
const updateInfo = document.querySelector("#updateInfo");
updateInfo?.addEventListener("submit", e => {

    // checkObj에 작성된 값 검사하기
    // ==> 닉네임, 전화번호 유효한지 검사

    // for ~ in 구문 : JS 객체의 key 값을 하나씩 접근하는 반복문
    for(let key in checkObj) {
        // 닉네임, 전화번호 중 유효하지 않은 값이 있을 경우
        if(checkObj[key] === false) {
            let str = " 유효하지 않습니다"
            switch(key) {
                case "memberNickname" : str = "닉네임이"   + str; break;
                case "memberTel"      : str = "전화번호가" + str; break;
            }

            alert(str);     // ㅁㅁㅁ 이 유효하지 않습니다 출력
            e.preventDefault();     // form 제출 막기
            document.getElementById(key).focus();       // focus 맞추기 (커서 옮겨놓기)
            return;
        }
    }

    /* 주소 유효성 검사 */
    // - 모두 작성 또는 모두 미작성
    // const postcode      = document.querySelector("#postcode").value.trim();
    // const address       = document.querySelector("#address").value.trim();
    // const detailAddress = document.querySelector("#detailAddress").value.trim();
    const addr = document.querySelectorAll("[name = memberAddress]");

    let empty = 0;      // 비어있는 input의 개수
    let notEmpty = 0;   // 비어있지 않은 input의 개수

    // for ~ of 향상된 for문
    for(let item of addr) {
        let len = item.value.trim().length;     // 작성된 값의 길이
        if(len > 0) notEmpty++; // 비어있지 않은 경우
        else        empty ++;   // 비어있을 경우
    }
    
    if(empty < 3 && notEmpty < 3) {
        alert("주소가 유효하지 않습니다(모두 작성 또는 미작성)");
        e.preventDefault();
        return;
    }
});

// ----------------------------------------------------------------------------------------------------
/*
    [비밀번호 변경 유효성 검사]

    1. 현재 비밀번호
    새 비밀번호
    새 비밀번호 확인
    입력 여부 체크

    2. 비밀번호가 알맞은 형태로 작성 되었는가 확인
    - 영어(대소문자 가리지 X) 1글자 이상
    - 숫자 1글자 이상
    - 특수문자(! @ # _ -) 1글자 이상
    - 최소 6글자 최대 20글자

    3. 새 비밀번호, 새 비밀번호 확인이 같은지 체크
*/

/* 비밀번호 변경 */

const changePw = document.querySelector("#changePw");

// "변경하기" 버튼 클릭 시 또는 form 내부 엔터 입력 시
// == submit (제출)
changePw?.addEventListener("submit", e => {
    // e.preventDefault();     // 기본 이벤트 막기
    // ==> form의 기본 이벤트인 제출 막기
    // ==> 유효성 검사 조건이 만족되지 않을 때 수행

    // 입력 요소 모두 얻어오기
    const currentPw = document.querySelector("#currentPw");
    const newPw = document.querySelector("#newPw");
    const newPwConfirm = document.querySelector("#newPwConfirm");

    // 1. 현재 비밀번호, 새 비밀번호, 새 비밀번호 확인
    //    입력 여부 체크

    let str;    // 값을 대입하지 않았으므로 undefinded 상태
    if(newPwConfirm.value.trim().length == 0) str = "새 비밀번호 확인을 입력해 주세요";
    if(newPw.value.trim().length == 0) str = "새 비밀번호를 입력해 주세요";
    if(currentPw.value.trim().length == 0) str = "현재 비밀번호를 입력해 주세요";
    
    if(str !== undefined) {     // 입력되지 않은 값이 존재
        alert(str);
        e.preventDefault();     // form 제출 막기
        return;                 // submit 이벤트 핸들러 종료
    }

    // 2. 새 비밀번호가 알맞은 형태로 작성 되었는가 확인
    // - 영어(대소문자 가리지 X) 1글자 이상
    // - 숫자 1글자 이상
    // - 특수문자(! @ # _ -) 1글자 이상
    // - 최소 6글자 최대 20글자
    
    /* 정규 표현식(regEx) */
    // https://developer.mozilla.org/ko/docs/Web/JavaScript/Guide/Regular_expressions

    // - 문자열에서 특정 문자 조합을 찾기 위한 패턴

    // ex) 숫자가 3개 이상 작성된 문자열 조합 찾기
    // "12abc" -> X
    // "444"   -> O
    // "1q2w3e" -> O

    /*
        [JS 정규 표현식 객체 생성 방법]
        1. /정규표현식/
        2. new RegExp("정규표현식")
    */
    
    const lengthCheck = newPw.value.length >= 6 && newPw.value.length <= 20;
    const letterCheck = /[a-zA-Z]/.test(newPw.value); // 영어 알파벳 포함
    const numberCheck = /\d/.test(newPw.value); // 숫자 포함
    const specialCharCheck = /[!@#_-]/.test(newPw.value); // 특수문자 포함

    /*
        function validatePassword(password) {
            const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#_\-])[A-Za-z\d!@#_\-]{6,20}$/;
            return regex.test(password);
        }
    */

    // 조건이 하나라도 만족하지 못하면
    if (!(lengthCheck && letterCheck && numberCheck && specialCharCheck)) {
        alert("영어,숫자,특수문자 1글자 이상, 6~20자 사이로 입력해주세요");
        e.preventDefault();
        return;
    }

    // 3. 새 비밀번호, 새 비밀번호 확인이 같은지 체크
    if(newPw.value !== newPwConfirm.value) {
        alert("새 비밀번호가 일치하지 않습니다")
        e.preventDefault();
        return;
    }
});

// ----------------------------------------------------------------------------------------------------

const secession = document.querySelector("#secession");
secession?.addEventListener("submit", e => {
    // 1) 비밀번호 입력 확인
    const memberPw = document.querySelector("#memberPw");
    if(memberPw.value.trim().length === 0) {    // 미입력
        alert("비밀번호를 입력해 주세요");
        e.preventDefault();
        return;
    }

    // 2) 체크박스 체크 여부 확인
    const agree = document.querySelector("#agree");

    if(agree.checked === false) {   // 체크가 되어있지 않은 경우
        alert("탈퇴를 원하시면 동의를 체크해 주세요");
        e.preventDefault();
        return;
    }

    // 3) confirm을 이용해서 탈퇴할건지 확인
    if(confirm("정말루?") === false) {
        // 취소 클릭 시
        alert("탈퇴 취소");
        e.preventDefault();
        return;
    } else {
        if (confirm("정말 탈퇴 안하시겠습니까?") === true) {
            alert("탈퇴 취소");
            e.preventDefault();
            return;
        }
    }
});

// ----------------------------------------------------------------------------------------------------

/* 프로필 이미지 미리보기, 삭제하기 */

// 프로필 이미지 업로드 상태에 따라서 어떤 상태인지 구분하는 값
// -1 : 프로필 이미지를 바꾼적이 없음(초기상태)
//  0 : 프로필 이미지삭제(X버튼 클릭)
//  1 : 새 프로필 이미지를 선택
let statusCheck = -1;
let lastValidFile = null;     // 마지막으로 선택된 파일을 저장할 변수

// 미리보기가 출력될 img
const profileImg = document.querySelector("#profileImg");

// 프로필 이미지를 선택할 input
const imageInput = document.querySelector("#imageInput");

// 기본 이미지로 변경할 x버튼
const deleteImage = document.querySelector("#deleteImage");

// 프로필 변경 화면일 경우에만 동작
if(imageInput != null) {

    /**
     * 미리 보기 함수
     * @param {} file : input type="file" 에서 선택된 파일
     */
    const updatePreview = (file) => {

        // 선택된 파일을 lastValidFile 에 대입(복사)
        lastValidFile = file;

        // JS 에서 제공하는 파일을 읽어오는 객체
        const reader = new FileReader();

        // 파일을 읽어오는데
        // DataURL 형식으로 읽어옴
        // DataURL : 파일 전체 데이터가 브라우저가 해석할 수 있는
        //           긴 주소형태 문자열로 변환
        reader.readAsDataURL(file);

        // 선택된 파일이 다 인식 되었을 때
        reader.addEventListener("load", e => {

            profileImg.src = e.target.result;
            // e.target.result == 파일이 변환된 주소 형태 문자열

            statusCheck = 1;    // 새 파일이 선택된 상태 체크

        });

    }

    // input type="file" 태그가 선택한 값이 변한 경우 수행
    imageInput.addEventListener("change", e => {

        // 선택된 파일 1개를 얻어옴
        const file = e.target.files[0];

        // 선택된 파일이 없을 경우
        if(file === undefined) {

            /* 이전 선택한 파일 유지하는 코드 */
            // ==> 이전 선택한 파일을 저장할 전역 변수(lastValidFile) 선언

            // 이전에 선택한 파일이 없는 경우
            // == 현재 페이지 들어와서 프로필 이미지 바꾼 적이 없는 경우
            if(lastValidFile === null) return;
            
            // 이전에 선택한 파일이 "있을" 경우
            const dataTransfer = new DataTransfer();

            // DataTransfer 가 가지고 있는 files 필드에 lastValidFile 추가
            // ==> lastValidFile 을 요소로 포함한 FileList 생성됨
            dataTransfer.items.add(lastValidFile);

            // input의 files 변수에 lastValidFile 이 추가된 files 대입
            imageInput.files = dataTransfer.files;

            // 히전 선택된 파일로 미리보기 되돌리기
            updatePreview(lastValidFile);


            return;
        }

        // 선택된 파일이 있을 경우
        updatePreview(file);    // 미리보기 함수 호출

    });

    /* X버튼 클릭 시 기본 이미지로 변환 */
    deleteImage.addEventListener("click", () => {

        // 미리보기를 기본 이미지로 변경
        profileImg.src = userDefaultImage;

        // input 태그와
        // 마지막 선택된 파일을 저장하는 lastValidFile에
        // 저장된 값을 모두 삭제

        imageInput.value = '';
        lastValidFile = null;

        statusCheck = 0;    // 삭제 상태 체크

    });

}

/* 프로필 화면에서 변경하기 버튼이 클릭된 경우 */
const profileForm = document.querySelector("#profile");

profileForm?.addEventListener("submit", e => {

    let flag = true;        // true 인 경우 제출 불가능

    // 프로필 이미지 미변경 시 제출 불가
    if(statusCheck === -1) flag = true;

    // 기존 프로필 이미지 X ==> 새 이미지 선택
    if(loginMemberProfileImg === null && statusCheck === 1) flag = false;

    // 기존 프로필 이미지 O ==> X 버튼을 눌러 삭제
    if(loginMemberProfileImg !== null && statusCheck === 0) flag = false;

    // 기존 프로필 이미지 O ==> 새 이미지 선택
    if(loginMemberProfileImg !== null && statusCheck === 1) flag = false;

    if(flag === true) {
        e.preventDefault();
        alert("이미지 변경 후 클릭하세요");
    }

});