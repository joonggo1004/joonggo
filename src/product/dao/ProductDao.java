package product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jdbc.JdbcUtil;
import product.model.Product;
import product.model.Writer;

public class ProductDao {

	public int delete(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM product " + " WHERE product_no=?")) {
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}

	public Product selectById(Connection conn, int no) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("SELECT * FROM product " + " WHERE product_no=?");
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			Product product = null;
			String product_no = "product_no";
			if (rs.next()) {
				product = convertProduct(rs, product_no);
			}
			return product;
		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}

	public void increaseReadCount(Connection conn, int no) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("UPDATE product SET read_cnt=read_cnt+1 " + " WHERE product_no=? ")) {
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
		}
	}

	public int selectCount(Connection conn, String search) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("SELECT count(*) FROM product p, product_content c " +
					"WHERE p.product_no = c.product_no AND CONCAT(title, content) LIKE ? ");
			pstmt.setString(1, "%" + search + "%");
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}

			return 0;
		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}

	public List<Product> select(Connection conn, int startRow, int size, String search) throws SQLException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("SELECT p.product_no, writer_id, writer_name, title, regdate, moddate, read_cnt FROM product p, product_content c " + 
					"WHERE p.product_no = c.product_no AND CONCAT(title, content) LIKE ? " +	
					"ORDER BY product_no DESC LIMIT ?, ? ");
			pstmt.setString(1, "%" + search + "%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();
			String product_no = "p.product_no";
			List<Product> result = new ArrayList<>();
			while (rs.next()) {
				result.add(convertProduct(rs, product_no));
			}
			return result;

		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}

	private Product convertProduct(ResultSet rs, String product_no) throws SQLException {
		return new Product(rs.getInt(product_no), new Writer(rs.getString("writer_id"), rs.getString("writer_name")),
				rs.getString("title"), toDate(rs.getTimestamp("regdate")), toDate(rs.getTimestamp("moddate")),
				rs.getInt("read_cnt"));
	}

	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	public Product insert(Connection conn, Product product) throws SQLException {

		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement("INSERT INTO " + " product " + " (writer_id, writer_name, title, "
					+ "  regdate, moddate, read_cnt) " + " VALUES (?, ?, ?, ?, ?, 0) ");
			pstmt.setString(1, product.getWriter().getId());
			pstmt.setString(2, product.getWriter().getName());
			pstmt.setString(3, product.getTitle());
			pstmt.setTimestamp(4, toTimestamp(product.getRegDate()));
			pstmt.setTimestamp(5, toTimestamp(product.getModifiedDate()));
			int insertedCount = pstmt.executeUpdate();

			if (insertedCount > 0) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_id() ");

				if (rs.next()) {
					Integer newNum = rs.getInt(1);
					return new Product(newNum, product.getWriter(), product.getTitle(), product.getRegDate(),
							product.getModifiedDate(), 0);
				}
			}

			return null;
		} finally {
			JdbcUtil.close(rs, stmt, pstmt);
		}
	}

	private Timestamp toTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	public int update(Connection conn, int no, String title) throws SQLException {
		try (PreparedStatement pstmt = conn
				.prepareStatement("UPDATE product SET title=?, moddate=now()" + " WHERE product_no=?")) {
			pstmt.setString(1, title);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
}
