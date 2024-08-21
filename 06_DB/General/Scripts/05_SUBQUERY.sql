/*
    * SUBQUERY (서브쿼리)
    - 하나의 SQL문 안에 포함된 또다른 SQL문
    - 메인쿼리(기존쿼리)를 위해 보조 역할을 하는 쿼리문
    -- SELECT, FROM, WHERE, HAVGIN 절에서 사용가능

*/  

-- 서브쿼리 예시 1.
-- 부서코드가 노옹철사원과 같은 소속의 직원의 
-- 이름, 부서코드 조회하기

-- 1) 사원명이 노옹철인 사람의 부서코드 조회
SELECT DEPT_CODE
FROM EMPLOYEE
WHERE EMP_NAME = '노옹철'
;

-- 2) 부서코드가 D9인 직원을 조회
SELECT EMP_NAME, DEPT_CODE
FROM EMPLOYEE
WHERE DEPT_CODE = 'D9'
;

-- 3) 부서코드가 노옹철사원과 같은 소속의 직원 명단 조회   
--> 위의 2개의 단계를 하나의 쿼리로!!! --> 1) 쿼리문을 서브쿼리로!!
SELECT EMP_NAME, DEPT_CODE
FROM EMPLOYEE
WHERE DEPT_CODE = (
	SELECT DEPT_CODE
	FROM EMPLOYEE
	WHERE EMP_NAME = '노옹철'
)
;
                   
                   
                   
-- 서브쿼리 예시 2.
-- 전 직원의 평균 급여보다 많은 급여를 받고 있는 직원의 
-- 사번, 이름, 직급코드, 급여 조회

-- 1) 전 직원의 평균 급여 조회하기
SELECT FLOOR(AVG(SALARY))
FROM EMPLOYEE
;


-- 2) 직원들중 급여가 4091140원 이상인 사원들의 사번, 이름, 직급코드, 급여 조회
SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY
FROM EMPLOYEE
WHERE 4091140 < SALARY
;
-- 3) 전 직원의 평균 급여보다 많은 급여를 받고 있는 직원 조회
--> 위의 2단계를 하나의 쿼리로 가능하다!! --> 1) 쿼리문을 서브쿼리로!!
SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY
FROM EMPLOYEE
WHERE (SELECT FLOOR(AVG(SALARY))
				FROM EMPLOYEE) < SALARY
;
                 
/************************************************************/
-- 기본적으로 " 서브 쿼리"를 먼저 해석!!!!
	--> 서브쿼리 결과를 이용해서 "메인 쿼리" 해석!!!

-- 단, 상호연관 서브쿼리는 순서가 반대!!! (메인 -> 서브)
/************************************************************/

----------------------------------------------------------------------------------------------------

/*  서브쿼리 유형

    - 단일행 (단일열) 서브쿼리 : 서브쿼리의 조회 결과 값의 개수가 1개일 때 
    
    - 다중행 (단일열) 서브쿼리 : 서브쿼리의 조회 결과 값의 개수가 여러개일 때
    
    - 다중열 서브쿼리 : 서브쿼리의 SELECT 절에 자열된 항목수가 여러개 일 때
    
    - 다중행 다중열 서브쿼리 : 조회 결과 행 수와 열 수가 여러개일 때 
    
    - 상관 서브쿼리 : 서브쿼리가 만든 결과 값을 메인 쿼리가 비교 연산할 때 
                     메인 쿼리 테이블의 값이 변경되면 서브쿼리의 결과값도 바뀌는 서브쿼리
                     
    - 스칼라 서브쿼리 : 상관 쿼리이면서 결과 값이 하나인 서브쿼리
    
   * 서브쿼리 유형에 따라 서브쿼리 앞에 붙은 연산자가 다름
    
*/


-- 1. 단일행 서브쿼리 (SINGLE ROW SUBQUERY)
--    서브쿼리의 조회 결과 값의 개수가 1개인 서브쿼리
--    WHERE 절에서, 단일행 서브쿼리 앞에는 비교 연산자 사용
--    <, >, <=, >=, =, !=/^=/<>


-- 전 직원의 급여 평균보다 많은 급여를 받는 직원의 
-- 이름, 직급명, 부서명, 급여를 직급 순으로 정렬하여 조회
SELECT EMP_NAME, JOB_NAME, DEPT_TITLE, SALARY
FROM EMPLOYEE
JOIN JOB USING(JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
WHERE SALARY > (SELECT AVG(SALARY) FROM EMPLOYEE)
ORDER BY JOB_CODE ASC
;


-- 가장 적은 급여를 받는 직원의
-- 사번, 이름, 직급명, 부서코드, 급여, 입사일을 조회
SELECT EMP_ID, EMP_NAME, DEPT_CODE, SALARY, HIRE_DATE
FROM EMPLOYEE
WHERE SALARY = (
	SELECT MIN(SALARY)
	FROM EMPLOYEE
)
;
                 
-- 노옹철 사원의 급여보다 많이 받는 직원의 
-- 사번, 이름, 부서명, 직급명, 급여를 조회
SELECT EMP_ID, EMP_NAME, DEPT_TITLE, JOB_NAME, SALARY
FROM EMPLOYEE
JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)
JOIN JOB USING(JOB_CODE)
WHERE SALARY > 
	(SELECT SALARY
	FROM EMPLOYEE
	WHERE EMP_NAME = '노옹철')
;
       
-- 부서별(부서가 없는 사람 포함) 급여의 합계 중 가장 큰 부서의
-- 부서명, 급여 합계를 조회 

-- 1) 부서별 급여 합 중 가장 큰값 조회
SELECT MAX(SUM(SALARY))
FROM EMPLOYEE
GROUP BY DEPT_CODE
;


