package contactUs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import contactUs.model.Report;
import jdbc.JdbcUtil;

public class ReportDao {

	public int insert(Connection conn, Report report) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO report"
					+ " (user_id, report_title, report_content)"
					+ " VALUES (?, ?, ?) ");

			pstmt.setString(1, report.getUserId());
			pstmt.setString(2, report.getReportTitle());
			pstmt.setString(3, report.getReportContent());
			return pstmt.executeUpdate();

		} finally {
			JdbcUtil.close(pstmt);
		}
	}
}
