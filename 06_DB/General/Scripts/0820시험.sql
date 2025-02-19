-- [요구사항]
-- 김영희 회원과 같은 지역에 사는 회원들의
-- 지역명, 아이디, 이름, 등급명을
-- 이름 오름차순으로 조회

--[작성한 SQL]

SELECT AREA_NAME 지역명, MEMBER_ID 아이디, MEMBER_NAME 이름, GRADE_NAME 등급명
FROM TB_MEMBER
JOIN TB_GRADE ON(GRADE = GRADE_CODE)
JOIN TB_AREA ON (AREA_CODE = AREA_CODE)
ORDER BY 이름 DESC;


-------------------------------------------------------
SELECT AREA_NAME 지역명, MEMBER_ID 아이디, MEMBER_NAME 이름, GRADE_NAME 등급명
FROM TB_MEMBER M
JOIN TB_GRADE G ON(GRADE = GRADE_CODE)
JOIN TB_AREA  A ON (M.AREA_CODE = A.AREA_CODE)
WHERE M.AREA_CODE = (
	SELECT AREA_CODE
	FROM TB_MEMBER
	WHERE MEMBER_NAME = '김영희')
	AND MEMBER_NAME != '김영희'
ORDER BY MEMBER_NAME ASC
;
-----------------------------------------------------
SELECT AREA_NAME 지역명, MEMBER_ID 아이디, MEMBER_NAME 이름, GRADE_NAME 등급명
FROM TB_MEMBER
JOIN TB_GRADE ON(GRADE = GRADE_CODE)
JOIN TB_AREA USING(AREA_CODE)
WHERE AREA_CODE = (
	SELECT AREA_CODE
	FROM TB_MEMBER
	WHERE MEMBER_NAME = '김영희')
	AND MEMBER_NAME != '김영희'
ORDER BY 이름 ASC
;
-----------------------------------------------------

SELECT AREA_CODE
FROM TB_MEMBER
WHERE MEMBER_NAME = '김영희'
;

SELECT * FROM TB_MEMBER;
SELECT * FROM TB_GRADE;
SELECT * FROM TB_AREA;



SELECT AREA_NAME 지역명, MEMBER_ID 아이디, MEMBER_NAME 이름, GRADE_NAME 등급명
FROM TB_MEMBER
JOIN TB_GRADE ON (GRADE = GRADE_CODE)
JOIN TB_AREA USING (AREA_CODE)
WHERE AREA_CODE = (
	SELECT AREA_CODE FROM TB_MEMBER WHERE MEMBER_NAME = '김영희'
	)
--AND MEMBER_NAME != '김영희'
ORDER BY 이름 ASC
;