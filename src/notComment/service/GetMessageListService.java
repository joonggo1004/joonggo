package notComment.service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import notComment.dao.MessageDao;
import notComment.model.Message;

public class GetMessageListService {
	
private static GetMessageListService instance = new GetMessageListService();
	
	public static GetMessageListService getInstance() {
		return instance;
	}
	
	private GetMessageListService() {
		
	}
	
	private static final int MESSAGE_COUNT_PER_PAGE = 3;
	
	public MessageListView getMessageList(int commentPageNo, int noticeNo, int parentNo) {
		Connection conn = null;
		int currentPageNumber = commentPageNo;
		try {
			conn = ConnectionProvider.getConnection();
			MessageDao messageDao = MessageDao.getInstance();
			
			int messageTotalCount = messageDao.selectCount(conn, noticeNo, parentNo);
			
			List<Message> messageList = null;
			int firstRow = 0;
			int endRow = 0;
			
			if (messageTotalCount > 0) {
				if (parentNo == 0) {
					firstRow = (commentPageNo-1) * MESSAGE_COUNT_PER_PAGE + 1;
					endRow = firstRow + MESSAGE_COUNT_PER_PAGE - 1;
				} else {
					firstRow = 1;
					endRow = messageTotalCount;
				}
				messageList = messageDao.selectList(conn, firstRow, endRow, noticeNo, parentNo);
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
