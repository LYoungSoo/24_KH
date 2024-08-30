package edu.kh.jdbc.dao;

//지정된 클래스의 static 메서드를 모두 얻어와 사용
import static edu.kh.jdbc.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.kh.jdbc.common.JDBCTemplate;
import edu.kh.jdbc.dto.User;

public class UserDaoImpl implements UserDao {

	// 필드

	// JDBC 객체 참조 변수 + Properties 참조 변수 선언
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private Properties prop;
	// ==> K ,V 가 모두 String인 Map, 파일 입출력이 쉬움

	// 기본 생성자
	public UserDaoImpl() {

		// 객체 생성 시
		// 외부에 존재하는 sql.xml 파일을 읽어와
		// prop 에 저장

		try {
			String filePath = JDBCTemplate.class.getResource("/edu/kh/jdbc/sql/sql.xml").getPath();
			// 파일 경로 잘못된거 잘 확인해볼것

			// 지정된 경로의 XML 파일 내용을 읽어와
			// Properties 객체에 K:V 세팅
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public int insertUser(Connection conn, User user) throws Exception {

		// 1. 결과 저장용 변수 선언
		int result = 0;

		try {
			// 2. SQL 작성 ==> Properties를 이용해 외부 sql.xml 파일에서 읽어온 sql이용
			String sql = prop.getProperty("insertUser");

			// 3. PreparedStatement 생성
			pstmt = conn.prepareStatement(sql);

			// 4. ? 에 알맞은 값 세팅
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getUserPw());
			pstmt.setString(3, user.getUserName());

			// 5. SQL(INSERT) 수행(executeUpdate()) 후
			// 결과 (삽입된 행의 개수, int) 반환
			result = pstmt.executeUpdate();

		} finally {
			// 6. 사용한 JDBC 객체 자원 반환(close)
			close(pstmt);
		}
		// 결과 반환
		return result;
	}

	@Override
	public int idCheck(Connection conn, String userId) throws Exception {

		int result = 0;

		try {
			String sql = prop.getProperty("idCheck");

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			if (rs.next()) { // 결과가 1행밖에 없어서 if 사용
				result = rs.getInt(1); // 조회 결과 1번 컬럼 값
			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return result;
	}

	@Override
	public User login(Connection conn, String userId, String userPw) throws Exception {

		// 결과 저장용 변수 선언
		User loginUser = null;

		try {
			String sql = prop.getProperty("login");

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int userNo = rs.getInt("USER_NO"); // DTO에 userNO 으로 되어있어서
				// 그거때문에 지금 거진 40분을!
				String id = rs.getString("USER_ID");
				String pw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				loginUser = new User(userNo, userId, userPw, userName, enrollDate);

			}

		} finally {
			close(rs);
			close(pstmt);
		}

		return loginUser;
	}

	@Override
	public List<User> selectAll(Connection conn) throws Exception {

		// 결과 저장용 변수 선언
		List<User> userList = new ArrayList<User>();

		try {
			String sql = prop.getProperty("selectAll");

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				User user = new User(userNo, userId, userPw, userName, enrollDate);

				userList.add(user);

			}

		} finally {
			close(rs);
			close(stmt);
		}

		return userList;
	}

	@Override
	public List<User> search(Connection conn, String searchId) throws Exception {

		// ArrayList 객체를 미리 생성하는 이유
		// == 조회된 결과를 추가(add) 해서 묶어서 반환하기 위해
		List<User> userList = new ArrayList<User>();

		try {
			String sql = prop.getProperty("search");

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchId); // '%검색어%'

			rs = pstmt.executeQuery();

			while (rs.next()) {
				int userNo = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				User user = new User(userNo, userId, userPw, userName, enrollDate);

				userList.add(user);

			}

		} finally {
			close(rs);
			close(stmt);
		}

		return userList;
	}

	@Override
	public User selectUser(Connection conn, String userNo) throws Exception {
		User user = null;
		
		try {
			String sql = prop.getProperty("selectUser");

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userNo);
			rs = pstmt.executeQuery();
		
			
			while (rs.next()) {
				int user_No = rs.getInt("USER_NO");
				String userId = rs.getString("USER_ID");
				String userPw = rs.getString("USER_PW");
				String userName = rs.getString("USER_NAME");
				String enrollDate = rs.getString("ENROLL_DATE");

				user = new User(user_No, userId, userPw, userName, enrollDate);
			}

		} finally {
			close(rs);
			close(stmt);
		}
		return user;
	}

	@Override
	public int deleteUser(Connection conn, int userNo) throws Exception {
		
		// 결과 저장용 변수
		int result = 0;		// delete 결과 행 개수 저장
		
		try {
			String sql = prop.getProperty("deleteUser");
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, userNo);
			
			// DML은 executeUpdate() 호출
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	@Override
	public int updateUser(Connection conn, User user) throws Exception {
		
		int result = 0;
		
		try {
			String sql = prop.getProperty("updateUser");
			
			String userPw = user.getUserPw();
			String userName = user.getUserName();
			int userNo = user.getUserNo();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userPw);
			pstmt.setString(2, userName);
			pstmt.setInt(3, userNo);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
