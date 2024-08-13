<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- "/" 요청 시 index.jsp 화면이 보이지게 되는데 
  여기서 다른 Servlet이 응답할 수 있도록 요청 위임 
  
  "/" 요청이 오면 "/main" 서블릿으로 요청을 위임
  --%>

<jsp:forward page="/main"/>