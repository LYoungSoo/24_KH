/* 
		WebSocket
		- 클라이언트 <-> 서버  간 전이중 통신
		ex) 전화
			- 양방향
			- 실시간
			- 서로 동시에 수신/송신이 가능함
			- 클1 <-> 서버 <-> 클2
		
		- ws:// 프로토콜 요청 (http가 아님!!)
			==> HTTP에서 사용하는 request, session 을 사용할 수 없음
		
		==> SessionHandShakeInterceptor		// 소매치기
				가 HTTP의 Session 을 가로채서 WebSocket 에서 사용 가능한 형태로 변환

		==> 어떤 요청 주소가 WebSocket 요청인지 지정
 */

// 채팅에 사용될 SockJS 객체를 저장할 변수 ==> 웹소켓 쉽게 쓸 수 있는 객체 ===> 맨 아래 라이브러리 등록됨
let chattingSock;

// 로그인이 되어 있을 경우
if(notificationLoginCheck) {		// common.html 에 선언된 전역 변수

	// 서버로 ws:// chattingSock 요청
	// ==> 요청으로 처리하는 WebSockHandler 와 연결
	// ===> WebSocketHandler 에 연결된 회원의 정보를 모아두게 된다!!!
	chattingSock = new SockJS("/chattingSock");
}


/* 채팅 메시지를 보내는 함수 */
const sendMessage = () => {

  // 채팅 입력 textarea
  const inputChatting = document.querySelector("#inputChatting");
  const msg = inputChatting.value.trim(); // 입력된 채팅 메시지

  // 로그인이 되어있지 않으면 함수 종료
  if(!notificationLoginCheck) return;

  if(msg.length === 0){ // 채팅 미입력
    alert("채팅을 입력해 주세요");
    return;
  }

  // 웹소켓 핸들러로 전달할 채팅 관련 데이터를 담은 객체 생성
  const chattingObj = {
    "targetNo" : selectTargetNo,    // 메시지를 받을 대상의 회원 번호(웹소켓)
    "messageContent" : msg,         // 전달할 메시지 내용
    "chattingRoomNo" : selectChattingNo // 채팅방 번호(DB 저장용도)
  }

  // JSON으로 변환하여 웹소켓 핸들러로 전달
  chattingSock.send( JSON.stringify(chattingObj) );

  inputChatting.value = ""; // 보낸 채팅 내용 삭제
}

// -------------------------------------------------- 위는 보내기 아래는 받기

/* 연결된 웹소켓 객체를 통해 서버로 부터 메시지를 전달 받은 경우 */
if(chattingSock != undefined){

  chattingSock.addEventListener("message", e => {
    console.log(e.data);

    // 메소드를 통해 전달받은 JSON을 JS Object로 변환해서 msg 변수에 저장.
    const msg = JSON.parse(e.data); 
    console.log(msg);


    // 현재 채팅방을 보고있는 경우
    if(selectChattingNo == msg.chattingRoomNo){


      const ul = document.querySelector(".display-chatting");
    
      // 메세지 만들어서 출력하기
      //<li>,  <li class="my-chat">
      const li = document.createElement("li");
    
      // 보낸 시간
      const span = document.createElement("span");
      span.classList.add("chatDate");
      span.innerText = msg.sendTime;
    
      // 메세지 내용
      const p = document.createElement("p");
      p.classList.add("chat");
      p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML
    
      // 내가 작성한 메세지인 경우
      if(loginMemberNo == msg.senderNo){ 
        li.classList.add("my-chat");
        
        li.append(span, p);
        
      }else{ // 상대가 작성한 메세지인 경우
        li.classList.add("target-chat");
    
        // 상대 프로필
        const img = document.createElement("img");
        img.setAttribute("src", selectTargetProfile);
        
        const div = document.createElement("div");
    
        // 상대 이름
        const b = document.createElement("b");
        b.innerText = selectTargetName; // 전역변수
    
        const br = document.createElement("br");
    
        div.append(b, br, p, span);
        li.append(img,div);
    
      }
    
      ul.append(li)
      ul.scrollTop = ul.scrollHeight; // 스크롤 제일 밑으로
    }

    selectRoomList();
  })
}

