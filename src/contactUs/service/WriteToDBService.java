package contactUs.service;

import java.sql.Connection;
import java.sql.SQLException;

import contactUs.dao.ReportDao;
import contactUs.model.Report;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteToDBService {
	private ReportDao reportDao = new ReportDao();
	
	public int write(Report report) {
		Connection conn = null;
		int result = 0;
		try {
			conn = ConnectionProvider.getConnection();
			
			int result1 = reportDao.insert(conn, report);
			if (result1 != 1) {
				throw new RuntimeException("fail to insert product");
			}
			result = 1;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			result = -1;
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
		return result;
	}

}
