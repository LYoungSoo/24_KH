/* 관리자 계정(sys) 접속 후 수행 */

/*ALTER SESSION SET "_ORACLE_SCRIPT"=TRUE;

-- 계정 생성
CREATE USER SPRING_LYS IDENTIFIED BY SPRING1234;

-- 권한 부여
GRANT CONNECT, RESOURCE TO SPRING_LYS;

-- 테이블 저장 공간 할당
ALTER USER SPRING_LYS
DEFAULT TABLESPACE USERS
QUOTA 100M ON USERS
;*/

----------------------------------------------------------------------------------------------------

/* SPRING 계정 접속 후 테이블, 시퀀스 생성 */

/* 회원("MEMBER") */
CREATE TABLE "MEMBER" (
		"MEMBER_NO"				NUMBER,
		"MEMBER_EMAIL"		VARCHAR2(50)	NOT NULL,
		"MEMBER_PW"				VARCHAR2(100) NOT NULL,
		"MEMBER_NICKNAME"	NVARCHAR2(10) NOT NULL,
		"MEMBER_TEL"			CHAR(11)			NOT NULL,
		"MEMBER_ADDRESS"	NVARCHAR2(150),
		"PROFILE_IMG"			VARCHAR2(300),
		"ENROLL_DATE"			DATE DEFAULT CURRENT_DATE,
		"MEMBER_DEL_FL"		CHAR(1) DEFAULT 'N',
		"AUTHORITY"				NUMBER DEFAULT 1,
		
		CONSTRAINT "MEMBER_PK" PRIMARY KEY("MEMBER_NO"),
		CONSTRAINT "MEMBER_DEL_FL_CHK" CHECK("MEMBER_DEL_FL" IN ('Y' , 'N')),
		CONSTRAINT "AUTHORITY_CHK" CHECK("AUTHORITY" IN (1, 2))
);

-- COMMENT 추가
COMMENT ON COLUMN "MEMBER"."MEMBER_NO"				IS '회원 번호(PK)';
COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL"			IS '회원 이메일(ID)';
COMMENT ON COLUMN "MEMBER"."MEMBER_PW" 				IS '회원 비밀번호';
COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME"	IS '회원명(별명)';
COMMENT ON COLUMN "MEMBER"."MEMBER_TEL"				IS '회원 전화번호(-제외)';
COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS"		IS '회원 주소';
COMMENT ON COLUMN "MEMBER"."PROFILE_IMG"			IS '프로필 이미지 경로';
COMMENT ON COLUMN "MEMBER"."ENROLL_DATE"			IS '가입일';
COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL"		IS '탈퇴 여부(Y,N)';
COMMENT ON COLUMN "MEMBER"."AUTHORITY"				IS '권한(1:일반, 2:관리자)';

-- 회원 번호 시퀀스 생성
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;

-- 샘플 회원 데이터 삽입
INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'member01@kh.or.kr', 'pass01!', '샘플1', '01012341234', 
			 NULL, NULL, DEFAULT, DEFAULT, DEFAULT)
;

COMMIT;

SELECT * FROM "MEMBER";

-- 샘플 데이터 비밀번호 암호화 적용
UPDATE "MEMBER"
SET "MEMBER_PW" = '$2a$10$X3H8rIWgfFWVKK2yA9J4Zu8pbNIG/eIYhDHzZqvi06jTExt4ox2Fi'
WHERE "MEMBER_NO" = 1;
COMMIT;

INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'member02@kh.or.krr', 
		'$2a$10$KzFKEvO4C65xBTetZDV8QufZvhQnIGU0SE5ZEaZo0T9SrdYS5oFMC', 
		'샘플2', '01022222222', 
		NULL, NULL, DEFAULT, DEFAULT, DEFAULT);

INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'member03@kh.or.kr', 
		'$2a$10$KzFKEvO4C65xBTetZDV8QufZvhQnIGU0SE5ZEaZo0T9SrdYS5oFMC', 
		'샘플3', '01033333333', 
		NULL, NULL, DEFAULT, DEFAULT, DEFAULT);

