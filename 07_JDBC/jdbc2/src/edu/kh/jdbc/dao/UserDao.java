package edu.kh.jdbc.dao;

// DAO (Data Access Object) : 데이터가 저장된 곳에 접근하는 용도의 객체
// ==> DB 에 접근하여 Java 에서 원하는 결과를 얻기 위해
//     SQL을 수행하고 결과 반환 받는 역할

import static edu.kh.jdbc.common.JDBCTemplate.*;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    
    // 필드
    // - DB 접근 관련한 JDBC 객체 참조형 변수를 미리 선언
    private Statement stmt = null;
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;
    
    // 메서드
    /** 전달 받은 Connection을 이용해 DB에 접근하여
     * 전달 받은 아이디와 일치하는 User 정보 조회하기
     * @Param conn : Service 에서 생성한 Connection 객체
     * @Param input : View 에서 입력 받은 아이디
     * @return
     */
    public User selectId(Connection conn, String input) {
        
        User user = null;   // 결과 저장용 변수
        
        try {
            // SQL 작성
            String sql = "SELECT * FROM TB_USER WHERE USER_ID = ?";
            
            // PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            
            // ?(placeholder)에 알맞은 값 대입
            pstmt.setString(1, input);
            
            // SQL 수행 후 결과 반환 반기
            rs = pstmt.executeQuery();
            
            // 조회 결과가 있을 경우
            // ==> 중복되는 아이디가 없을 경우 1행만 조회되기 때문에
            //     while 보단 if 를  사용하는게 효과적
            if (rs.next()) {
                // 각 컬럼의 값 얻어오기
                int userNo      = rs.getInt("USER_NO");
                String userId   = rs.getString("USER_ID");
                String userPw   = rs.getString("USER_PW");
                String userName = rs.getString("USER_NAME");
                
                // java.sql.Date 활용
                Date enrollDate  = rs.getDate("ENROLL_DATE");
                
                // 조회된 컬럼 값을 이용해 User 객체 생성
                user = new User(userNo, userId, userPw, userName, enrollDate.toString());
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 사용한 JDBC 객체 자원 반환(close)
            JDBCTemplate.close(rs);
            JDBCTemplate.close(pstmt);
            
            // Connection 객체는 Service에서 close(conn)!!!
        }
        return user;    // 결과 반환(생성된 User 또는 null)
    }
    
    
    /**
     * User 등록 DAO 메서드
     * @param conn : DB 연결 정보가 담겨있는 객체
     * @param user : 입력 받은 id, pw, name
     * @return result : INSERT 결과 행의 개수
     * @throws Exception : 발생하는 예외 모두 던짐
     */
    public int insertUser(Connection conn, User user) throws Exception {
        
        // SQL 수행 중 발생하는 예외를
        // catch로 처리하지 않고, throws를 이용해서 호출부로 던져 처리
        // ==> catch 문이 필요 없다!!
        
        // 1. 결과 저장용 변수 선언
        int result = 0;
        
        try {
            // 2. SQL 작성
            String sql = """
                    INSERT INTO TB_USER --(USER_ID, USER_PW, USER_NAME, ENROLL_DATE)
                    VALUES (SEQ_USER_NO.NEXTVAL, ?, ?, ?, DEFAULT)
                    """;
            
            // 3. PreparedStatement 생성
            pstmt = conn.prepareStatement(sql);
            
            // 4. ?(placeholder) 알맞은 값 대입
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserPw());
            pstmt.setString(3, user.getUserName());
            
            // 5. SQL (INSERT) 수행 (executeUpdate()) 후
            //    결과(삽입된 행의 개수, int) 반환 받기
            result = pstmt.executeUpdate();
        } finally {
            // 6. 사용한 JDBC 객체 자원 반환(close)
            close(pstmt);
            
        }
        
        return result;
    }   // 1. insertUser()
    
    /**
     * User 전체 조회 DAO 메서드
     * @param conn
     * @return userList
     * @throws Exception
     */
    public List<User> selectAll(Connection conn) throws Exception {
        
        // 1. 결과 저장용 변수 선언
        //    ==> List 같은 컬렉션을 반환하는 경우에는
        //        변수 선언 시 객체도 같이 생성해두자!!!!
        List<User> userList = new ArrayList<User>();
        
        try {
            // 2. SQL 작성
            String sql = """
                  SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
                         TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
                  FROM TB_USER
                  ORDER BY USER_NO ASC
                  """;
            
            // 3. PreparedStatement 생성
            pstmt = conn.prepareStatement(sql);
            
            // 4. ? 에 알맞은 값 대입 (없으면 패스)
            
            // 5. SQL(SELECT) 수행(executeQuery()) 후
            //    결과(ResultSet) 반환 받기
            rs = pstmt.executeQuery();
            
            // 6. 조회 결과(ResultSet)를
            //    커서를 이용해서 1행씩 접근하여 컬럼 값 얻어오기
            
            /* 몇 행이 조회될지 모른다 ==> while
               무조건 1행이 조회 된다  ==> if
            */
            
            // rs.next() : 커서를 1행 이동
            // 이동된 행에 데이터가 있으면 true, 없으면 false
            while (rs.next()) {
                int    userNo       = rs.getInt("USER_NO");
                String userId       = rs.getString("USER_ID");
                String userPw       = rs.getString("USER_PW");
                String userName     = rs.getString("USER_NAME");
                
                String enrollDate   = rs.getString("ENROLL_DATE");
                // - java.sql.Date 타입으로 값을 저장하지 않는 이유!
                // ==> TO_CHAR()를 이용해서 문자열로 변환했기 때문!
                
                // ** 조회된 값을 userList에 추가 **
                // User 객체를 생성해 조회된 값을 담고 userList에 추가하기
//                userList.add(new User(userNo, userId, userPw, userName, enrollDate));
                User user = new User(userNo, userId, userPw, userName, enrollDate);
                userList.add(user);
                
                // ResultSet을 List에 옮겨 담는 이유
                // 1. List가 사용이 편해서 ==> 호환되는 곳도 많음(jsp,thymeleaf 등)
                // 2. 사용된 ResultSet은 DAO에서 close 되기 때문
                
                
            }
            
        } finally {
            close(rs);
            close(pstmt);
        }
        
        // 조회 결과가 담긴 List 반환
        return userList;
    }   // 2. selectAll()
    
    /** 3
     * 이름에 검색어가 포함되는 회원 모두 조회 DAO 메서드
     * @param keyword
     * @param conn
     * @return searchList
     * @throws Exception
     */
    public List<User> selectName(String keyword, Connection conn) throws Exception {
        List<User> searchList = new ArrayList<>();
        
        try {
            String sql = """
                    SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
                    TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
                    FROM TB_USER
                    WHERE USER_NAME LIKE '%""" + keyword + "%'"
                    ;
//            WHERE USER_NAME LIKE '%' || ? || '%'

            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, keyword);
            rs = pstmt.executeQuery();

//            System.out.println(sql);
            while (rs.next()) {
                int    userNo       = rs.getInt("USER_NO");
                String userId       = rs.getString("USER_ID");
                String userPw       = rs.getString("USER_PW");
                String userName     = rs.getString("USER_NAME");
                String enrollDate   = rs.getString("ENROLL_DATE");
                User user = new User(userNo, userId, userPw, userName, enrollDate);
                searchList.add(user);
            }
        
        } finally {
            close(rs);
            close(pstmt);
        }
        return searchList;
    }   // 3. selectName()
    
    /** 4
     * USER_NO를 입력 받아 일치하는 User 조회
     * @param conn
     * @param input
     * @return user
     * @throws Exception
     */
    public User selectUser(Connection conn, int input) throws Exception {
        User user = null;
        
        try {
            String sql = """
                    SELECT USER_NO, USER_ID, USER_PW, USER_NAME,
                    TO_CHAR(ENROLL_DATE, 'YYYY"년" MM"월" DD"일"') ENROLL_DATE
                    FROM TB_USER
                    WHERE USER_NO = ?
                    """
                    ;
//                    WHERE USER_NO = '""\" + input + "'"

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, input);
            
            rs = pstmt.executeQuery();

//            System.out.println(sql);
            if (rs.next()) {
                int    userNo       = rs.getInt("USER_NO");
                String userId       = rs.getString("USER_ID");
                String userPw       = rs.getString("USER_PW");
                String userName     = rs.getString("USER_NAME");
                String enrollDate   = rs.getString("ENROLL_DATE");
                user = new User(userNo, userId, userPw, userName, enrollDate);
            }
        } finally {
            close(rs);
            close(pstmt);
        }
        return user;
    }   // 4. selectUser()
    
    
    /**5
     * USER_NO를 입력 받아 일치하는 User 삭제
     * @param conn
     * @param input
     * @return
     * @throws Exception
     */
    public int deleteUser(Connection conn, int input) throws Exception {
        
        // 1. 결과 저장용 변수 선언
        int result = 0;
        
        try {
            String sql = """
                    DELETE
                    FROM TB_USER
                    WHERE USER_NO = ?
                    """;
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, input);
            result = pstmt.executeUpdate();
        } finally {
            close(pstmt);
        }
        return result;
    }   // 5. deleteUser()
    
    /**6-1
     * 아이디, 비밀번호가 일치하는 User의 USER_NO 조회
     * @param conn
     * @param userId
     * @param userPw
     * @return userNo
     * @throws Exception
     */
    public int selectUser(Connection conn, String userId, String userPw) throws Exception {
        int userNo = 0;
        
        try {
            String sql = """
                    
                    SELECT USER_NO
                    FROM TB_USER
                    WHERE USER_ID = ?
                    AND   USER_PW = ?
                    """;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            pstmt.setString(2, userPw);
            
            // SQL(SELECT) 수행(executeQuery()) 후
            // 결과(ResultSet) 반환 받기
            rs = pstmt.executeQuery();
            
            // 조회된 1행이 있을 경우 (PK)
            if (rs.next()) {
                userNo = rs.getInt("USER_NO");
            }
            
        } finally {
            close(rs);
            close(pstmt);
        }
        return userNo;  // 조회 성공 USER_NO, 실패 0 반환
    }   // 6-1. selectUser
    
    /**6-2
     * userNo가 일치하는 User의 이름 수정
     * @param conn
     * @param userName
     * @param userNo
     * @return
     * @throws Exception
     */
    public int updateName(Connection conn, String userName, int userNo) throws Exception {
        int result = 0;
        
        try {
            String sql = """
                    UPDATE TB_USER
                    SET    USER_NAME = ?
                    WHERE  USER_NO = ?
                    """;
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setInt(2, userNo);
            
            result = pstmt.executeUpdate();
            
        } finally {
            close(pstmt);
        }
        return result;
    }
    
    /**7-1
     * 아이디 중복 확인
     * @param conn
     * @param userId
     * @return
     * @throws Exception
     */
    public int idCheck(Connection conn, String userId) throws Exception {
        
        int count = 0;  // 결과 저장용 변수
        
        try {
            String sql = """
                    SELECT COUNT(*)
                    FROM TB_USER
                    WHERE USER_ID = ?
                    """;
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {    // COUNT(*) 그룹함수 결과 1행만 조회
//                count = rs.getInt("COUNT(*)");  // columnLabel 이름으로 조회
                count = rs.getInt(1);   // columnIndex 로 조회. 컬럼 순서 맨앞
            }
        } finally {
            close(rs);
            close(pstmt);
        }
        return count;
    }
    
    
}

