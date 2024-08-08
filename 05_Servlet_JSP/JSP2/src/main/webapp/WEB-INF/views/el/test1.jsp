<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>전달 받은 파라미터 출력하기</title>
</head>
<body>

  <h2>EL의 특징</h2>
  <pre>
    1. null을 빈칸("")으로 처리 + NullPointerException을 발생시키지 않음!! (도랐다 이건)

    2. get 이라는 단어를 거의 사용하지 않는다!
      - EL은 표현(출력) 언어
      ==> 값을 대입하고, 조건/반복하는 구문을 작성 X, 오로지 출력!!
      ===> 출력하려면 값을 얻어와야 되는게 당연한거라 get 단어 사용 X)
  </pre>

  <hr>

  <h3>전달 받은 파라미터 출력하기</h3>
  <pre>
    - \$ {param.key} : key 일치하는 파라미터 얻어오기
    - \$ {paramValues.key} : key 일치하는 파라미터 모두 얻어오기
  </pre>
  
  <h4>존재하는 파라미터 얻어오기</h4>
  <ul>
    <li>
      JSP Expression(표현식)으로 str 파라미터 얻어오기 :
      <%= request.getParameter("str") %>
    </li>
    <li>
      EL 구문으로 str 파라미터 얻어오기 : ${param.str}
    </li>
  </ul>

  <h4>존재하지 않는 파라미터 얻어오기</h4>
  <ul>
    <li>
    JSP 표현식으로 temp 파라미터 얻어오기 : 
    <%= request.getParameter("temp") %>
    </li>
    <li>
    
    </li>
  </ul>

  <hr>

  <h3>EL은 자료형 변환(parsing)을 자동 수행 해준다</h3>

  <ul>
    <%-- EL은 Java로 변환 되지만 문자열 비교는 == (비교 연산자) 사용 가능 --%>
    <li> ${param.str == "abc"} </li>
    <%-- HTML에서 얻어온 데이터는 모두 String
    하지만 EL에서 연산되는 자료형이 다를 경우 자동으로 자료형 변환(parsing)을 진행해준다!! --%>
    <%-- Integer.parseInt(request.getParameter()) 으로 작성하지 않아도 됨--%>
    <li> ${param.intNum == 100} </li>
    <li> ${param.intNum == 3.14} </li>
  </ul>

  <hr>

  <h3>같은 key 값을 지닌 파라미터 얻어오기</h3>
  <ul>
    <%-- check가 여러 개 전달된 경우 첫 번째 값 출력 --%>
    <li>
      param.check : ${param.check}
      <%-- 여러개 체크 되었을 때 제일 앞에거만 나옴 --%>
    </li>

    <%-- check라는 key를 가진 모든 값을 모아 String[]로 반환 --%>
    <li>
      paramValues.check : ${paramValues.check}
    </li>
    <li>
      paramValues.check[0] : ${paramValues.check[0]}
    </li>
    <li>
      paramValues.check[1] : ${paramValues.check[1]}
    </li>
    <li>
      paramValues.check[2] : ${paramValues.check[2]}
    </li>
  </ul>

</body>
</html>