COMMIT;

UPDATE "MEMBER"
SET		MEMBER_DEL_FL = 'N'
WHERE MEMBER_DEL_FL = 'Y';

SELECT	COUNT(*)
FROM	"MEMBER"
WHERE	MEMBER_EMAIL = 'member05@kh.or.kr'
AND		MEMBER_DEL_FL = 'N';

SELECT * FROM "MEMBER"
ORDER BY MEMBER_NO ASC;

UPDATE "MEMBER"
SET			MEMBER_DEL_FL = DECODE(MEMBER_DEL_FL, 'Y','N','N','Y');


-- 파일 업로드 테스트용 테이블
CREATE TABLE TB_FILE_TEST(
	FILE_NO NUMBER PRIMARY KEY,
	FILE_ORIGINAL_NAME VARCHAR2(300),	-- 원본 파일명
	FILE_RENAME VARCHAR2(300),	-- 변경된 파일명
	FILE_PATH VARCHAR2(300),	-- 파일이 저장된 폴더명
	UPLOAD_DATE DATE DEFAULT CURRENT_DATE	-- 저장된 날짜
);

CREATE SEQUENCE SEQ_FILE_NO NOCACHE;	-- 시퀀스 생성

----------------------------------------------------------------------------------------------------

/* 게시판 종류 (BOARD_TYPE) 추가 */
INSERT INTO "BOARD_TYPE" VALUES (1, '공지 사항');
INSERT INTO "BOARD_TYPE" VALUES (2, '자유 게시판');
INSERT INTO "BOARD_TYPE" VALUES (3, '정보 게시판');

SELECT * FROM BOARD_TYPE;

COMMIT;

----------------------------------------------------------------------------------------------------

/* 게시글 번호 시퀀스 생성 */
CREATE SEQUENCE SEQ_BOARD_NO NOCACHE;
DROP SEQUENCE SEQ_BOARD_NO;

/* PL/SQL을 이용해서 BOARD 테이블에 샘플 데이터 삽입 */
BEGIN
		FOR I IN 1..2000 LOOP
			INSERT INTO "BOARD"
			VALUES(
				SEQ_BOARD_NO.NEXTVAL,
				SEQ_BOARD_NO.CURRVAL	|| '번째 게시글',
				SEQ_BOARD_NO.CURRVAL	|| '번째 게시글 내용 입니다',
				DEFAULT, DEFAULT, DEFAULT, DEFAULT,
				1,
				CEIL(DBMS_RANDOM.VALUE(0,3))
			);
		END LOOP;
END;

-- ALT + X 로 실행

-- 삽입된 행의 개수 확인
SELECT COUNT(*) FROM BOARD;
SELECT * FROM BOARD ORDER BY BOARD_NO DESC;

ROLLBACK;
-- 확인되면 COMMIT;
COMMIT;

-- 게시판 종류 별로 샘플 데이터 개수 확인
SELECT BOARD_CODE, COUNT(*)
FROM BOARD
GROUP BY BOARD_CODE
ORDER BY BOARD_CODE ASC;

----------------------------------------------------------------------------------------------------

/* 댓글 번호 시퀀스 생성 */
CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;
DROP SEQUENCE SEQ_COMMENT_NO;

/* 댓글 테이블("COMMENT") 에 샘플 데이터 삽입 */
BEGIN
	FOR I IN 1..3000 LOOP
		INSERT INTO "COMMENT"
		VALUES(
			SEQ_COMMENT_NO.NEXTVAL,
			SEQ_COMMENT_NO.CURRVAL || '번째 댓글',
			DEFAULT, DEFAULT,
			1,
			CEIL(DBMS_RANDOM.VALUE(0,1999)),
			NULL
		);
	END LOOP;
END;



SELECT BOARD_NO, COUNT(*)
FROM "COMMENT"
GROUP BY BOARD_NO
ORDER BY BOARD_NO DESC;
	