-- 2) 부서별 급여합이 21760000원 부서의 부서명과 급여 합 조회
SELECT DEPT_TITLE, SUM(SALARY)
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)
GROUP BY DEPT_TITLE
HAVING SUM(SALARY) = 21760000
;
/* GROUP BY 에 작성된 컬럼명만 SELECT 절에 작성할 수 있다 (해석순서를 생각해볼것) */


-- 3) >> 위의 두 서브쿼리 합쳐 부서별 급여 합이 큰 부서의 부서명, 급여 합 조회
SELECT DEPT_TITLE, SUM(SALARY)
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)
GROUP BY DEPT_TITLE
HAVING SUM(SALARY) = (
	SELECT MAX(SUM(SALARY))
	FROM EMPLOYEE
	GROUP BY DEPT_CODE)
;

-- 부서별 인원 수가 3명 이상인 부서의
-- 부서명, 인원 수 조회
SELECT DEPT_TITLE, COUNT(*)
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)
GROUP BY DEPT_TITLE
HAVING COUNT(*) >= 3
;

-- 부서별 인원 수가 가장 적은 부서의
-- 부서명, 인원 수 조회
SELECT NVL(DEPT_TITLE, '없음') 부서명 , COUNT(*)
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (DEPT_ID = DEPT_CODE)
GROUP BY DEPT_TITLE
HAVING COUNT(*) = (
	SELECT MIN(COUNT(*))
	FROM EMPLOYEE
	GROUP BY DEPT_CODE)
;

/***** 서브쿼리에서 사용한 별칭을 메인 쿼리에서 사용하기 *****/
-- 인라인뷰 : FROM절에 사용된 서브쿼리
		--> 서브쿼리 결과가 테이블 처럼 인식

SELECT 이름, 급여
FROM (SELECT EMP_NAME 이름, SALARY 급여
			FROM EMPLOYEE)
WHERE 4000000 <= 급여
ORDER BY 급여 ASC
;
		--  메인쿼리 해석 1순위인 FROM 절에 작성된
		--> 서브쿼리 결과 컬럼명이 "급여" 이기 때문에
		--  메인쿼리 해석 2순위인 WHERE,
		--  메인쿼리 해석 3순위인 SELECT 절에서도
		--  똑같이 "급여" 라고 컬럼명을 작성해야 한다!

----------------------------------------------------------------------------------------------------

-- 2. 다중행 서브쿼리 (MULTI ROW SUBQUERY)
--    서브쿼리의 조회 결과 값의 개수가 여러행일 때 

/*
    >> 다중행 서브쿼리 앞에는 일반 비교연산자 사용 x
    
    - IN / NOT IN : 여러 개의 결과값 중에서 한 개라도 일치하는 값이 있다면
                    혹은 없다면 이라는 의미(가장 많이 사용!)
    - > ANY, < ANY : 여러개의 결과값 중에서 한개라도 큰 / 작은 경우
                     가장 작은 값보다 큰가? / 가장 큰 값 보다 작은가?
    - > ALL, < ALL : 여러개의 결과값의 모든 값보다 큰 / 작은 경우
                     가장 큰 값 보다 큰가? / 가장 작은 값 보다 작은가?
    - EXISTS / NOT EXISTS : 값이 존재하는가? / 존재하지 않는가?
    
*/

-- 부서별 최고 급여를 받는 직원의 
-- 이름, 직급코드, 부서코드, 급여를 부서 순으로 정렬하여 조회

-- 1) 부서별 최고 급여만 조회
SELECT MAX(SALARY)
FROM EMPLOYEE
GROUP BY DEPT_CODE;

-- 2) 부서별 최고 급여를 받는 직원 조회하기
SELECT EMP_NAME, JOB_CODE, DEPT_CODE, SALARY
FROM EMPLOYEE
WHERE SALARY IN (
	SELECT MAX(SALARY)
	FROM EMPLOYEE
	GROUP BY DEPT_CODE
)
;

-- 사수에 해당하는 직원에 대해 조회 
--  사번, 이름, 부서명, 직급명, 구분(사수 / 직원)

-- 1) 사수에 해당하는 사원 번호 조회
--> MANAGER_ID 컬럼에 작성되어있는 사번을 가진 사원 == 사수
-- DISTINCT : 컬럼값 중복 제거
SELECT DISTINCT MANAGER_ID
FROM EMPLOYEE
WHERE MANAGER_ID IS NOT NULL
;

-- 2) 직원의 사번, 이름, 부서명, 직급명 조회
SELECT EMP_ID 사번, EMP_NAME 이름, NVL(DEPT_TITLE, '없음') 부서명, JOB_NAME 직급명
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
;

-- 3) 사수에 해당하는 직원에 대한 정보 추출 조회 (이때, 구분은 '사수'로)
SELECT EMP_ID 사번, EMP_NAME 이름, NVL(DEPT_TITLE, '없음') 부서명, JOB_NAME 직급명, '사수' AS 구분
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
WHERE EMP_ID IN (
	SELECT DISTINCT MANAGER_ID
	FROM EMPLOYEE
	WHERE MANAGER_ID IS NOT NULL
)
;

-- 4) 일반 직원에 해당하는 사원들 정보 조회 (이때, 구분은 '사원'으로)
SELECT EMP_ID 사번, EMP_NAME 이름, NVL(DEPT_TITLE, '없음') 부서명, JOB_NAME 직급명, '사원' AS 구분
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
WHERE EMP_ID NOT IN (
	SELECT DISTINCT MANAGER_ID
	FROM EMPLOYEE
	WHERE MANAGER_ID IS NOT NULL
)
;

-- 5) 3, 4의 조회 결과를 하나로 합침 -> SELECT절 SUBQUERY
-- * SELECT 절에도 서브쿼리 사용할 수 있음

-- 방법 1) UNION을 이용하는 방법
			--> UNION : 두 SELECT의 결과(RESULT SET)을 하나로 합침(합집합)
	
