package edu.kh.jdbc;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Practice1 {
    public static void main(String[] args) {
//        PreparedStatement ps = new PreparedStatement();
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // jdbc:oracle:thin:@//localhost:1521/XE
            String type = "jdbc:oracle:thin:@";
            String host = "localhost";
            String port = ":1521";
            String dbName = ":XE";
            
            String userName = "KH_LYS";
            String password = "KH1234";
            conn = DriverManager.getConnection(type + host + port + dbName, userName, password);
            
            Scanner sc = new Scanner(System.in);
            
            System.out.print("조회할 성별(M/F) : ");
            String inputGender = sc.next().toUpperCase();
            switch (inputGender) {
                case "M","'M'" : inputGender = "'M'"; break;
                case "F","'F'" : inputGender = "'F'"; break;
                default: inputGender = "'M','F'";
                    System.out.println("성별입력오류");
            }
            System.out.println("성별 : " + inputGender);
            
            System.out.println("급여 범위");
//            System.out.print("급여 최소값 : ");
            int minSalary = sc.nextInt();
//            System.out.print("급여 최대값 : ");
            int maxSalary = sc.nextInt();
            if (minSalary < Integer.MIN_VALUE) minSalary = Integer.MIN_VALUE;
            if (maxSalary > Integer.MAX_VALUE) maxSalary = Integer.MAX_VALUE;
            if (minSalary > maxSalary) {
                minSalary ^= maxSalary;
                maxSalary ^= minSalary;
                minSalary ^= maxSalary;
            }
            System.out.println("min : " + minSalary + " | max : " + maxSalary);
            
            System.out.print("급여 오름차순 / 내림차순 : ");
            String inputSort = sc.next().toUpperCase();
            
            switch (inputSort) {
                case "내림차순", "2", "DESC" : inputSort = "DESC"; break;
                case "오름차순", "1", "ASC"  :
                default: inputSort = "ASC";
            }
            
            String sql = """
                    SELECT EMP_ID "사번", EMP_NAME "이름",
                    DECODE(MOD(SUBSTR(EMP_NO,8,1),2), 1, 'M', 0, 'F') "성별",
                    SALARY "급여", JOB_NAME "직급명", DEPT_TITLE "부서명"
                    FROM EMPLOYEE
                    JOIN DEPARTMENT ON (DEPT_CODE = DEPT_ID)
                    JOIN JOB USING (JOB_CODE)
                    WHERE DECODE(MOD(SUBSTR(EMP_NO,8,1),2), 1, 'M', 0, 'F') IN("""
                    + inputGender + ")\n" + """
                    AND   SALARY BETWEEN\s""" + minSalary + "\sAND\s" + maxSalary + "\n" + """
                    ORDER BY SALARY\s""" + inputSort;
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            System.out.println(sql + "\n;");
            
            
            int count = 0;
            System.out.println("  사번\t| 이름\t\t| 성별\t| 급여\t\t| 직급명\t| 부서명");
            System.out.println("------------------------------------------------------------");
            
            boolean flag = false;
            while (rs.next()) {
                flag = true;
                String empId = rs.getString("사번");
                String empName = rs.getString("이름");
//                char gender = rs.getString("성별").charAt(0);
                String gender = rs.getString("성별");
                int salary = rs.getInt("급여");
                String jobName = rs.getString("직급명");
                String deptTitle = rs.getString("부서명");
                
                System.out.printf("%d. %s\t| %s\t| %s\t\t| %d\t| %s  \t| %s\n",
                        ++count, empId, empName, gender, salary, jobName, deptTitle);
                /*
                System.out.printf("%-4s | %3s | %-4s | %7d | %-3s  | %s \n",
				empId, empName, gen, salary, jobName, deptTitle);
                 */
            }
            if (flag = false) System.out.println("조회 결과 없음");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }   // finally - try
        }   // try
    }   // main
}   // class
/*
프로젝트명 :  jdbc1
패키지명 : edu.kh.jdbc
클래스명 : Practice1

// EMPLOYEE	테이블에서
// 사번, 이름, 성별, 급여, 직급명, 부서명을 조회
// 단, 입력 받은 조건에 맞는 결과만 조회하고 정렬할 것

// - 조건 1 : 성별 (M, F)
// - 조건 2 : 급여 범위
// - 조건 3 : 급여 오름차순/내림차순

// [실행화면]
// 조회할 성별(M/F) : F
// 급여 범위(최소, 최대 순서로 작성) : 3000000 4000000
// 급여 정렬(1.ASC, 2.DESC) : 2

// 사번 | 이름   | 성별 | 급여    | 직급명 | 부서명
//--------------------------------------------------------
// 218  | 이오리 | F    | 3890000 | 사원   | 없음
// 203  | 송은희 | F    | 3800000 | 차장   | 해외영업2부
// 212  | 장쯔위 | F    | 3550000 | 대리   | 기술지원부
// 222  | 이태림 | F    | 3436240 | 대리   | 기술지원부
// 207  | 하이유 | F    | 3200000 | 과장   | 해외영업1부
// 210  | 윤은해 | F    | 3000000 | 사원   | 해외영업1부
*/

class PreparedStatement {
    // 1. JDBC 객체 참조
}