// ----------------------------------------------------------------------------------------------------

let selectChattingNo;     // 선택한 채팅방 번호
let selectTargetNo;       // 현재 채팅 대상
let selectTargetName;     // 대상의 이름
let selectTargetProfile;  // 대상의 프로필

/* 사용자 검색 팝업 레이어 열기 */
const addTarget         = document.querySelector("#addTarget");             // 추가 버튼
const addTargetPopupLayer = document.querySelector("#addTargetPopupLayer");     // 팝업 레이어
const closeBtn          = document.querySelector("#closeBtn");              // 팝업 닫기
const targetInput       = document.querySelector("#targetInput");           // 검색창
const resultArea        = document.querySelector("#resultArea");            // 검색 결과 목록

// 추가 버튼 클릭 시
addTarget.addEventListener("click", () => {
  addTargetPopupLayer.classList.remove("popup-layer-close");
  targetInput.focus();
});

// 닫기(X) 버튼 클릭 시
closeBtn.addEventListener("click", () => {
  addTargetPopupLayer.classList.add("popup-layer-close");
  resultArea.innerHTML = "";    // 검색 목록 내용 지우기
});

// 사용자 검색(AJAX)
targetInput.addEventListener("input", () => {

  // 입력된 값
  const query = targetInput.value.trim();

  // 입력된 값이 없을 경우
  if(query.length === 0) {
    resultArea.innerHTML = "";    // 검색 결과 목록 삭제
    return;
  }

  // 입력된 값이 있을 경우
  fetch("/chatting/selectTarget?query=" + query)
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("검색 실패");
  })
  .then(list => {
    console.log(list);

    resultArea.innerHTML = ""; // 이전 검색 결과 비우기

    if (list.length == 0) {
      const li = document.createElement("li");
      li.classList.add("result-row");
      li.innerText = "일치하는 회원이 없습니다";
      resultArea.append(li);
      return;
    }

    for (let member of list) {
      // li요소 생성(한 행을 감싸는 요소)
      const li = document.createElement("li");
      li.classList.add("result-row");
      li.setAttribute("data-id", member.memberNo);

      // 프로필 이미지 요소
      const img = document.createElement("img");
      img.classList.add("result-row-img");

      // 프로필 이미지 여부에 따른 src 속성 선택
      if (member.profileImage == null) img.setAttribute("src", userDefaultImage);
      else img.setAttribute("src", member.profileImage);

      let nickname = member.memberNickname;
      let email = member.memberEmail;

      const span = document.createElement("span");
      span.innerHTML = `${nickname} ${email}`.replace(query, `<mark>${query}</mark>`);

      // 요소 조립(화면에 추가)
      li.append(img, span);   // 채팅참여 검색된 맴버 한 줄
      resultArea.append(li);

      // 클릭 시 채팅방 입장 함수 호출
      li.addEventListener("click", chattingEnter);
    }
  })
  .catch(err => console.error(err));

});

/**
 * 채팅방 입장
 * @param e : 이벤트 객체
 */
