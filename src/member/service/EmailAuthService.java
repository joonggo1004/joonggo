package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import auth.service.LoginFailException;
import auth.service.User;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class EmailAuthService {
	
	private MemberDao memberDao = new MemberDao();
	
	public boolean getEmailChecked(String userId) {
		
		Connection conn = null;
		boolean result = false;
		try {
			conn = ConnectionProvider.getConnection();
			Member member = memberDao.selectById(conn, userId);
			result = member.isEmailChecked();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	public String getEmail(String userId) {
		
		Connection conn = null;
		String result = null;
		try {
			conn = ConnectionProvider.getConnection();
			Member member = memberDao.selectById(conn, userId);
			result = member.getEmail();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	public int setEmailChecked(String userId) {
		Connection conn = null;
		int result = 0;
		try {
			conn = ConnectionProvider.getConnection();
			result = memberDao.setEmailChecked(conn, userId);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}
	
	public User getUser(String userId) {
		try (Connection conn = ConnectionProvider.getConnection()){
			Member member = memberDao.selectById(conn, userId);
			if (member == null) {
				throw new LoginFailException();
			}
			return new User(member.getId(), member.getName(), member.isEmailChecked());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
