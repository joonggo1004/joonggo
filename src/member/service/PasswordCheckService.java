package member.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class PasswordCheckService {
	
	private MemberDao memberDao = new MemberDao();
	
	public boolean passwordCheck(String userId, String password) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			
			Member member = memberDao.selectById(conn, userId);
			if (member == null) {
				throw new MemberNotFoundException();
			}
			return member.matchPassword(password);
			
		} catch (SQLException e) {
			e.printStackTrace();
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
