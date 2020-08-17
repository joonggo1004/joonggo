package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;
import util.SHA256;

public class JoinService {
	
	private MemberDao memberDao = new MemberDao();
	
	public void join(JoinRequest joinReq) {
		
		Connection conn = null;
		int result = 0;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Member member = memberDao.selectById(conn, joinReq.getId());
			if (member != null) { // 이미 존재하는 아이디
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			
			result = memberDao.insert(conn,
					new Member(
							joinReq.getId(),
							joinReq.getPassword(),
							joinReq.getName(),
							joinReq.getPhone(),
							joinReq.getEmail(),
							SHA256.getSHA256(joinReq.getEmail()),
							false,
							new Date())
					);
			if(result != 1) throw new JoinSQLException();
			conn.commit();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
