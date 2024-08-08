<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>책 목록 조회</title>

  <%-- webapp 폴더를 기준(/)으로 css 파일 경로 작성 --%>
  <link rel="stylesheet" href="/resources/css/book.css">

  <%-- GET http://localhost/favicon.ico 404 (Not Found) 오류 삭제 --%>
  <link rel="shortcut icon" href="#">
</head>
<body>
  <h1>책 목록 조회</h1>

  <hr>

  <h3>전체 책 수량 : ${fn:length(bookList)}권</h3>

  <hr>

  <table border="1">
    <thead>
      <tr>
        <th>번호</th>
        <th>제목</th>
        <th>저자</th>
        <th>가격(원)</th>
      </tr>
    </thead>

    <tbody>
      <c:forEach var="book" items="${bookList}" varStatus="vs">
        <tr>
          <td>${vs.count}</td>
          <td>${book.title}</td>
          <td>${book.writer}</td>
          <td>${book.price}</td>
        </tr>

        <%-- 3배수 번째 반복 시 --%>
        <c:if test="${vs.count % 3 == 0}">
          <tr><td colspan="4" class="blank">&nbsp;</td></tr>
        </c:if>
      </c:forEach>
    </tbody>
  </table>

  <button id="btn">js 테스트</button>

<%-- webapp 폴더를 기준(/)으로 js 파일 경로 작성 --%>
<script src="/resources/js/book.js"></script>
</body>
</html>