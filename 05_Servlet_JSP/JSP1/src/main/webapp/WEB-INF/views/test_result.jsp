<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<title>Test 결과 페이지</title>
	
	<style>
		.title {
			text-decoration: underline;
			font-family: "궁서";
			color: green;
		}

		#input1 { color : red; }
		#input2 { color : blue; }
	</style>
</head>
<body>
	<h1 class="title">Test 결과 페이지 입니다</h1>
	<!-- 
		JSP가 전달 받은 
		HttpServletRequest  객체가 저장된 변수명 : request
		HttpServletResponse 객체가 저장된 변수명 : response
	 -->
	<h1>
		입력 1 : 
		<span id="input1"> <%= request.getParameter("input1") %></span>
	</h1>
	<h1>
		입력 2 : 
		<span id="input2"> <%= request.getParameter("input2") %></span>
	</h1>
	
	<button id="returnBtn">돌아가기</button>
	
	<script>
		document.querySelector("#returnBtn").addEventListener("click", () => {
			alert("돌아갑니다");
			history.back();
			})
	</script>
	
</body>
</html>