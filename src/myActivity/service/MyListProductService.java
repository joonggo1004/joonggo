package myActivity.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import myActivity.dao.MyProductDao;
import product.model.Product;
import product.service.ProductPage;

public class MyListProductService {
	
	private MyProductDao productDao = new MyProductDao();
	private int size = 10;
	
	public ProductPage getProductPage(int pageNum, String userId) {
		try (Connection conn = ConnectionProvider.getConnection()){
			int total = productDao.selectCount(conn, userId);
			List<Product> content = productDao.select(conn, (pageNum-1)*size, size, userId);
			return new ProductPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