// 1. 결과 저장용 변수 선언
// User user = null;
// int result = 0;
// List<User> userList = new ArrayList<User>();
// try {

// 2. SQL 작성
// String sql =

// 3. PreparedStatement 생성
// pstmt = conn.prepareStatement(sql);

// 4. ?(placeholder) 에 알맞은 값 대입 (없으면 패스)

// 5-1. SQL(SELECT) 수행(executeQuery()) 후
//    결과(ResultSet) 반환 받기
//    rs = pstmt.executeQuery();

// 5-2. SQL (INSERT) 수행 (executeUpdate()) 후
//    결과(삽입된 행의 개수, int) 반환 받기
//    result = pstmt.executeUpdate();

// 6-1. 조회 결과(ResultSet)를
//    커서를 이용해서 1행씩 접근하여 컬럼 값 얻어오기
            /* 몇 행이 조회될지 모른다 ==> while
               무조건 1행이 조회 된다  ==> if
            */
// rs.next() : 커서를 1행 이동
// 이동된 행에 데이터가 있으면 true, 없으면 false
// while (rs.next()) {

// ** 조회된 값을 userList에 추가 **
// User 객체를 생성해 조회된 값을 담고 userList에 추가하기
// userList.add(new User(userNo, userId, userPw, userName, enrollDate));

// 6-2, 7-1
// finally {
// close(~);

// return ~;