SELECT EMP_ID 사번, EMP_NAME 이름, NVL(DEPT_TITLE, '없음') 부서명, JOB_NAME 직급명, '사수' AS 구분
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
WHERE EMP_ID IN (
	SELECT DISTINCT MANAGER_ID
	FROM EMPLOYEE
	WHERE MANAGER_ID IS NOT NULL
)
UNION

SELECT EMP_ID 사번, EMP_NAME 이름, NVL(DEPT_TITLE, '없음') 부서명, JOB_NAME 직급명, '사원' AS 구분
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
WHERE EMP_ID NOT IN (
	SELECT DISTINCT MANAGER_ID
	FROM EMPLOYEE
	WHERE MANAGER_ID IS NOT NULL
)
;

-- 방법 2) SELECT절 SUBQUERY
-- * SELECT 절에도 서브쿼리 사용할 수 있음
-- CASE, WHEN, THEN, ELSE, END --> 선택 함수
SELECT EMP_ID 사번, EMP_NAME 이름, NVL(DEPT_TITLE, '없음') 부서명, JOB_NAME 직급명,
	CASE
		WHEN EMP_ID IN (
			SELECT DISTINCT MANAGER_ID
			FROM EMPLOYEE
			WHERE MANAGER_ID IS NOT NULL)
		THEN '사수'
		ELSE '사원'
	END AS 구분
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
ORDER BY EMP_ID ASC
;


-- 대리 직급의 직원들 중에서 과장 직급의 최소 급여보다 많이 받는 직원의
-- 사번, 이름, 직급, 급여를 조회하세요
-- 단, > ANY 혹은 < ANY 연산자를 사용하세요

-- > ANY, < ANY : 여러개의 결과값 중에서 하나라도 큰 / 작은 경우
--                     가장 작은 값보다 큰가? / 가장 큰 값 보다 작은가?

-- 1) 직급이 대리인 직원들의 사번, 이름, 직급명, 급여 조회
SELECT EMP_ID, EMP_NAME, JOB_NAME, SALARY
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
WHERE JOB_NAME = '대리'
;

-- 2) 직급이 과장인 직원들 급여 조회
SELECT EMP_ID, EMP_NAME, JOB_NAME, SALARY
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
WHERE JOB_NAME = '과장'
;

-- 3) 대리 직급의 직원들 중에서 과장 직급의 최소 급여보다 많이 받는 직원
-- 3-1) MIN을 이용하여 단일행 서브쿼리를 만듦.
SELECT EMP_ID, EMP_NAME, JOB_NAME, SALARY
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
WHERE JOB_NAME = '대리'
AND SALARY > (
	SELECT MIN(SALARY)
	FROM EMPLOYEE
	JOIN JOB USING (JOB_CODE)
	WHERE JOB_NAME = '과장'
)
;

-- 3-2) ANY를 이용하여 과장 중 가장 급여가 적은 직원 초과하는 대리를 조회
SELECT EMP_ID, EMP_NAME, JOB_NAME, SALARY
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
WHERE JOB_NAME = '대리'
AND SALARY > ANY (
	SELECT (SALARY)
	FROM EMPLOYEE
	JOIN JOB USING (JOB_CODE)
	WHERE JOB_NAME = '과장'
)
;


-- 차장 직급의 급여의 가장 큰 값보다 많이 받는 과장 직급의 직원
-- 사번, 이름, 직급, 급여를 조회하세요
-- 단, > ALL 혹은 < ALL 연산자를 사용하세요

-- > ALL, < ALL : 여러개의 결과값의 모든 값보다 큰 / 작은 경우
--                     가장 큰 값 보다 크냐? / 가장 작은 값 보다 작냐?

-- 1) MAX 이용
SELECT EMP_ID, EMP_NAME, JOB_NAME, SALARY
FROM EMPLOYEE E
JOIN JOB J ON (E.JOB_CODE = J.JOB_CODE)
WHERE JOB_NAME = '과장'
	AND SALARY > (
		SELECT MAX(SALARY)
		FROM EMPLOYEE
		JOIN JOB USING (JOB_CODE)
		WHERE JOB_NAME = '차장'
	)
;

-- 2) ALL 이용
SELECT EMP_ID, EMP_NAME, JOB_NAME, SALARY
FROM EMPLOYEE E
JOIN JOB J ON (E.JOB_CODE = J.JOB_CODE)
WHERE JOB_NAME = '과장'
	AND SALARY > ALL (
		SELECT SALARY
		FROM EMPLOYEE
		JOIN JOB USING (JOB_CODE)
		WHERE JOB_NAME = '차장'
	)
;

-- 서브쿼리 중첩 사용(응용편!)


-- LOCATION 테이블에서 NATIONAL_CODE가 KO인 경우의 LOCAL_CODE와
-- DEPARTMENT 테이블의 LOCATION_ID와 동일한 DEPT_ID가 
-- EMPLOYEE테이블의 DEPT_CODE와 동일한 사원을 구하시오.

-- 1) LOCATION 테이블을 통해 NATIONAL_CODE가 KO인 LOCAL_CODE 조회
SELECT LOCAL_CODE
FROM LOCATION
WHERE NATIONAL_CODE = 'KO'
;
--> 'L1' (단일행 서브쿼리)

-- 2)DEPARTMENT 테이블에서 위의 결과와 동일한 LOCATION_ID를 가지고 있는 DEPT_ID를 조회
SELECT DEPT_ID
FROM DEPARTMENT
WHERE LOCATION_ID = (
	SELECT LOCAL_CODE
	FROM LOCATION
	WHERE NATIONAL_CODE = 'KO'
)
;
--> D1, D2, D3, D4, D9

-- 3) 최종적으로 EMPLOYEE 테이블에서 위의 결과들과 동일한 DEPT_CODE를 가지는 사원을 조회
SELECT EMP_NAME, DEPT_CODE		-- 메인쿼리
FROM EMPLOYEE
WHERE DEPT_CODE IN (
	SELECT DEPT_ID		-- 다중행 서브쿼리
	FROM DEPARTMENT
	WHERE LOCATION_ID = (
		SELECT LOCAL_CODE		-- 단일행 서브쿼리
		FROM LOCATION
		WHERE NATIONAL_CODE = 'KO'
	)
)
;

