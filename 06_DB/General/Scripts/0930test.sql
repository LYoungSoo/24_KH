CREATE TABLE "BOOK" (
		BOOK_NO NUMBER PRIMARY KEY,
		TITLE VARCHAR(100) NOT NULL,
		WRITER VARCHAR(50) NOT NULL,
		PRICE NUMBER
);

CREATE SEQUENCE SEQ_BOOK_NO;

INSERT INTO BOOK VALUES (SEQ_BOOK_NO.NEXTVAL, '불변의 법칙', '모건 하우절', 22500);
INSERT INTO BOOK VALUES (SEQ_BOOK_NO.NEXTVAL, '일류의 조건', '사이토 다카시', 17820);
INSERT INTO BOOK VALUES (SEQ_BOOK_NO.NEXTVAL, '나를 소모하지 않는 현명한 태도에 관하여', '마티아스 뇔케', 16020);
INSERT INTO BOOK VALUES (SEQ_BOOK_NO.NEXTVAL, '철도원 삼대', '황석영', 18000);
INSERT INTO BOOK VALUES (SEQ_BOOK_NO.NEXTVAL, '꽃길이 따로 있나, 내 삶이 꽃인 것을', '오평선', 15120);

COMMIT;

SELECT * FROM BOOK;

DROP SEQUENCE SEQ_BOOK_NO;
DROP TABLE BOOK;

----------------------------------------------------------------------------재시험



CREATE TABLE "MEMBER" (
	MEMBER_NO NUMBER PRIMARY KEY,
	NAME VARCHAR(100) NOT NULL,
	ADDRESS VARCHAR(50) NOT NULL,
	AGE NUMBER
);

CREATE SEQUENCE SEQ_MEMBER_NO;
INSERT INTO MEMBER VALUES (SEQ_MEMBER_NO.NEXTVAL, '유저일', '서울시 강남구', 20);
INSERT INTO MEMBER VALUES (SEQ_MEMBER_NO.NEXTVAL, '유저이', '서울시 강북구', 35);
INSERT INTO MEMBER VALUES (SEQ_MEMBER_NO.NEXTVAL, '유저삼', '서울시 성동구', 40);
INSERT INTO MEMBER VALUES (SEQ_MEMBER_NO.NEXTVAL, '유저사', '서울시 종로구', 18);
INSERT INTO MEMBER VALUES (SEQ_MEMBER_NO.NEXTVAL, '유저오', '서울시 성북구', 32);

COMMIT;

SELECT * FROM MEMBER;