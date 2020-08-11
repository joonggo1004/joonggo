package notice.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import notice.dao.NoticeContentDao;
import notice.dao.NoticeDao;
import notice.model.Notice;

public class ModifyNoticeService {
	
	private NoticeDao noticeDao = new NoticeDao();
	private NoticeContentDao contentDao = new NoticeContentDao();
	
	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Notice notice = noticeDao.selectById(conn, modReq.getNoticeNumber());
			if (notice == null) {
				throw new NoticeNotFoundException();
			}
			if (!canModify(modReq.getUserId(), notice)) {
				throw new PermissionDeniedException();
			}
			noticeDao.update(conn, modReq.getNoticeNumber(), modReq.getTitle());
			contentDao.update(conn, modReq.getNoticeNumber(), modReq.getContent(), modReq.getFileName());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean canModify(String modfyingUserId, Notice notice) {
		return notice.getWriter().getId().contentEquals(modfyingUserId);
	}

}