----------------------------------------------------------------------------------------------------

-- 3. 다중열 서브쿼리 (단일행 = 결과값은 한 행)
--    서브쿼리 SELECT 절에 나열된 컬럼 수가 여러개 일 때

-- 퇴사한 여직원과 같은 부서, 같은 직급에 해당하는
-- 사원의 이름, 직급, 부서, 입사일을 조회        

-- 1) 퇴사한 여직원 조회
SELECT EMP_NAME, JOB_CODE, DEPT_CODE, HIRE_DATE
FROM EMPLOYEE
WHERE ENT_YN = 'Y'	-- 퇴사 여부 = 'Y' == 퇴사한 직원
	AND SUBSTR(EMP_NO, 8, 1) IN ('2','4')
;
-- 이태림 JOB_CODE = 'J6' , DEPT_CODE = 'D8'

-- 2) 퇴사한 여직원과 같은 부서, 같은 직급 (다중 열 서브쿼리)

-- 방법 1 : 단일행 서브쿼리 2개 사용하기
SELECT EMP_NAME, JOB_CODE, DEPT_CODE, HIRE_DATE
FROM EMPLOYEE
WHERE DEPT_CODE = (
	SELECT DEPT_CODE
	FROM EMPLOYEE
	WHERE ENT_YN = 'Y'
		AND SUBSTR(EMP_NO, 8, 1) IN ('2','4')
)
AND JOB_CODE = (
	SELECT JOB_CODE
	FROM EMPLOYEE
	WHERE ENT_YN = 'Y'
		AND SUBSTR(EMP_NO, 8, 1) IN ('2','4')
)
;

-- 방법 2 : 다중열 서브쿼리 이용
SELECT EMP_NAME, JOB_CODE, DEPT_CODE, HIRE_DATE
FROM EMPLOYEE
WHERE (DEPT_CODE, JOB_CODE) = (
--WHERE (JOB_CODE, DEPT_CODE) = ( --이거쓰면 값 안나옴. 순서가 중요함
	SELECT DEPT_CODE, JOB_CODE
	FROM EMPLOYEE
	WHERE ENT_YN = 'Y'
		AND SUBSTR(EMP_NO, 8, 1) IN ('2','4')
)
;

-------------------------- 연습문제 -------------------------------
-- 1. 노옹철 사원과 같은 부서, 같은 직급인 사원을 조회하시오. (단, 노옹철 사원은 제외)
--    사번, 이름, 부서코드, 직급코드, 부서명, 직급명
SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, DEPT_TITLE, JOB_NAME
FROM EMPLOYEE
JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
JOIN JOB USING (JOB_CODE)
WHERE (DEPT_CODE, JOB_CODE) = (
	SELECT DEPT_CODE, JOB_CODE
	FROM EMPLOYEE
	WHERE EMP_NAME = '노옹철'
) AND EMP_NAME != '노옹철'
;

-- 2. 2010년도에 입사한 사원의 부서와 직급이 같은 사원을 조회하시오
--    사번, 이름, 부서코드, 직급코드, 고용일
SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, HIRE_DATE
FROM EMPLOYEE
WHERE (DEPT_CODE, JOB_CODE) = (
	SELECT DEPT_CODE, JOB_CODE
	FROM EMPLOYEE
	WHERE HIRE_DATE BETWEEN '2010-01-01' AND '2010-12-31'
--	WHERE EXTRACT(YEAR FROM HIRE_DATE) = 2010
)
;

-- 3. 87년생 여자 사원과 동일한 부서이면서 동일한 사수를 가지고 있는 사원을 조회하시오
--    사번, 이름, 부서코드, 사수번호, 주민번호, 고용일     
SELECT EMP_ID, EMP_NAME, DEPT_CODE, MANAGER_ID, EMP_NO, HIRE_DATE
FROM EMPLOYEE
WHERE (DEPT_CODE, MANAGER_ID) = (
	SELECT DEPT_CODE, MANAGER_ID
	FROM EMPLOYEE
	WHERE EMP_NO LIKE '87%'
	  AND SUBSTR(EMP_NO,8,1) = '2'
)
;

----------------------------------------------------------------------------------------------------

-- 4. 다중행 다중열 서브쿼리
--    서브쿼리 조회 결과 행 수와 열 수가 여러개 일 때

-- 본인 직급의 평균 급여를 받고 있는 직원의
-- 사번, 이름, 직급, 급여를 조회하세요
-- 단, 급여와 급여 평균은 만원단위로 계산하세요 TRUNC(컬럼명, -4)    

-- 1) 급여를 200, 600만 받는 직원 (200만, 600만이 평균급여라 생각 할 경우)


-- 2) 직급별 평균 급여


-- 3) 본인 직급의 평균 급여를 받고 있는 직원

/*
 * 
 * 
-- 1) 급여를 300만, 700만 받는 직원 (300만, 700만이 평균급여라 생각 할 경우)
SELECT EMP_ID, EMP_NAME, JOB_CODE, SALARY
FROM EMPLOYEE
WHERE SALARY IN (3000000, 7000000);


-- 2) 직급별 평균 급여
SELECT JOB_CODE, TRUNC( AVG(SALARY),  -4)
FROM EMPLOYEE
GROUP BY JOB_CODE;


-- 3) 본인 직급의 평균 급여를 받고 있는 직원
SELECT EMP_ID, EMP_NAME, JOB_CODE, SALARY
FROM EMPLOYEE
WHERE (JOB_CODE, SALARY) IN (
	SELECT JOB_CODE, TRUNC( AVG(SALARY),  -4)
	FROM EMPLOYEE
	GROUP BY JOB_CODE
);
                  
 */
