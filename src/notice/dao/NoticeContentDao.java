package notice.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import notice.model.NoticeContent;

public class NoticeContentDao {
	public int delete(Connection conn, int no)
			throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM notice_content "
						+ " WHERE notice_no=?")) {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}

	public NoticeContent selectById(Connection conn, int no)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(
					"SELECT * FROM notice_content"
							+ " WHERE notice_no=?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			NoticeContent content = null;
			if (rs.next()) {
				content = new NoticeContent(
						rs.getInt("notice_no"),
						rs.getString("content"), 
						rs.getString("file_name"));

			}

			return content;

		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}

	public int update(Connection conn, int no, String content, String fileName)
			throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"UPDATE notice_content SET content=?, file_name=?"
						+ "WHERE notice_no=?")) {
			pstmt.setString(1, content);
			pstmt.setString(2, fileName);
			pstmt.setInt(3, no);
			return pstmt.executeUpdate();
		}
	}

	public NoticeContent insert(Connection conn,
			NoticeContent content) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO "
					+ " notice_content "
					+ " (notice_no, content, file_name) "
					+ " VALUES "
					+ " (?, ?, ?) ");

			pstmt.setLong(1, content.getNumber());
			pstmt.setString(2, content.getContent());
			pstmt.setString(3, content.getFileName());
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				return content;
			} else {
				return null;
			}

		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}
