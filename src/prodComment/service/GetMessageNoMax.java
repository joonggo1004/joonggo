package prodComment.service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import prodComment.dao.MessageDao;
import prodComment.model.Message;
import jdbc.JdbcUtil;

public class GetMessageNoMax {
	
private static GetMessageNoMax instance = new GetMessageNoMax();
	
	public static GetMessageNoMax getInstance() {
		return instance;
	}
	
	private GetMessageNoMax() {
		
	}
	
	private static final int MESSAGE_COUNT_PER_PAGE = 3;
	
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
