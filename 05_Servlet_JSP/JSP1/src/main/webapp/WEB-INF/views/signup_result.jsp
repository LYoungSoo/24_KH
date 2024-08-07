<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원 가입 결과</title>
</head>
<body>
    <h1>회원 가입 결과 페이지 입니다</h1>
    <h3>입력되어 전달된 값들</h3>
    <ul>
        <li>ID : <%= request.getParameter("inputId") %> </li>
        <li>PW : <%= request.getParameter("inputPw") %> </li>
        <li>확인 : <%= request.getParameter("inputPwCheck") %> </li>
        <li>이름 : <%= request.getParameter("inputName") %> </li>
        <li>나이 : <%= request.getParameter("inputAge") %> </li>
    </ul>

    <%-- 
        JSP 주석 ==> 자바 주석으로 변환 ==> 랜더링된 HTML에서 보여지지 않는 주석
        언제사용? - JSP에 작성된 Java 코드를 주석처리 할 때 사용
    --%>

    <%-- 
        [JSP에서 Java 코드 작성 시 사용하는 태그]

        <%  %>  : Script + let ==> Scriptlet(스크립틀릿)
                 - JSP에서 자바 코드를 작성할 수 있게 하는 태그
                 - 변수 선언, if, for 등 모두 작성 가능
                 - JSP ==> Servlet 변환 시 작성된 형태 그대로 Servlet 중간에 작성됨

        <%= %>  : Expression(표현식)
                 - JSP에 자바 코드를 출력할 수 있게 하는 태그
                 - JSP ==> Servlet 변환 시 out.write(); 내부에 코드가 작성되어 HTML 형태로 출력됨

        <%@ %>  : 페이지 지시자
                 - 페이지 관련된 설정, import 등을 작성하는 태그
                 - 직접 다루는 상황은 많이 안나옴
                   ex) <%@ import="java.util.Scanner;" %>
    --%>

    <%
        String pw = request.getParameter("inputPw");
        String check = request.getParameter("inputPwCheck");

        // 비밀번호가 일치하는 경우
        if(pw.equals(check)) {
    %>
        <h1 style="color:green">가입 성공</h1>

    <%  } else {    %>

        <h1 style="color:red">비밀번호 불일치</h1>

    <%
        }
    %>


</body>
</html>