----------------------------------------------------------------------------------------------------

-- 5. 상[호연]관 서브쿼리
--    상관 쿼리는 메인쿼리가 사용하는 테이블값을 서브쿼리가 이용해서 결과를 만듦
--    메인쿼리의 테이블값이 변경되면 서브쿼리의 결과값도 바뀌게 되는 구조임

-- 상관쿼리는 먼저 메인쿼리 한 행을 조회하고
-- 해당 행이 서브쿼리의 조건을 충족하는지 확인하여 SELECT를 진행함

-- 사수가 현재 테이블에 존재하는(있는?) 직원의 사번, 이름, 부서명, 사수사번 조회
SELECT EMP_ID, EMP_NAME, DEPT_TITLE, MANAGER_ID
FROM EMPLOYEE "MAIN"
LEFT JOIN DEPARTMENT ON(DEPT_CODE = DEPT_ID)
WHERE EXISTS (	-- 서브쿼리 조회 결과가 있으면 TRUE == 해당 행을 결과에 포함
		SELECT '있음' -- 서브쿼리에서 조회되는 컬럼 값은 중요한게 아님!!
									-- 조회되는 데이터가 있는지 없는지가 중요!
		FROM EMPLOYEE "SUB"
		WHERE "SUB".EMP_ID = "MAIN".MANAGER_ID
)
;
-- 직급별 급여 평균보다 급여를 많이 받는 직원의 
-- 이름, 직급코드, 급여 조회

-- 직급별 급여 평균
SELECT JOB_CODE, AVG(SALARY)
FROM EMPLOYEE
GROUP BY JOB_CODE
ORDER BY JOB_CODE ASC
;

--
SELECT EMP_NAME, JOB_CODE, SALARY
FROM EMPLOYEE "MAIN"			-- 메인쿼리 테이블 별칭이 "MAIN"
WHERE SALARY > (
		SELECT AVG(SALARY)
		FROM EMPLOYEE "SUB"		-- 서브쿼리 테이블 별칭이 "SUB"
		WHERE SUB.JOB_CODE = MAIN.JOB_CODE
				-- 먼저 해석된 메인쿼리의 1개 행에
				-- JOB_CODE 값을 얻어와
				-- 서브쿼리 해석에 사용
)
;

-- 부서별 입사일이 가장 빠른 사원의
--    사번, 이름, 부서명(NULL이면 '소속없음'), 직급명, 입사일을 조회하고
--    입사일이 빠른 순으로 조회하세요
--    단, 퇴사한 직원은 제외하고 조회하세요

SELECT EMP_ID, EMP_NAME, NVL(DEPT_TITLE, '소속없음'), JOB_NAME, HIRE_DATE
FROM EMPLOYEE "MAIN"
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
JOIN JOB USING (JOB_CODE)

WHERE HIRE_DATE = (
		-- 메인쿼리 1행을 해석했을 때 조회되는 행 중에서
		-- DEPT_CODE 값을 얻어와
		-- 서브쿼리에서 해당 DEPT_CODE 가 일치하는 사원들 중
		-- 가장 빠른 입사일을 조회
		SELECT MIN(HIRE_DATE)
		FROM EMPLOYEE "SUB"
		WHERE NVL("SUB".DEPT_CODE,'소속없음') = NVL("MAIN".DEPT_CODE, '소속없음')
		--> NULL은 비교가 안되기 때문에 가능한 형태로 변환
		--> 서브쿼리에서 조회된 결과를 다시 메인쿼리로 넘겨
		--  메인쿼리 WHERE절 조건을 충족하는지 확인
		AND ENT_YN != 'Y'		--> 퇴사자 제외
		--> 서브쿼리에서 조회된 결과를 다시 메인쿼리로 넘겨
		--  메인 쿼리 WHERE절 조건을 충족하는지 확인
)
;


/* 1) 메인 쿼리 한 행의 값을 서브 쿼리로 전달
 * 2) 서브 쿼리에서 전달 받은 값을 이용해서 SELECT 수행
 *    -> SELECT 결과를 다시 메인 쿼리로 반환
 * 3) 메인 쿼리에서 반환 받은 값을 이용해 
 *    해당 행의 결과 포함 여부를 결정
 */

----------------------------------------------------------------------------------------------------

-- 6. 스칼라 서브쿼리(== SELECT절에 사용되는 단일행 서브쿼리)
--    SELECT절에 사용되는 서브쿼리 결과로 1행만 반환
--    SQL에서 단일 값을 가르켜 '스칼라'라고 함

-- 각 직원들이 속한 직급의 급여 평균 조회

-- 1) 각 직급별 급여 평균 조회
SELECT JOB_CODE, AVG(SALARY)
FROM EMPLOYEE
GROUP BY JOB_CODE
ORDER BY JOB_CODE ASC
;

-- 2) 각 직원의 이름, 직급코드 조회
SELECT EMP_NAME, JOB_CODE
FROM EMPLOYEE
ORDER BY EMP_ID ASC
;

-- 3) 각 직원의 이름, 직급코드, "직급별 급여 평균" 조회
SELECT EMP_NAME, JOB_CODE, 
	(
		SELECT AVG(SALARY)
		FROM EMPLOYEE "SUB"
		WHERE SUB.JOB_CODE = MAIN.JOB_CODE
	) AS "직급별 급여 평균"
FROM EMPLOYEE "MAIN"
ORDER BY EMP_ID ASC
;

-- 모든 사원의 사번, 이름, 관리자사번, 관리자명을 조회
-- 단 관리자가 없는 경우 '없음'으로 표시
-- (스칼라 + 상관 쿼리)
SELECT EMP_ID, EMP_NAME, MANAGER_ID, 
	NVL(
		(
			SELECT EMP_NAME 
			FROM EMPLOYEE SUB
			WHERE SUB.EMP_ID = MAIN.MANAGER_ID
		)
	,'없음') AS 관리자명
