package edu.kh.jdbc;

import java.sql.*;

// ctrl + f11  실행
public class JDBCExample1 {
    public static void main(String[] args) {
    
        /* JDBC (Java DataBase Connectivity)
         *
         * - Java에서 DB에 연결(접근) 할 수 있게 해주는
         *   Java API (Java에서 제공하는 코드)
         *   ==> java.sql 패키지에 존재함
         */
        
        // Java 코드를 이용해
        // EMPLOYEE 테이블에서
        // 사번, 이름, 부서코드, 직급코드, 급여, 입사일 조회 후
        // 이클립스 콘솔에 출력
        
        /* 1. JDBC 객체 참조용 변수 선언 */
        
        // java.sql.connection
        // 특정 DBMS와 연결하기 위한 정보를 저장한 객체
        // == DBeaver 에서 사용하는 DB 연결
        Connection conn = null;
        
        // java.sql.Statement
        // - 1) SQL을 DB에 전달
        // - 2) DB에서 SQL 수행 결과를 반환 받아옴
        Statement stmt = null;
        
        // java.sql.ResultSet
        // - SELECT 조회 결과를 저장하는 객체
        ResultSet rs = null;
        
        try {
            /* 2. DrivarManager 객체를 이용해서 Connection 객체 생성하기*/
            // java.sql.DriverManager
            // - DB 연결 정보와 JDBC 드라이버를 이용해서
            //   원하는 dB와 연결할 수 있는 Connection 객체를 생성하는 객체
            
            /* 2-1) Oracle JDBC Driver 객체를 메모리(RAM)에 로드(적재) 하기 */
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // Class.forName("패키지명 + 클래스명")
            // - 해당 클래스를 읽어 메모리에 적재
            // ==> JVM이 프로그램 동작에 사용할 객체를 생성하는 구문
            
            // oracle.jdbc.driver.OracleDriver
            // - Oracle DBMS 연결 시 필요한 코드가 담겨있는 클래스
            //   (Oracle에서 만들어서 준 클래스)
            
            /* 2-2) DB 연결 정보 작성 */
            String type = "jdbc:oracle:thin:@"; // 드라이버의 종류
            
            String host = "localhost";  // DB 서버 컴퓨터의 IP 또는 도메인 주소
                                        // localhost == 현재 컴퓨터
            String port = ":1521";  // 프로그램 연결을 위한 구분 번호
            
            String dbName = ":XE"; // DBMS 이름(XE == eXpress Edition)
            
            String userName = "KH_LYS"; // 사용자 계정명
            
            String password = "KH1234"; // 계정 비밀번호
            
            /* 2-3) DB 연결 정보와 DriverManager를 이용해서 Connection 객체 생성 */
            conn = DriverManager.getConnection(type + host + port + dbName,
                                                    userName, password);
            // Connection 객체가 잘 생성되었는지 확인
            // == DB 연결 정보에 오타가 없는지 확인
            System.out.println(conn);
            // 개많이 좋아졌네 진짜 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ
            // 이거 연결한다고 미치고 팔짝뛰고 빙빙돌고 그랬는데 로그인안되고 막 아주그냥 막
            // mysql이 이상한건지 이클립스 구버전이 난리난건지 뭐가문제인지
            
            /* 3. SQL 작성 */
            // !! WARNING 주의사항 !!
            // ==> JDBC 코드에서 SQL 작성 시
            //     세미콜론 (;)을 작성하면 안된다!!!
            // ==> "SQL 명령어가 올바르게 종료되지 않았습니다" 예외 발생
            String sql = "SELECT "
                    + "EMP_ID, EMP_NAME, DEPT_CODE, JOB_CODE, SALARY, HIRE_DATE "
                    + "FROM EMPLOYEE"
                    ;
            
            /* 4. Statement 객체 생성 */
            stmt = conn.createStatement();  // 연결된 DB(Connection)에 SQL을 전달하고
                                            // 결과를 반환 받을 Statement 객체를 생성함
            
            /* 5. Statement 객체를 이용해서 SQL 수행 후 결과 반환받기 */
            // SELECT 는 RESULT SET을 반환, INSERT, UPDATE, DELETE는 INT(성공여부)를 반환
            
            // 1) ResultSet Statement.excuteQuery(sql);
            // ==> SELECT문 실행 메서드
            //     결과로 java.sql.ResultSet 반환
            
            
            // 2) int Statement.executeUpdate(sql);
            // ==> DML(INSERT, UPDATE, DELETE) 실행 메서드
            //     결과로 int 반환(삽입, 수정, 삭제된 행의 개수)
            rs = stmt.executeQuery(sql);
            
            /* 6. 조회 결과가 담겨 있는 ResultSet을 커서(Cursor)를 이용해 1행씩 접근하여
                  각 행에 작성된 컬럼 값 얻어오기 */
            
            // rs.next() : 커서를 다음 행으로 이동 시킨 후,
            //             이동된 행에 값이 있으면 true, 없으면 false 반환
            //             맨 처음 호출 시 1행부터 시작
            while (rs.next()) {
                // 200	선동일	D9	J1	8000000	2000-02-06 00:00:00.000
                // Str  Str     Str Str Int     Date
                
                // rs.get자료형 (컬럼명 | 순서);
                // - 현재 행에서 지정된 컬럼의 값을 얻어와 반환
                // ==> 지정된 자료형 형태로 값이 반환됨
                //     (자료형을 잘못 지정하면 예외 발생!)
                
                // [Java]           [DB]
                // Sting            CHAR, VARCHAR2
                // int, long        NUMBER (정수만 저장된 컬럼)
                // float, double    NUMBER (정수 + 실수)
                // java.sql.Date    DATE
                
                String empId = rs.getString("EMP_ID");
                String empName = rs.getString("EMP_NAME");
                String deptCode = rs.getString("DEPT_CODE");
                String jobCode = rs.getString("JOB_CODE");
                
                int  salary = rs.getInt("SALARY");
                
                Date hireDate = rs.getDate("HIRE_DATE");
                
                System.out.printf("사번 : %s\t/ 이름 : %s\t/ 부서코드 : %s \t/ 직급코드 : %s\t/ " +
                        "급여 : %d\t/ 입사일 : %s \n",
                        empId, empName, deptCode, jobCode, salary, hireDate.toString());
                
            }
            
        } catch (SQLException e) {
            // SQLException : DB 연결과 관련된 예외의 최상위 부모
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            /* 7. 사용 완료된 JDBC 객체 자원 반환(close) */
            // ==> 수행하지 않으면 DB와 연결된 Connection 이 남아있어서
            //     다른 클라이언트가 추가적으로 연결되지 못하는 문제가 발생할 수 있다!
            
            try {
                /* 만들어진 역순으로 close 수행하는 것을 추천 */
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
                // if문은 NullPointerException 방지용 구문
                
            } catch (SQLException e) {
                e.printStackTrace();
            }   // finally - try
        }   // try
        
    }   // main
}   // class

//             클래스 : 객체의 속성과 기능??을 정의하는 문서
