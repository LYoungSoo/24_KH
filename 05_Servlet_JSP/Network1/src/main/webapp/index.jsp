<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>네트워크 프로그래밍_1</title>
</head>

<body>
  <h1>messge : ${message}</h1>

  <hr>
  
  <h1>회원가입</h1>
  <form action="/join" method="post">
    아이디 : <input type="text" name="memberId" required><br>
    비밀번호 : <input type="password" name="memberPw" required><br>
    이름 : <input type="text" name="memberName" required><br>
    <button>가입하기</button>
  </form>
  
</body>
</html>