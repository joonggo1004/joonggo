package prodComment.service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import prodComment.dao.MessageDao;
import prodComment.model.Message;
import jdbc.JdbcUtil;

public class GetMessageNoMax_useless {
	
private static GetMessageNoMax_useless instance = new GetMessageNoMax_useless();
	
	public static GetMessageNoMax_useless getInstance() {
		return instance;
	}
	
	private GetMessageNoMax_useless() {
		
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
	
	public int getMessageParentNo(int productNo) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			
			return messageDao.selectCount(conn, productNo, 0);
			
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			JdbcUtil.close(conn);
		}
		return 0;
	}



}
