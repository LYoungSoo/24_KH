<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
  "/" 메인 페이지 요청 시 index.jsp 가 매핑되는데
  바로 "/main" 이라는 요청을 처리하는 Servlet 으로 요청 위임
--%>
<jsp:forward page="/main"/>