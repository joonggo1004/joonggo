package product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import product.model.ProductContent;

public class ProductContentDao {
	public int delete(Connection conn, int no)
			throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("DELETE FROM product_content "
						+ " WHERE product_no=?")) {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}

	public ProductContent selectById(Connection conn, int no)
			throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(
					"SELECT * FROM product_content"
							+ " WHERE product_no=?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			ProductContent content = null;
			if (rs.next()) {
				content = new ProductContent(
						rs.getInt("product_no"),
						rs.getString("content"), 
						rs.getString("file_name"));

			}

			return content;

		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}

	public int update(Connection conn, int no, String content)
			throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"UPDATE product_content SET content=?"
						+ "WHERE product_no=?")) {
			pstmt.setString(1, content);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}

	public ProductContent insert(Connection conn,
			ProductContent content) throws SQLException {

		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO "
					+ " product_content "
					+ " (product_no, content, file_name) "
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
