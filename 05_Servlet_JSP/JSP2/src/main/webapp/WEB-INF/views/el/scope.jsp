<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Servlet/JSP 범위 별 내장 객체</title>
</head>
<body>
  <h1>Servlet/JSP 범위별 내장 객체</h1>
  <pre>
    Servlet/JSP에는 4종류 범위를 나타낸 내장 객체가 존재한다!
    ==> 각 종류마다 영향을 끼치는 범위가 달라진다

      <h3>1. page (pageContext) : 현재 페이지</h3>
      ==> 현재 JSP에서만 사용 가능한 객체(Servlet X)
      ===> 내가 알고있는 일반적인 변수의 개념. 다른파일 변수는 못쓰는거

      <h3>2. request (요청) == HttpServletRequest</h3>
      ==> 요청 받은 페이지(Servlet/JSP)와
      위임 받은 페이지(Servlet/JSP)에서 유지되는 객체

      <h3>3. session (입회, 접속)</h3>
      - session : 서버에 접속한 클라이언트를
                  나타내거나, 관련 정보를 get/set 할 수 있는 객체
                  (session 객체는 서버에서 관리함)
      
      - [중요!] session은 브라우저 마다 하나씩 생성된다!!!
      (새탭, 새창은 아님)

      - [유지 범위]
      사이트 접속 ~ 브라우저 종료 | 세션 만료

      - session이 유지되는 상태에서는 
      아무 곳에서나 가져다 사용할 수 있다!


    <h3>4. apllication (ServletContext) </h3>
    - 하나의 웹 애플리케이션 마다 1개만 생성되는 객체
    (Server를 키면 1개만 생성되는 객체)

    - application 객체는 어디서든 사용 가능

    - [유지 범위]
    서버 실행 ~ 서버 종료

    <h3>5. 내장 객체의 우선 순위 :  page > request > session > application </h3>
	</pre>

  <hr><hr>

  <h3>범위별 객체에 세팅된 값(속성) 얻어오기</h3>

  <pre>
    - 제출된 파라미터 얻어오는 EL : \${param.key}

    - 범위별 객체에 세팅된 값 얻어오는 EL
      1) \${ㅁㅁㅁScope.key}    ex) \${requestScope.key}  
      2) \${key}    ==> 좁은 범위 객체 부터 탐색하여 일치하는 key가 있으면 얻어옴
  </pre>

  <ul>
    <li>page        scope : </li>
    <li>request     scope : ${requestValue}</li>
    <li>session     scope : ${sessionValue}</li>
    <li>application scope : ${applicationValue}</li>
  </ul>

  <a href="/el/check">객체 범위 확인 페이지로 이동</a>

  <hr>

  <h1>범위별 객체 우선순위 확인</h1>
  <%
    // page scope 객체에 값 세팅
    pageContext.setAttribute("menu","짬짜면(page)");
  %>

  <h3>menu : ${menu}</h3>

  <hr>

  <h3>원하는 scope의 세팅된 값 얻어오기</h3>
  <ul>
    <li>pageScope.menu        : ${pageScope.menu}</li>
    <li>requestScope.menu     : ${requestScope.menu}</li>
    <li>sessionScope.menu     : ${sessionScope.menu}</li>
    <li>applicationScope.menu : ${applicationScope.menu}</li>
  </ul>

</body>
</html>