const chattingEnter = (e) => {

  // e.currentTarget : 이벤트 리스너가 설정된 요소
  // data-id 값을 얻어와 저장(참여자 회원 번호)
  const targetNo = e.currentTarget.dataset.id;
  console.log(targetNo);

  fetch("/chatting/enter", {
    method : "POST",
    headers  : {"Content-Type" : "application/json"},
    body : targetNo
  })
  .then(response => {
    if(response.ok) return response.json();
    throw new Error("입장 실패");
  })
  .then(chattingNo => {
    // chattingNo : 입장한 채팅방 번호
    console.log(chattingNo);

    selectRoomList();   // 비동기로 채팅방 목록 조회

		// 200ms 뒤에 실행
		setTimeout(() => {
			// 입장하려던 채팅방이
			// 이미 채팅방 목록에 존재하는 경우

			// 1) 채팅방 목록 li 태그들 얻어오기
			const itemList = document.querySelectorAll(".chatting-item");

			// 2) li 태그의 "chat-no" 값과 입장하려는 방 번호가 같은 경우
			for(let item of itemList) {
				if(item.getAttribute("chat-no") == chattingNo) {
					item.focus();
					item.click();			// 클릭 ==> selectChattingFn() 호출됨
					
					// 검색창 닫기
					addTargetPopupLayer.classList.add("popup-layer-close");

					// 검색창 내용 비우기
					targetInput.value = "";
					resultArea.innerHtml = "";
					return;
				}
			}
		} , 200)
  })
  .catch(err => console.error(err));

}

// ----------------------------------------------------------------------------------------------------

// 비동기로 채팅방 목록 조회
const selectRoomList = () => {

	fetch("/chatting/roomList")
	.then(resp => resp.json())
	.then(roomList => {
		console.log(roomList);

		// 채팅방 목록 출력 영역 선택 (왼쪽 네모)
		const chattingList = document.querySelector(".chatting-list");

		// 채팅방 목록 지우기
		chattingList.innerHTML = "";

		// 조회한 채팅방 목록을 화면에 추가
		for(let room of roomList){
			const li = document.createElement("li");
			li.classList.add("chatting-item");
			li.setAttribute("chat-no", room.chattingRoomNo);
			li.setAttribute("target-no", room.targetNo);

			if(room.chattingRoomNo == selectChattingNo){
				li.classList.add("select");
			}

			// item-header 부분
			const itemHeader = document.createElement("div");
			itemHeader.classList.add("item-header");

			const listProfile = document.createElement("img");
			listProfile.classList.add("list-profile");

			if(room.targetProfile == undefined)	
				listProfile.setAttribute("src", userDefaultImage);
			else								
				listProfile.setAttribute("src", room.targetProfile);

			itemHeader.append(listProfile);

			// item-body 부분
			const itemBody = document.createElement("div");
			itemBody.classList.add("item-body");

			const p = document.createElement("p");

			const targetName = document.createElement("span");
			targetName.classList.add("target-name");
			targetName.innerText = room.targetNickname;
			
			const recentSendTime = document.createElement("span");
			recentSendTime.classList.add("recent-send-time");
			recentSendTime.innerText = room.sendTime;
			
			
			p.append(targetName, recentSendTime);
			
			
			const div = document.createElement("div");
			
			const recentMessage = document.createElement("p");
			recentMessage.classList.add("recent-message");

			if(room.lastMessage != undefined){
				recentMessage.innerHTML = room.lastMessage;
			}
			
			div.append(recentMessage);

			itemBody.append(p,div);

			// 현재 채팅방을 보고있는게 아니고 읽지 않은 개수가 0개 이상인 경우 -> 읽지 않은 메세지 개수 출력
			if(room.notReadCount > 0 && room.chattingRoomNo != selectChattingNo ){
				const notReadCount = document.createElement("p");
				notReadCount.classList.add("not-read-count");
				notReadCount.innerText = room.notReadCount;
				div.append(notReadCount);
			// } else {
			} else if(selectChattingNo !== undefined && room.chattingRoomNo == selectChattingNo) {
			// 나 왜 오류 안남?


				// 현재 채팅방을 보고있는 경우
				// 비동기로 해당 채팅방 글을 읽음으로 표시
				fetch("/chatting/updateReadFlag",{
					method : "PUT",
					headers : {"Content-Type": "application/json"},
					body : selectChattingNo
					// body : JSON.stringify({"chattingNo" : selectChattingNo})
				})
				.then(resp => resp.text())
				.then(result => console.log(result))
				.catch(err => console.log(err));

			}
			

			li.append(itemHeader, itemBody);
			chattingList.append(li);
		}

		roomListAddEvent();
	})
	.catch(err => console.log(err));
}

