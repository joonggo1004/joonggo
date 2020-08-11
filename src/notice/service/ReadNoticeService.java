package notice.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import notice.dao.NoticeContentDao;
import notice.dao.NoticeDao;
import notice.model.Notice;
import notice.model.NoticeContent;

public class ReadNoticeService {
	
	private NoticeDao noticeDao = new NoticeDao();
	private NoticeContentDao contentDao = new NoticeContentDao();
	
	public NoticeData getNotice(int noticeNum, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			Notice notice = noticeDao.selectById(conn, noticeNum);
			if (notice == null) {
				throw new NoticeNotFoundException();
			}
			NoticeContent content = contentDao.selectById(conn, noticeNum);
			if (content == null) {
				throw new NoticeContentNotFoundException();
			}
			if (increaseReadCount) {
				noticeDao.increaseReadCount(conn, noticeNum);
			}
			return new NoticeData(notice, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
