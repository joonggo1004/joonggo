package product.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import product.dao.ProductContentDao;
import product.dao.ProductDao;
import product.model.Product;
import product.model.ProductContent;

public class WriteProductService {
	
	private ProductDao productDao = new ProductDao();
	private ProductContentDao contentDao = new ProductContentDao();
	
	public Integer write(WriteRequest req) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Product product = toProduct(req);
			Product savedProduct = productDao.insert(conn, product);
			if (savedProduct == null) {
				throw new RuntimeException("fail to insert product");
			}
			ProductContent content = new ProductContent(savedProduct.getNumber(), req.getContent(), req.getFileName());
			ProductContent savedContent = contentDao.insert(conn, content);
			if (savedContent == null) {
				throw new RuntimeException("fail to insert product_content");
			}
			
			conn.commit();
			
			return savedProduct.getNumber();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (RuntimeException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private Product toProduct(WriteRequest req) {
		Date now = new Date();
		return new Product(null, req.getWriter(), req.getTitle(), now, now, 0);
	}

}
