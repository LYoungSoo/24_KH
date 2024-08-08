<%-- c : 자주 사용하는 Java 코드 ==> core --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- fn : 컬렉션/문자열 관련 기능 ==> fn --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>JSTL 확인하기</title>
</head>
<body>
  <h3>1. 변수 선언(== 속성 추가) - c:set 태그</h3>
  lib 에 3개 넣고 맨위에 core(자동완성 베포된거)

  <pre>
    - 원하는 scope 객체에 값을 세팅할 수 있는 태그
        객체.setAttribute("key", value); ==> \ < % request.setAttribute("str", "하이"); \% >
    
    [속성]
    1) var : 변수명(==key)

    2) value : 대입할 값(== value)

    3) scope : 범위 지정(page, request, session, application) ==> 기본 값은 page
  </pre>

  <c:set var="num1" value="10" scope="page" />
  <c:set var="num2" value="20" scope="request" />
  <c:set var="num3" value="30" scope="session" />
  <c:set var="num4" value="40" scope="application" />

  <h5>page - num1 : ${pageScope.num1}</h5>
  <h5>request - num2 : ${requestScope.num2}</h5>
  <h5>session - num3 : ${sessionScope.num3}</h5>
  <h5>application - num4 : ${applicationScope.num4}</h5>

  <hr>
  set, get, remove, (add) 정도의 세트가 존재함

  <h3>2. 변수 제거(== 속성 제거) - c:remove 태그</h3>
  <pre>
    -scope 객체에 세팅된 속성을 제거하는 태그
    (== 객체.removeAttribute("key"))

    [속성]
    1) var : 변수명 (== key)
    2) scope : 범위 지정(page, request, session, application) ==> 기본 값 : 전 범위 일괄 제거
  </pre>

  <c:set var="test2" scope="page" value="페이지"/>
  <c:set var="test2" scope="request" value="리퀘스트" />
  <c:set var="test2" scope="session" value="세션"/>
  <c:set var="test2" scope="application" value="어플리케이션"/>

  <table>
    <tr>
      <td>
        <ul>
          <li>page : ${pageScope.test2}</li>
          <li>request : ${requestScope.test2}</li>
          <li>session : ${sessionScope.test2}</li>
          <li>application : ${applicationScope.test2}</li>
        </ul>
      </td>

      <%-- session scope에 존재하는 test2 제거 --%>
      <c:remove var="test2" scope="session" />

      <td>
        <ul>
          <li>page : ${pageScope.test2}</li>
          <li>request : ${requestScope.test2}</li>
          <li>session : ${sessionScope.test2}</li>
          <li>application : ${applicationScope.test2}</li>
        </ul>
      </td>

      <%-- 모든 scope의 test2 제거 --%>
      <c:remove var="test2"/> <%-- scope 미작성 --%>

      <td>
        <ul>
          <li>page : ${pageScope.test2}</li>
          <li>request : ${requestScope.test2}</li>
          <li>session : ${sessionScope.test2}</li>
          <li>application : ${applicationScope.test2}</li>
        </ul>
      </td>
    </tr>
  </table>

  <hr>

  <h3>3. 단일 조건문 - c:if </h3>

  <pre>
    - Java의 if문을 태그 형태로 만든 것

    * 주의사항 : else 구문이 없다!

    [속성]
    1) test : 조건식을 작성하는 속성

    - 작성법 1 : 대입되는 값은 무조건 EL 구문으로 작성!
    - 작성법 2 : 작성된 조건식의 결과는 무조건 True/False
  </pre>

  <c:set var="test3" value="양념 닭갈비" />
  
  <c:if test='${test3 == "숯불 닭갈비"}' >
    <h4>${test3} 맛있겠다</h4>
  </c:if>
  
  <c:if test='${test3 != "숯불 닭갈비"}' >
    <h4>숯불 닭갈비 먹고싶다 ....</h4>
  </c:if>

  <%-- 문자열 비교가 아니면 "" 신경 안써도된다!! --%>
  <c:if test="${100 == 100}" >
    100 입니다
  </c:if>

  <hr>

  <h3>4. 연속된 조건문 - c:choose, c:when, c:otherwise</h3>

  <pre>
    [c:choose 태그]
      - 내부에 조건문 태그를 작성하겠다는 태그
      ==> 내부에 c:when, c:otherwise 태그만 작성 가능!, 다른태그 작성시 오류

      * 주의 사항 *
      바로 한 단계 아래에는 c:when, c:otherwise, 공백, jsp 주석만 작성할 수 있고 나머진 모두 에러 발생!!

    [c:when]
      - if(조건식) / else if(조건식)를 나타내는 태그 ==> 조건식을 작성하는 속성은 [test]

    [c:otherwise]
      - else를 나타내는 태그 ==> 아무 속성도 없음!
  </pre>

  <h3>파라미터(쿼리스트링)으로 전달된 값에 따라 동작하기</h3>
  <c:choose>
    <%-- 파라미터중 age가 없을 경우 --%>
    <%-- <c:when test="${param.age == null}"> null이 해석된 빈칸이 출력되는거지 "" 가 출력되는게 아니라서 --%>

    <%--
      empty 연산자
      - EL에서 사용하는 연산자
        1) null인 경우 true
        2) 연산 대상이 배열/컬렉셔인데 내부에 아무 요소도 없는 경우 true 나머지는 false
    --%>
    <c:when test="${empty param.age}">
      <h4 style="color:red">age 값이 없습니다</h4>
    </c:when>

    <c:when test="${param.age <= 13}">
      <h4>어린이 입니다</h4>
    </c:when>

    <c:when test="${param.age <= 19}">
      <h4>미성년자 입니다</h4>
    </c:when>
  
    <c:otherwise>
      <h4>성인 입니다</h4>
    </c:otherwise>
  </c:choose>

  <hr>

  <h3>5. 향상된 for문 + 추가 기능 ==> c:forEach</h3>
  <pre>
   - 속성
    1) var   : 현재 반복 횟수에 해당하는 변수 (int i)
    2) begin : 반복 시 var 시작 값
    3) end   : 반복이 종료될 var 값
    4) step  : 반복 시 마다 var의 증가 값 (기본값 1)
    
    5) items : 반복 접근한 객체(배열, 컬렉션 객체)
    
    6) varStatus : 현재 반복 상태와 관련된 정보를 제공하는 변수 선언
    
      varStatus="변수명"
      -> c:forEach 구문 내에서 "변수명"을 통해 원하는 값을 얻을 수 있다.
    
      * varStatus에서 제공되는 값
      - current : 현재 반복 횟수(현재 var 값) 
      또는 현재 반복 접근 중인 객체(컬렉션/배열 요소)

      - index : 현재 인덱스값 반환 (0부터 시작)
      
      - count : 현재 몇바퀴째인지 반복 횟수 반환 (1부터 시작)
      
      - first : 첫 번째 반복이면 true, 아니면 false
      
      - last : 마지막 반복이면 true, 아니면 false
  </pre>

  <h3>5-1) 일반 for문 처럼 사용하기</h3>
  <p>1 ~ 6 까지 1씩 증가하는 for문</p>
  <c:forEach var="i" begin="1" end="6" step="1">
    <h${i}> h${i} 태그 입니다 </h${i}>
  </c:forEach>

  <hr>

  <p>6 ~ 1 까지 1씩 감소하는 for문</p>
  <%-- step 속성 값은 양수만 가능!!! --%>

  <c:forEach var="i" begin="1" end="6" step="1">
  <%-- begin, end, step은 변수가 아님!! 못씀!! end - i  이런거 안됨!! --%>
    <h${7-i}> h${7-i} 태그 입니다 </h${7-i}>
  </c:forEach>

  <hr>

  <h3>5-2) 일반 for문 + 컬렉션 List 사용하기</h3>
  <ul>
    <li>
      nameList : ${nameList}
    </li>
    <li>
      <%--
        접두사 fn 이 추가된 EL 구문을 사용해야 한다!!!

        fn(function, 기능) : 문자열, 컬렉션 관련 기능을 제공하는 JSTL-EL

        \${fn:length(컬렉션|배열|문자열)} ==> 컬렉션|배열|문자열의 길이 반환

      --%>
      nameList의 길이(저장된 데이터 개수) : ${fn:length(nameList)}
    </li>

    <%-- c:forEach 태그를 이용해 이름 하나씩 출력 --%>
    <c:forEach var="i" begin="0" end="${fn:length(nameList) -1}" step="1">
      <li>${i+1}) ${nameList[i]}</li>
    </c:forEach>
  </ul>

  <hr>

  <h3>5-3) 향상된 for문 + List 사용하기</h3>
  <%-- varStatus : 현재 반복 상태와 관련된 정보를 제공하는 변수 선언 --%>
  <ul>
    <c:forEach var="name" items="${nameList}" varStatus="vs">
      <c:if test="${vs.first}">
        <li>---------- 시작 ----------</li>
      </c:if>
      <li style="margin-bottom:10px;">
        <ul>
          <li>현재 인덱스 : ${vs.index}</li>
          <li>현재 반복 횟수 : ${vs.count}</li>
          <li>현재 요소 current : ${vs.current}</li>
          <li>현재 요소 n a m e : ${name}</li>
          <li>맨앞 : ${vs.first}</li>
          <li>맨뒤 : ${vs.last}</li>
        </ul>
      </li>

      <c:if test="${vs.last}">
        <li>---------- 종료 ----------</li>
      </c:if>
    </c:forEach>
  </ul>
  


</body>
</html>