package test;

import java.sql.Connection;
import java.util.List;

import static test.ShopJDBCTemplate.close;
import static test.ShopJDBCTemplate.getConnection;

public class ShopService {
    
    private ShopDAO shopDAO = new ShopDAO();
    
    public ShopMember selectMember(String memberId) {
        Connection conn = getConnection();
        ShopMember sm = shopDAO.selectMember(conn, memberId);
        close(conn);
        return sm;
    }
}
