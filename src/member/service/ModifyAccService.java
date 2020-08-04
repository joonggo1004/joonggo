package member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;

public class ModifyAccService {
	
	private MemberDao memberDao = new MemberDao();
	
	public void update(ModifyRequest modifyReq) {
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Member member = memberDao.selectById(conn, modifyReq.getId());
			if (member == null) {
				JdbcUtil.rollback(conn);
				throw new DuplicateIdException();
			}
			
			memberDao.update(conn,
					new Member(
							modifyReq.getId(),
							modifyReq.getPassword(),
							modifyReq.getName(),
							modifyReq.getPhone(),
							modifyReq.getEmail(),
							new Date())
					);
			conn.commit();
			
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} finally {
			JdbcUtil.close(conn);
		}
	}

}