ROLLBACK;
COMMIT;
----------------------------------------------------------------------------------------------------
/* 특정 게시판(BOARD_CODE)에
 * 삭제되지 않은 게시글 목록 조회
 * 
 * - 조회된 행 번호, : ROWNUM 또는 ROWNUMBER() 이용
 *   게시글 번호, 제목, 조회수, 작성일 : BOARD 테이블 컬럼
 *   작성자 닉네임, : MEMBER 테이블
 *   댓글 수, : COMMENT 테이블에서 BOARD_NO 별 댓글 개수 COUNT(*)
 *   좋아요 개수 : BOARD_LIKE 테이블에서 BOARD_NO 별 댓글 개수 COUNT(*)
 * 
 * - 작성일은 몇 초/분/시간 전 또는 YYYY-MM-DD 형식으로 조회
 * - 가장 최근 글이 제일 처음 조회 (시퀀스 번호로 만들어진 BOARD_NO를 이용)
 */

-- ROW_NUMBER() OVER (ORDER BY BOARD_NO ASC) AS "RNUM"
--==> BOARD_NO 오름차순으로 정렬 후 조회된 행의 번호를 지정
--    + 해당 컬럼 별칭을 "RNUM" 지정
--==> 연속 되지 않은 PK 대신 연속된 숫자를 만들려고 사용!

-- 상관 서브쿼리 해석 순서
-- 1) 메인 쿼리 1행 해석
-- 2) 서브 쿼리에서 메인 쿼리 1행 조회 결과를 이용 ==> 해석
-- 3) 다시 메인 쿼리 해석

SELECT	ROW_NUMBER() OVER (ORDER BY BOARD_NO ASC) "RNUM", 
				BOARD_NO, BOARD_TITLE, READ_COUNT, MEMBER_NICKNAME,
				(SELECT COUNT(*) FROM "COMMENT" C WHERE C.BOARD_NO = B.BOARD_NO) AS "COMMENT_COUNT",
				(SELECT COUNT(*) FROM "BOARD_LIKE" L WHERE L.BOARD_NO = B.BOARD_NO) AS "LIKE_COUNT",
				CASE
					WHEN CURRENT_DATE - BOARD_WRITE_DATE < 1 / 24 / 60
					THEN  FLOOR((CURRENT_DATE - BOARD_WRITE_DATE) * 24 * 60 * 60) || '초 전'
					
					WHEN CURRENT_DATE - BOARD_WRITE_DATE < 1 / 24
					THEN  FLOOR((CURRENT_DATE - BOARD_WRITE_DATE) * 24 * 60) || '분 전'
					
					WHEN CURRENT_DATE - BOARD_WRITE_DATE < 1
					THEN  FLOOR((CURRENT_DATE - BOARD_WRITE_DATE) * 24) || '시간 전'
					
					ELSE TO_CHAR(BOARD_WRITE_DATE, 'YYYY-MM-DD')
				END AS "BOARD_WRITE_DATE"
FROM 		"BOARD" B
JOIN		"MEMBER" M ON (B.MEMBER_NO = M.MEMBER_NO)
WHERE 	BOARD_DEL_FL = 'N'	-- 삭제 안된 글
AND			BOARD_CODE = 1
ORDER BY RNUM DESC
;



-- DATE 타입끼리 ( - ) 연산 시 결과 = 몇 일 차이 (일 단위)의 결과로 출력됨 => 1시간은 0.083333...?
SELECT CURRENT_DATE - TO_DATE('2024-10-01 10:36:01', 'YYYY-MM-DD HH24:MI:SS') FROM DUAL;

-- WRITE_DATE 출력 테스트 용 삽입구문
INSERT INTO "BOARD"
			VALUES(
				SEQ_BOARD_NO.NEXTVAL,
				SEQ_BOARD_NO.CURRVAL	|| '번째 게시글',
				SEQ_BOARD_NO.CURRVAL	|| '번째 게시글 내용 입니다',
				DEFAULT, DEFAULT, DEFAULT, DEFAULT,
				1,
				CEIL(DBMS_RANDOM.VALUE(0,3))
			);

----------------------------------------------------------------------------------------------------