FROM EMPLOYEE MAIN
;



----------------------------------------------------------------------------------------------------

-- 7. 인라인 뷰(INLINE-VIEW) ==> SELECT 문에서 조회되는 가상 테이블

/*
 * VIEW (객체) : 조회 용도의 가상 테이블
 * ==> SELECT는 가능하지만, INSERTM UPDATE, DELETE는 불가함
 */

--    FROM 절에서 서브쿼리를 사용하는 경우로
--    서브쿼리가 만든 결과의 집합(RESULT SET)을 테이블 대신에 사용한다.

-- 인라인뷰를 활용한 TOP-N분석
-- 전 직원 중 급여가 높은 상위 5명의
-- 순위, 이름, 급여 조회

-- 1) 전 직원의 급여를 내림차순으로 조회
SELECT EMP_NAME, SALARY
FROM EMPLOYEE
ORDER BY SALARY DESC
;

-- 2) ROWNUM 을 이용해서 행에 번호를 추가
-- ROWNUM : 행 번호(줄 번호)를 나타내는 가상의 컬럼
SELECT ROWNUM, EMP_NAME
FROM EMPLOYEE
WHERE ROWNUM <= 5
;

SELECT ROWNUM, EMP_NAME, SALARY
FROM EMPLOYEE
WHERE ROWNUM <= 5
ORDER BY SALARY DESC
;	-- 조회 결과가 잘못됨!!! ==> EMPLOYEE 테이블 위에서 5명을 잘라서 조회, 정렬된 결과

/* 인라인 뷰를 이용하여 해결 가능!!! */

SELECT ROWNUM, EMP_NAME, SALARY
FROM (SELECT EMP_NAME, SALARY
			FROM EMPLOYEE
			ORDER BY SALARY DESC)
			--> 서브쿼리 결과(23행 2열)를 메인쿼리의 테이블로 인식
WHERE ROWNUM <= 5
			--> 정렬된 서브쿼리 결과에서 1,2,3,4,5 행 조회
ORDER BY SALARY DESC
;

-- 급여 평균이 3위 안에 드는 부서의 부서코드와 부서명, 평균급여를 조회
SELECT NVL(DEPT_CODE,'없음') "부서 코드", NVL(DEPT_TITLE, '부서명 없음')"부서명", FLOOR(AVG(SALARY))
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
GROUP BY DEPT_CODE, DEPT_TITLE		--> SELECT에 있는거 다 적으면 된다
ORDER BY AVG(SALARY) DESC
;

SELECT ROWNUM "순위", "부서 코드", "부서명", "급여 평균"		--> 서브쿼리(인라인 뷰) 결과에 보여지는 컬럼명을 작성
FROM (
		SELECT NVL(DEPT_CODE,'없음') "부서 코드", NVL(DEPT_TITLE, '부서명 없음')"부서명", FLOOR(AVG(SALARY)) "급여 평균"
		FROM EMPLOYEE
		LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
		GROUP BY DEPT_CODE, DEPT_TITLE		--> SELECT에 있는거 다 적으면 된다
		ORDER BY AVG(SALARY) DESC
)
WHERE ROWNUM <= 3
;

----------------------------------------------------------------------------------------------------

-- 8. WITH
--    서브쿼리에 이름을 붙여주고 사용시 이름을 사용하게 함
--    인라인뷰로 사용될 서브쿼리에 주로 사용됨
--    실행 속도도 빨라진다는 장점이 있다. 

-- 
-- 전 직원의 급여 순위 
-- 순위, 이름, 급여 조회
-- 단, 10위 까지만 조회

-- A) 전 직원 급여 순위
SELECT EMP_NAME, SALARY
FROM EMPLOYEE
ORDER BY SALARY DESC
;

-- 1) FROM절에 서브쿼리 직접 작성한 형
SELECT ROWNUM, EMP_NAME, SALARY
FROM (SELECT EMP_NAME, SALARY
			FROM EMPLOYEE
			ORDER BY SALARY DESC)
WHERE ROWNUM <= 10
;

SELECT ROWNUM, EMP_NAME, SALARY
FROM (SELECT EMP_NAME, SALARY
			FROM EMPLOYEE
			ORDER BY SALARY DESC)
WHERE ROWNUM > 5
;		--> ROWNUM 에 문제가 있어서 이런게 안된다...

-- 2) WITH 이용하기
WITH TOP_SALARY		-- 서브쿼리 이름 지정
AS (		-- 저장할 서브쿼리 작성
		SELECT EMP_NAME, SALARY
		FROM EMPLOYEE
		ORDER BY SALARY DESC
)
SELECT ROWNUM, EMP_NAME, SALARY
FROM TOP_SALARY
WHERE ROWNUM <= 10
;

----------------------------------------------------------------------------------------------------

-- 9. RANK() OVER / DENSE_RANK() OVER

-- RANK() OVER : 동일한 순위 이후의 등수를 동일한 인원 수 만큼 건너뛰고 순위 계산
--               EX) 공동 1위가 2명이면 다음 순위는 2위가 아니라 3위



-- DENSE_RANK() OVER : 동일한 순위 이후의 등수를 이후의 순위로 계산
--                     EX) 공동 1위가 2명이어도 다음 순위는 2위

-- 1) RANK() OVER		-- ROWNUM은 컬럼값이 String 이고, RANK OVER는 Integer 이다!!
--			OVER() 괄호에 작성돼 정렬 기준대로 정렬 후 순위 지정
--			단, 값의 크기가 같다면 공동 순위 지정, 지정된 만큼 순위 건너뛰기

-- 급여를 많이 받는 순서대로 조회
SELECT RANK() OVER(ORDER BY SALARY DESC) "순위",		--> SALARY 내림 차순으로 정렬하고 순위를 지정함
	EMP_NAME, SALARY
FROM EMPLOYEE;

