package edu.kh.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JDBCExample4 {
    public static void main(String[] args) {
        
        // 부서명을 입력 받아
        // 해당 부서에 근무하는 사원의
        // 사번, 이름, 부서명, 직급명을
        // 직급코드 오름차순으로 조회
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // JDBC URL TEMPLATE : jdbc:oracle:thin:@//localhost:1521/XE
            String url = "jdbc:oracle:thin:@localhost:1521:XE";
            String userName = "KH_LYS";
            String password = "KH1234";
//            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","KH_LYS","KH1234");
            conn = DriverManager.getConnection(url,userName,password);
            Scanner sc = new Scanner(System.in);
            System.out.print("부서명 : ");
            String input = sc.next();
            String sql = """
                        SELECT EMP_ID, EMP_NAME, NVL(DEPT_TITLE, '없음') DEPT_TITLE, JOB_NAME, JOB_CODE
                        FROM EMPLOYEE
                        LEFT JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
                        JOIN JOB USING (JOB_CODE)
                        WHERE DEPT_TITLE = '""" + input + "'" + """
                        ORDER BY JOB_CODE ASC"""
                        ;
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
//            System.out.println(rs); // oracle.jdbc.driver.ForwardOnlyResultSet@55322aab
            
            int count = 0;
            while (rs.next()) {
                String empId = rs.getString("EMP_ID");
                String empName = rs.getString("EMP_NAME");
                String deptTitle = rs.getString("DEPT_TITLE");
                String jobName = rs.getString("JOB_NAME");
                String jobCode = rs.getString("JOB_CODE");
                count++;
                System.out.printf("%d.\t 사번 : %s\t/ 이름 : %s\t/ 부서명 : %s\t/ 직급명 : %s \t/ 직급코드 : %s\n",
                        count, empId, empName, deptTitle, jobName, jobCode);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
