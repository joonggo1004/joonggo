package prodComment.service;

import java.sql.Connection;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import prodComment.dao.MessageDao;

public class GetMessageNoMax {
	
private static GetMessageNoMax instance = new GetMessageNoMax();
	
	public static GetMessageNoMax getInstance() {
		return instance;
	}
	
	private GetMessageNoMax() {
		
	}
	
	public int getMessageNoMax() {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			
			return messageDao.selectMaxMessageNo(conn);
			
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			JdbcUtil.close(conn);
		}
		return 0;
	}


}