SELECT ROWNUM "순위", EMP_NAME, SALARY
FROM (SELECT EMP_NAME, SALARY
			FROM EMPLOYEE
			ORDER BY SALARY DESC)
;

-- 2) DENS_RANK() OVER : 공동 순위 지정 후 순위 건너뛰기를 하지 않음. 19, 19, 20등
SELECT DENSE_RANK() OVER(ORDER BY SALARY DESC) "순위", EMP_NAME, SALARY
FROM EMPLOYEE
;

----------------------------------------------------------------------------------------------------
/* ROWNUM 사용 시 주의사항!!! */
--> ROWNUM 이 WHERE 절에 사용되는 경우
--  항상 범위에 1부터 연속적인 범위가 포함 되어야만 한다!!!
-->	ROWNUM 은 RESULT SET 완성 후 적용되는 가상 컬럼이라서
--  정해진 규칙(1부터 1씩 증가)을 만족하지 못하면 사용 불가


-- 급여 순위 3위 ~ 7위 까지 조회하기
SELECT RANK() OVER(ORDER BY SALARY DESC) "순위", EMP_NAME, SALARY
FROM EMPLOYEE
;

SELECT "순위", EMP_NAME, SALARY
FROM (SELECT RANK() OVER(ORDER BY SALARY DESC) "순위", EMP_NAME, SALARY
			FROM EMPLOYEE)
WHERE ROWNUM BETWEEN 1 AND 7
--WHERE ROWNUM BETWEEN '3' AND '7'
--WHERE ROWNUM IN ('1','2','3','6','4','5')
;

-- 해결 방법 : 인라인 뷰 중첩 사용 --> ROWNUM 대신 "순위" 사용
SELECT "순위", EMP_NAME, SALARY
FROM (SELECT RANK() OVER(ORDER BY SALARY DESC) "순위", EMP_NAME, SALARY
			FROM EMPLOYEE)
WHERE "순위" BETWEEN 3 AND 7
;

----------------------------------------------------------------------------------------------------

SELECT * FROM EMPLOYEE;
SELECT * FROM JOB;
SELECT * FROM DEPARTMENT;
SELECT * FROM LOCATION;
SELECT * FROM SAL_GRADE;

-- [연습문제]

-- 1. 전지연 사원이 속해있는 부서원들을 조회하시오 (단, 전지연은 제외) 
--    사번, 사원명, 전화번호, 고용일, 부서명 
SELECT DEPT_CODE
FROM EMPLOYEE
WHERE EMP_NAME = '전지연'
;

SELECT EMP_ID, EMP_NAME, PHONE, HIRE_DATE, DEPT_TITLE, DEPT_ID
FROM EMPLOYEE
JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
WHERE DEPT_CODE = (
		SELECT DEPT_CODE
		FROM EMPLOYEE
		WHERE EMP_NAME = '전지연'
)
AND   EMP_NAME != '전지연'
;

-- 2. 고용일이 2010년도 이후인 사원들 중 급여가 가장 높은 사원의  
--    사번, 사원명, 전화번호, 급여, 직급명을 조회하시오. 
SELECT SALARY
FROM EMPLOYEE
--WHERE HIRE_DATE > '2010-01-01'
WHERE EXTRACT(YEAR FROM HIRE_DATE) >= 2010
;

SELECT EMP_ID, EMP_NAME, PHONE, SALARY, JOB_NAME
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
WHERE SALARY = (
		SELECT MAX(SALARY)
		FROM EMPLOYEE
		WHERE EXTRACT(YEAR FROM HIRE_DATE) >= 2010
)
;

-- 3. 노옹철 사원과 같은 부서, 같은 직급인 사원을 조회하시오. (단, 노옹철 사원은 제외) 
--    사번, 이름, 부서코드, 직급코드, 부서명, 직급명 
SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, DEPT_TITLE, JOB_NAME
FROM EMPLOYEE
JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
JOIN JOB USING (JOB_CODE)
WHERE (DEPT_CODE, JOB_CODE) = (
		SELECT DEPT_CODE, JOB_CODE
		FROM EMPLOYEE
		WHERE EMP_NAME = '노옹철'
)
AND   EMP_NAME != '노옹철'
;

-- 4. 2010년도에 입사한 사원과 부서와 직급이 같은 사원을 조회하시오 
--    사번, 이름, 부서코드, 직급코드, 고용일 
SELECT EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, HIRE_DATE
FROM EMPLOYEE
WHERE (DEPT_CODE, JOB_CODE) = (
		SELECT DEPT_CODE, JOB_CODE
		FROM EMPLOYEE
		WHERE EXTRACT(YEAR FROM HIRE_DATE) = 2010
)
;

-- 5. 87년생 여자 사원과 동일한 부서이면서 동일한 사수를 가지고 있는 사원을 조회하시오 
--    사번, 이름, 부서코드, 사수번호, 주민번호, 고용일 
SELECT EMP_ID, EMP_NAME, DEPT_CODE, MANAGER_ID, EMP_NO, HIRE_DATE
FROM EMPLOYEE
WHERE (DEPT_CODE, MANAGER_ID) = (
		SELECT DEPT_CODE, MANAGER_ID
		FROM EMPLOYEE
		WHERE EMP_NO LIKE '87%'
		AND   SUBSTR(EMP_NO,8,1) = '2'
)
;

-- 6. 부서별 입사일이 가장 빠른 사원의 
--    사번, 이름, 부서명(NULL이면 '소속없음'), 직급명, 입사일을 조회하고 
--    입사일이 빠른 순으로 조회하시오 
--    단, 퇴사한 직원은 제외하고 조회.. 

