<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>redirect 결과 페이지</title>
</head>
<body>
  <h3>
    /fr/redirect 요청 시 재요청되어 보여지는 페이지!
  </h3>

  <h3 id="print"></h3>

  <hr>

  <h3>request scope로 전달된 값이 존재하나 확인</h3>
  <ul>
    <li>redirectInput1 : ${param.redirectInput1}</li>
    <li>redirectInput2 : ${param.redirectInput2}</li>
    <li>str : ${str2}</li>
  </ul>

  <h3>session scope로 전달된 값 : ${sessionNum}</h3>

  <script>
    // 아이디가 print인 요소의 내부 내용으로 현재 페이지 주소를 대입
    document.querySelector("#print").innerText = location.pathname;
  </script>

</body>
</html>