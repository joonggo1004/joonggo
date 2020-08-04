package product.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.connection.ConnectionProvider;
import product.dao.ProductContentDao;
import product.dao.ProductDao;
import product.model.Product;
import product.model.ProductContent;

public class ReadProductService {
	
	private ProductDao productDao = new ProductDao();
	private ProductContentDao contentDao = new ProductContentDao();
	
	public ProductData getProduct(int productNum, boolean increaseReadCount) {
		try (Connection conn = ConnectionProvider.getConnection()) {
			Product product = productDao.selectById(conn, productNum);
			if (product == null) {
				throw new ProductNotFoundException();
			}
			ProductContent content = contentDao.selectById(conn, productNum);
			if (content == null) {
				throw new ProductContentNotFoundException();
			}
			if (increaseReadCount) {
				productDao.increaseReadCount(conn, productNum);
			}
			return new ProductData(product, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
