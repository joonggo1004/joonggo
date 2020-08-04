package product.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import product.dao.ProductContentDao;
import product.dao.ProductDao;
import product.model.Product;

public class ModifyProductService {
	
	private ProductDao productDao = new ProductDao();
	private ProductContentDao contentDao = new ProductContentDao();
	
	public void modify(ModifyRequest modReq) {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Product product = productDao.selectById(conn, modReq.getProductNumber());
			if (product == null) {
				throw new ProductNotFoundException();
			}
			if (!canModify(modReq.getUserId(), product)) {
				throw new PermissionDeniedException();
			}
			productDao.update(conn, modReq.getProductNumber(), modReq.getTitle());
			contentDao.update(conn, modReq.getProductNumber(), modReq.getContent());
			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean canModify(String modfyingUserId, Product product) {
		return product.getWriter().getId().contentEquals(modfyingUserId);
	}

}
