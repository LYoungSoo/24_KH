<%-- prefix : 접두사 (앞에 붙는 단어/말)
	만약 prefix="c"    ->  <c:if>
	만약 prefix="core" ->  <core:if>
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>EL / JSTL 사용법</title>
</head>
<body>
  <h1>EL (Expression language) : 표현 언어</h1>

  <pre>
    <%-- <%=  %> --%>
    - JSP에서 표현식을 간단하고 효율적으로 작성할 수 있도록 고안된 언어(JSP 내부에 기본 내장되어 있음!)

    - Java 값을 HTML 형태로 쉽게 출력할 수 있다!!

    <%-- \ 쓰면 문자로 보이고, 안쓰면 코드로 인식됨. 즉 ${key}로 사용하여야 함 &nbsp같은거 --%>
    - 기본 작성법 : \${key}   (백슬래시('\')는 제거하고 사용)
  </pre>

  <h3>전달 받은 파라미터를 출력하는 방법</h3>

  <p style = "color:red;">
    주소 뒤에 쿼리스트링(?name=홍길동&age=20) 직접 작성해보면서 테스트
  </p>

  <h4>1. JSP 표현식</h4>
  <div>
    name : <%= request.getParameter("name") %>
    <br>
    age : <%= request.getParameter("age") %>
  </div>

  <h4>2. EL</h4>
  <div>
    <%-- EL에서 파라미터를 얻어오는 방법 : ${param.key값} --%>
    name : ${param.name}
    <br>
    age : ${param.age}
  </div>

  <hr><hr>

  <h1>JSTL (Jsp Standard Tag Library)</h1>
  <pre>
    JSP에서 자주 사용하는 Java 코드 (if, for, 변수 선언, String 관련 메서드 ....)

    <%-- <% Java코드 %> --%>
    <%-- 주소 뒤에. 그러니까  localhost: ~ el_jstl 뒤에 ?name=홍길동&age=20 이런식으로 --%>
    <%-- http://localhost/el_jstl?name=홍길동&age=100 --%>
    스크립틀릿이 아닌 HTML 태그 모양으로 작성할 수 있도록 태그를 제공하는 라이브러리

    [라이브러리 추가 방법]

    1. 필요한 라이브러리 (.jar) 파일을 다운 받기
    2. 프로젝트 webapp/WEB_INF/lib 폴더에 다운 받은 라이브러리 추가(복사, 붙여넣기)
    3. JSTL을 사용할 JSP 파일 제일 위에 taglib 추가 구문 작성
  </pre>

  <h3>JSTL c:if문 사용해보기</h3>

  <%
    int age = Integer.parseInt(request.getParameter("age"));
    if(age > 20) {
  %>
  <h3>성인 입니다 !!@</h3>
  <%
    }
  %>

  
  <c:if test="${param.age > 20}">
    <h3>성인 입니다</h3>
  </c:if>

  <c:if test="${param.age <= 20}">
    <h3>성인이 아닙니다</h3>
  </c:if>


</body>
</html>