// ----------------------------------------------------------------------------------------------------

// 채팅방 목록에 이벤트를 추가하는 함수 ==> 다시 만들어진 화면(요소) 에는 이벤트가 없으므로 이벤트 추가
const roomListAddEvent = () => {
	const chattingItemList = document.getElementsByClassName("chatting-item");
	
	for(let item of chattingItemList){
		item.addEventListener("click", e => {
	
			// 전역변수에 채팅방 번호, 상대 번호, 상태 프로필, 상대 이름 저장
			selectChattingNo = item.getAttribute("chat-no");
			selectTargetNo = item.getAttribute("target-no");

			selectTargetProfile = item.children[0].children[0].getAttribute("src");
			selectTargetName = item.children[1].children[0].children[0].innerText;

			if(item.children[1].children[1].children[1] != undefined){
				item.children[1].children[1].children[1].remove();
			}
	
			// 모든 채팅방에서 select 클래스를 제거
			for(let it of chattingItemList) it.classList.remove("select")
	
			// 현재 클릭한 채팅방에 select 클래스 추가
			item.classList.add("select");
	
			// 비동기로 메세지 목록을 조회하는 함수 호출
			selectChattingFn();
		});
	}
}

// ----------------------------------------------------------------------------------------------------

// 비동기로 메세지 목록을 조회하는 함수
const selectChattingFn = () => {

	fetch(`/chatting/selectMessage?chattingNo=${selectChattingNo}`)
	.then(resp => resp.json())
	.then(messageList => {
		console.log(messageList);

		// <ul class="display-chatting">
		const ul = document.querySelector(".display-chatting");

		ul.innerHTML = ""; // 이전 내용 지우기

		// 메세지 만들어서 출력하기
		for(let msg of messageList){
			//<li>,  <li class="my-chat">
			const li = document.createElement("li");

			// 보낸 시간
			const span = document.createElement("span");
			span.classList.add("chatDate");
			span.innerText = msg.sendTime;

			// 메세지 내용
			const p = document.createElement("p");
			p.classList.add("chat");
			p.innerHTML = msg.messageContent; // br태그 해석을 위해 innerHTML

			// 내가 작성한 메세지인 경우
			if(loginMemberNo == msg.senderNo){ 
				li.classList.add("my-chat");
				
				li.append(span, p);
				
			}else{ // 상대가 작성한 메세지인 경우
				li.classList.add("target-chat");

				// 상대 프로필
				const img = document.createElement("img");
				img.setAttribute("src", selectTargetProfile);
				
				const div = document.createElement("div");

				// 상대 이름
				const b = document.createElement("b");
				b.innerText = selectTargetName; // 전역변수

				const br = document.createElement("br");

				div.append(b, br, p, span);
				li.append(img,div);

			}

			ul.append(li);
			ul.scrollTop = ul.scrollHeight; // 스크롤 제일 밑으로
		}

	})
	.catch(err => console.log(err));
}

// ----------------------------------------------------------------------------------------------------

// 문서 로딩 완료된 후
document.addEventListener("DOMContentLoaded", () => {
  
  // 채팅방 목록에 클릭 이벤트 추가
  roomListAddEvent();
	
	// 보내기 버튼 클릭 시 메시지 보내기
	document.querySelector("#send").addEventListener("click", sendMessage);

	// 채팅 입력 후 엔터 입력 시 메시지 보내기
	document.querySelector("#inputChatting").addEventListener("keyup", e => {
		// 입력한 키가 Enter인 경우
		if(e.key == "Enter"){
			if(!e.shiftKey){ /// shift가 눌러지지 않은 경우
											// == shift + enter 입력 시 제출 X
				sendMessage();
			}
		}
	})

})