-- 1) GROUP BY 이용한 방법 (정확도 떨어짐) ==> 똑같은 날짜에 입사한 사람이 두명이면 조회가 중복으로됨
SELECT EMP_ID, EMP_NAME, NVL(DEPT_TITLE, '소속없음'), JOB_NAME, HIRE_DATE
FROM EMPLOYEE
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
--JOIN JOB USING (JOB_CODE)
JOIN JOB ON (EMPLOYEE.JOB_CODE = JOB.JOB_CODE)
WHERE HIRE_DATE IN (
		SELECT MIN(HIRE_DATE)
		FROM EMPLOYEE
		WHERE ENT_YN != 'Y'
		GROUP BY DEPT_CODE
)
ORDER BY HIRE_DATE ASC
;

-- 2) 상관 서브쿼리를 이용한 방법 (정확도가 더 높음)
SELECT EMP_ID, EMP_NAME, NVL(DEPT_TITLE, '소속없음'), JOB_NAME, HIRE_DATE
FROM EMPLOYEE E
LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
JOIN JOB J ON (E.JOB_CODE = J.JOB_CODE)
WHERE HIRE_DATE IN (
		SELECT MIN(HIRE_DATE)
		FROM EMPLOYEE SUB
		WHERE ENT_YN != 'Y'
--		AND SUB.DEPT_CODE = E.DEPT_CODE			-- NULL = NULL 비교는 안됨
		AND NVL(SUB.DEPT_CODE, '소속 없음') = NVL(E.DEPT_CODE, '소속 없음')	
		-- NULL은 비교 연산(= , !=) 이 불가능하기 때문에
		-- 비교할 수 있도록 NULL을 '소속 없음' 으로 변환해서 비교를 수행
)
ORDER BY HIRE_DATE ASC
;


-- 7. 직급별 나이가 가장 어린 직원의 
-- 사번, 이름, 직급명, 만 나이, 보너스 포함 연봉( (급여 * (1 + 보너스)) * 12)을 조회하고 
-- 나이순으로 내림차순 정렬하세요 
-- 단 연봉은 \124,800,000 으로 출력되게 하세요. (\ : 원 단위 기호) 
SELECT EMP_ID, EMP_NAME, JOB_NAME, 
	FLOOR((CURRENT_DATE - TO_DATE(SUBSTR(EMP_NO, 1, 6), 'RR-MM-DD')) / 365) AS "만 나이", 
	TO_CHAR(SALARY * 12 * (1 + NVL(BONUS, 0)), 'L999,999,999') AS "보너스 포함 연봉"
FROM EMPLOYEE
JOIN JOB USING (JOB_CODE)
WHERE EMP_NO IN (
		SELECT MAX(EMP_NO)
		FROM EMPLOYEE
		GROUP BY JOB_CODE
)
ORDER BY "만 나이" DESC
;

-- 1) 직급별 나이가 가장 어린 직원
SELECT MAX(TO_DATE(SUBSTR(EMP_NO, 1, 6)))
FROM EMPLOYEE
GROUP BY JOB_CODE
;

-- 2) 1번 결과를 다중행 서브쿼리로 사용해서 생일이 일치하는 사원들 조회
SELECT EMP_ID, EMP_NAME, JOB_NAME, 
	FLOOR(MONTHS_BETWEEN(CURRENT_DATE, TO_DATE(SUBSTR(EMP_NO, 1, 6))) / 12 ) AS "만 나이",
	TO_CHAR(SALARY * 12 * (1 + NVL(BONUS, 0)), 'L999,999,999') AS "연봉"
FROM EMPLOYEE E
JOIN JOB J ON (E.JOB_CODE = J.JOB_CODE)
WHERE TO_DATE(SUBSTR(EMP_NO,1,6)) IN (
		SELECT MAX(TO_DATE(SUBSTR(EMP_NO,1,6)))
		FROM EMPLOYEE
		GROUP BY JOB_CODE
)
;

/* 상관 서브쿼리를 이용하는 것이 정확도가 높다!! */
SELECT EMP_ID, EMP_NAME, JOB_NAME, 
	FLOOR(MONTHS_BETWEEN(CURRENT_DATE, TO_DATE(SUBSTR(EMP_NO, 1, 6))) / 12 ) AS "만 나이",
	TO_CHAR(SALARY * 12 * (1 + NVL(BONUS, 0)), 'L999,999,999') AS "연봉"
FROM EMPLOYEE E
JOIN JOB J ON (E.JOB_CODE = J.JOB_CODE)
WHERE TO_DATE(SUBSTR(EMP_NO,1,6)) IN (
		SELECT MAX(TO_DATE(SUBSTR(EMP_NO,1,6)))
		FROM EMPLOYEE SUB
		WHERE SUB.JOB_CODE = E.JOB_CODE
)		--> 현재 메인쿼리의 해석되는 행(1행)이 해당 직급에서 가장 어린 사원이 맞는지 비교 (1행씩)
;

--------------------------------------------------
-- 직급별 나이가 가장 어린 직원의 주민번호
SELECT MAX(EMP_NO)
FROM EMPLOYEE
GROUP BY JOB_CODE
;

-- 주민번호를 출생일자로
SELECT TO_DATE(SUBSTR(MAX(EMP_NO),1,6), 'RR-MM-DD')
FROM EMPLOYEE
GROUP BY JOB_CODE
ORDER BY MAX(EMP_NO)
;

-- 만 나이인데 정확하진 못함
SELECT FLOOR((CURRENT_DATE - TO_DATE(SUBSTR(MAX(EMP_NO),1,6), 'RR-MM-DD')) / 365) AS "만 나이"
FROM EMPLOYEE
GROUP BY JOB_CODE
ORDER BY MAX(EMP_NO)
;

-- 개월로 측정하는게 더욱 정확함
SELECT 
	FLOOR((CURRENT_DATE - TO_DATE(SUBSTR(EMP_NO,1,6), 'RR-MM-DD')) / 365) AS "일수 기준 만 나이",
	FLOOR(MONTHS_BETWEEN(CURRENT_DATE, TO_DATE(SUBSTR(EMP_NO, 1, 6))) / 12 ) AS "개월수 기준 만 나이"
FROM EMPLOYEE
;