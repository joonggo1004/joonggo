package prodComment.service;

import java.sql.Connection;

import jdbc.connection.ConnectionProvider;
import prodComment.dao.MessageDao;
import prodComment.model.Message;
import jdbc.JdbcUtil;

public class WriteMessageService {
	
private static WriteMessageService instance = new WriteMessageService();
	
	public static WriteMessageService getInstance() {
		return instance;
	}
	
	private WriteMessageService() {	
	}
	
	public boolean write(Message message) {
		/*
		if (message.getGuestName() == null || message.getGuestName().isEmpty()) {
			throw new IllegalArgumentException("invalid guest name");
		} else if (message.getPassword() == null || message.getPassword().isEmpty()) {
			throw new IllegalArgumentException("암호가 지정되어 있지 않음");
		}
		*/
		
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			messageDao.insert(conn, message);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			JdbcUtil.close(conn);
		}
		
		return true;
	}

}
