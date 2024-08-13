<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${todo.title} 상세 조회</title>
  <link rel="stylesheet" href="/resources/css/detail.css">
</head>
<body>

  <%-- 할 일 제목 --%>
  <h1>${todo.title}</h1>

  <%-- 완료 여부 --%>
  <div class="complete">
    완료 여부 : 
    
    <c:if test="${todo.complete}">
      <span class="green">O</span>
    </c:if>

    <c:if test="${not todo.complete}">
      <span class="red">X</span>
    </c:if>
  </div>

  <div>
    작성일 : ${todo.regDate}
  </div>

  <%-- 상세 내용 --%>
  <div class="content">${todo.detail}</div>

  <div class="btn-container">
    <div>
      <button id="goToList">목록으로</button>
    </div>
    
    <div>
      <button id="completeBtn">완료 여부 변경</button>
      <button id="updateBtn">수정</button>
      <button id="deleteBtn">삭제</button>
    </div>
  </div>

  <%-- session에 message가 있다면 --%>
  <c:if test="${not empty sessionScope.message}">
    <script>
      // JSP 해석 순서 : EL/JSTL  >  html,css,js
      alert("${message}");
    </script>

    <c:remove var="message" scope="session" />
  </c:if>

  <script src="/resources/js/detail.js"></script>
</body>
</html>