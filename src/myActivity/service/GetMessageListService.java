package myActivity.service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import myActivity.dao.MessageDao;
import prodComment.model.Message;
import prodComment.service.MessageListView;
import jdbc.connection.ConnectionProvider;
import jdbc.JdbcUtil;

public class GetMessageListService {
	
private static GetMessageListService instance = new GetMessageListService();
	
	public static GetMessageListService getInstance() {
		return instance;
	}
	
	private GetMessageListService() {
		
	}
	
	private static final int MESSAGE_COUNT_PER_PAGE = 3;
	
	public MessageListView getMessageList(int pageNumber, String userId) {
		Connection conn = null;
		int currentPageNumber = pageNumber;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			
			int messageTotalCount = messageDao.selectCount(conn, userId);
			
			List<Message> messageList = null;
			int firstRow = 0;
			int endRow = 0;
			if (messageTotalCount > 0) {
				firstRow = (pageNumber-1) * MESSAGE_COUNT_PER_PAGE + 1;
				endRow = firstRow + MESSAGE_COUNT_PER_PAGE - 1;
				messageList = messageDao.selectList(conn, firstRow, endRow, userId);
			} else {
				currentPageNumber = 0;
				messageList = Collections.emptyList();
			}
			return new MessageListView(messageList, messageTotalCount, currentPageNumber,
					MESSAGE_COUNT_PER_PAGE, firstRow, endRow);
		} catch (Exception e) {
			e.printStackTrace();;
		} finally {
			JdbcUtil.close(conn);
		}
		return null;
	}


}
