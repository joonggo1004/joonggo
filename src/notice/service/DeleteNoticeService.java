package notice.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;
import member.service.InvalidPasswordException;
import notice.dao.NoticeContentDao;
import notice.dao.NoticeDao;
import notice.model.Notice;

public class DeleteNoticeService {
	private NoticeDao noticeDao = new NoticeDao();
	private NoticeContentDao contentDao = new NoticeContentDao();
	private MemberDao memberDao = new MemberDao();

	public void delete(DeleteRequest delReq) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Notice notice = noticeDao.selectById(conn,
					delReq.getNoticeNumber());
			Member member = memberDao.selectById(conn,
					delReq.getUserId());

			if (notice == null) {
				throw new NoticeNotFoundException();
			}

			if (!canModify(delReq.getUserId(), notice)) {
				throw new PermissionDeniedException();
			}

			if (!delReq.getPassword()
					.equals(member.getPassword())) {
				throw new InvalidPasswordException();
			}

			noticeDao.delete(conn, delReq.getNoticeNumber());
			contentDao.delete(conn, delReq.getNoticeNumber());
			conn.commit();
		} catch (SQLException | PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvalidPasswordException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean canModify(String modfyingUserId,
			Notice notice) {
		return notice.getWriter().getId()
				.equals(modfyingUserId);
	}
}
