-- 1번
-- 영어영문학과(학과코드 002) 학생들의 학번과 이름, 입학 년도를  
-- 입학 년도가 빠른 순으로 표시하는 SQL을 작성하시오. 
-- (단, 헤더는 "학번", "이름", "입학년도" 가 표시되도록 한다.)
SELECT STUDENT_NO 학번, STUDENT_NAME 이름, SUBSTR(ENTRANCE_DATE,1,10) AS 입학년도
FROM TB_STUDENT
WHERE DEPARTMENT_NO = 002
ORDER BY ENTRANCE_DATE ASC
;

-- 2번 
-- 춘 기술대학교의 교수 중 이름이 세 글자가 아닌 교수가 두 명 있다고 한다. 
-- 그 교수의 이름과 주민번호를 조회하는 SQL을 작성하시오. 
SELECT PROFESSOR_NAME, PROFESSOR_SSN
FROM TB_PROFESSOR
WHERE LENGTH(PROFESSOR_NAME) != 3
;

-- 3번
-- 춘 기술대학교의 남자 교수들의 이름과 나이를 나이 오름차순으로 조회하시오. 
-- (단, 교수 중 2000년 이후 출생자는 없으며 출력 헤더는 "교수이름"으로 한다.  
-- 나이는 '만'으로 계산한다.) 
SELECT PROFESSOR_NAME "교수 이름", (SUBSTR(CURRENT_DATE, 1, 2) + 100) - SUBSTR(PROFESSOR_SSN, 1, 2) AS 나이, PROFESSOR_SSN
FROM TB_PROFESSOR
WHERE SUBSTR(PROFESSOR_SSN, 8, 1) = '1'
ORDER BY PROFESSOR_SSN DESC
;

-- 4번 
-- 교수들의 이름 중 성을 제외한 이름만 조회하시오. 출력 헤더는 "이름"이 찍히도록 한다. 
-- (성이 2자인 경우의 교수는 없다고 가정) 
SELECT SUBSTR(PROFESSOR_NAME, 2) 이름
FROM TB_PROFESSOR
;


-- 5번 
-- 춘 기술대학교의 재수생 입학자를 조회하시오. 
-- (19살에 입학하면 재수를 하지 않은 것!) 


-- 6번 
-- 춘 기술대학교의 2000년도 이후 입학자들은 학번이 A로 시작하게 되어있다.  
-- 2000년도 이전 학번을 받은 학생들의 학번과 이름 조회하는 SQL을 작성하시오. 
SELECT STUDENT_NO, STUDENT_NAME
FROM TB_STUDENT
WHERE SUBSTR(STUDENT_NO,1,1) != 'A'
;

-- 7번 
-- 학번이 A517178인 한아름 학생의 학점 총 평점을 구하는 SQL문을 작성하시오. 
-- 단, 이때 출력 화면의 헤더는 "평점"이라고 찍히게 하고,  
-- 점수는 반올림하여 소수점 이하 한자리까지만 표시한다. 
SELECT ROUND(AVG(POINT),1) 평점
FROM TB_GRADE
WHERE STUDENT_NO = 'A517178'
;

-- 8번 
-- 학과별 학생 수를 구하여 "학과번호", "학생수(명)"의 형태로 조회하시오. 
SELECT DEPARTMENT_NO "학과번호", COUNT(*) AS "학생수(명)"
FROM TB_DEPARTMENT
JOIN TB_STUDENT USING (DEPARTMENT_NO)
GROUP BY DEPARTMENT_NO
ORDER BY DEPARTMENT_NO ASC
;

-- 9번 
-- 지도 교수를 배정받지 못한 학생의 수를 조회하시오. 
SELECT COUNT(*)
FROM TB_STUDENT
WHERE COACH_PROFESSOR_NO IS NULL
;

-- 10번 
-- 학번이 A112113인 김고운 학생의 년도 별 평점을 구하는 SQL문을 작성하시오.
-- 단, 이때 출력화면의 헤더는 "년도", "년도 별 평점"이라고 찍히게 하고, 
-- 점수는 반올림하여 소수점 이하 한자리까지만 표시한다. 
SELECT SUBSTR(TERM_NO,1,4) 년도, ROUND(AVG(POINT),1) "년도 별 평점"
FROM TB_GRADE
GROUP BY SUBSTR(TERM_NO,1,4), STUDENT_NO
HAVING STUDENT_NO = 'A112113'
ORDER BY 년도 ASC
;

-- 11번 
-- 학과 별 휴학생 수를 파악하고자 한다.  
-- 학과 번호와 휴학생 수를 조회하는 SQL을 작성하시오. 

SELECT DEPARTMENT_NO, COUNT(STUDENT_NO), 
FROM TB_STUDENT
WHERE ABSENCE_YN = 'Y'
GROUP BY DEPARTMENT_NO
ORDER BY DEPARTMENT_NO ASC
;		-- 이건 0이 제외된 결과다



-- 12번 
-- 춘 대학교에 다니는 동명이인인 학생들의 이름, 동명인 수를 조회하시오. 
SELECT STUDENT_NAME 동일이름, COUNT(STUDENT_NAME) "동명인 수"
FROM TB_STUDENT
GROUP BY STUDENT_NAME
HAVING COUNT(STUDENT_NAME) >= 2
ORDER BY STUDENT_NAME;

-- 13번 
-- 학번이 A112113인 김고운 학생의 학점을 조회하려고 한다. 
-- 년도, 학기 별 평점과 년도 별 누적 평점, 총 평점을 구하는 SQL을 작성하시오. 
-- (단, 평점은 소수점 1자리까지만 반올림하여 표시한다.) 
SELECT SUBSTR(TERM_NO,1,4) 년도, SUBSTR(TERM_NO,5,2) 학기, ROUND(AVG(POINT),1) 평점
FROM TB_GRADE
WHERE STUDENT_NO = 'A112113'
GROUP BY ROLLUP(SUBSTR(TERM_NO,1,4), SUBSTR(TERM_NO,5,2))
;