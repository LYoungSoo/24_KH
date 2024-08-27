package edu.kh.jdbc.run;

import edu.kh.jdbc.view.UserView;

public class UserRun {
    public static void main(String[] args) {
        
//        System.out.println(JDBCTemplate.getConnection());
        // oracle.jdbc.driver.T4CConnection@7364985f
        
        UserView view = new UserView();
        view.test();
    }
}
