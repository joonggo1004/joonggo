package product.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jdbc.connection.ConnectionProvider;
import product.dao.ProductDao;
import product.model.Product;

public class ListProductService {
	
	private ProductDao productDao = new ProductDao();
	private int size = 10;
	
	public ProductPage getProductPage(int pageNum) {
		try (Connection conn = ConnectionProvider.getConnection()){
			int total = productDao.selectCount(conn);
			List<Product> content = productDao.select(conn, (pageNum-1)*size, size);
			return new ProductPage(total, pageNum, size, content);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
