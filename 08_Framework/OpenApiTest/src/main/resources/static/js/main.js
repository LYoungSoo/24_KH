/* 개인 API 인증키 */

const serviceKey = "XXbHmMVPnofdOHgdIfy3pphBo6HMg1PZbPbwTLXXAX5NTO2hUsFS3i5a2PHufmnixHRnsUNyF9KatS2ZZ6WqfQ%3D%3D";

const getAirPollution = (cityName) => {

//    const requestUrl = '';
// @@   let requestUrl = ''; 
    let requestUrl = 'http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty';

    requestUrl += `?serviceKey=${serviceKey}`;

    requestUrl += `&returnType=json`;
    requestUrl += `&numOfRows=1`;
    requestUrl += `&pageNo=1`;
    requestUrl += `&ver=1.0`;
	requestUrl += `&sidoName=${cityName}`;		// 추가?
	


    fetch(requestUrl)
    // .then(resp => 
    .then(resp => {
        if (resp.ok) return resp.json();
        // throws new Error("api 요청 실패")
        throw new Error("api 요청 실패")
    })
	.then(result => {
	        console.log(result);
			
	//=====================
			// 필요한 데이터만 추출
			const item = result.response.body.items[0];

			console.log(item);

			console.log(`미세먼지 농도 : ${item['pm10Value']} ㎍/㎥`)
			console.log(`미세먼지 등급 : ${item['pm10Grade']} 급`)

			console.log(`초미세먼지 농도 : ${item['pm25Value']} ㎍/㎥`)
			console.log(`초미세먼지 등급 : ${item['pm25Grade']} 급`)


			// 화면에 출력
			const pm10Grade = document.querySelector("#pm10Grade");
			const pm10GradeText = document.querySelector("#pm10GradeText");
			const pm10Value = document.querySelector("#pm10Value");

			const pm25Grade = document.querySelector("#pm25Grade");
			const pm25GradeText = document.querySelector("#pm25GradeText");
			const pm25Value = document.querySelector("#pm25Value");

			// 이모지/등급 배열
			const gradeEmoji = ['😄', '🙂', '😷', '🤢'];
			const gradeText = ['좋음', '보통', '나쁨', '매우나쁨'];

			// 데이터 등급은 1,2,3,4 등급이고, 배열은 0,1,2,3 이므로
			pm10Grade.innerText = gradeEmoji[item.pm10Grade -1];  
			pm10GradeText.innerText = gradeText[item.pm10Grade -1];
			pm10Value.innerText = `미세먼지 농도 : ${item['pm10Value']} ㎍/㎥`;

			pm25Grade.innerText = gradeEmoji[item.pm25Grade -1];
			pm25GradeText.innerText = gradeText[item.pm25Grade -1];
			pm25Value.innerText = `초미세먼지 농도 : ${item['pm25Value']} ㎍/㎥`;
	//=====================
			
	})
    .catch(err => console.error(err));
}

getAirPollution('서울');