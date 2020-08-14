package notice.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import notice.dao.NoticeDao;
import notice.model.Notice;

public class ListNoticeService {
	
	private NoticeDao noticeDao = new NoticeDao();
	private int size = 10;
	
	public NoticePage getNoticePage(int pageNum, String search) {
		try (Connection conn = ConnectionProvider.getConnection()){
			int total = noticeDao.selectCount(conn, search);
			List<Notice> content = noticeDao.select(conn, (pageNum-1)*size, size, search);
			return new NoticePage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
