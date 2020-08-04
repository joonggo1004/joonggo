package product.service;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;
import member.dao.MemberDao;
import member.model.Member;
import member.service.InvalidPasswordException;
import product.dao.ProductContentDao;
import product.dao.ProductDao;
import product.model.Product;

public class DeleteProductService {
	private ProductDao productDao = new ProductDao();
	private ProductContentDao contentDao = new ProductContentDao();
	private MemberDao memberDao = new MemberDao();

	public void delete(DeleteRequest delReq) {
		Connection conn = null;

		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);

			Product product = productDao.selectById(conn,
					delReq.getProductNumber());
			Member member = memberDao.selectById(conn,
					delReq.getUserId());

			if (product == null) {
				throw new ProductNotFoundException();
			}

			if (!canModify(delReq.getUserId(), product)) {
				throw new PermissionDeniedException();
			}

			if (!delReq.getPassword()
					.equals(member.getPassword())) {
				throw new InvalidPasswordException();
			}

			productDao.delete(conn, delReq.getProductNumber());
			contentDao.delete(conn, delReq.getProductNumber());
			conn.commit();
		} catch (SQLException | PermissionDeniedException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (InvalidPasswordException e) {
			JdbcUtil.rollback(conn);
			e.printStackTrace();
			throw e;
		} finally {
			JdbcUtil.close(conn);
		}
	}

	private boolean canModify(String modfyingUserId,
			Product product) {
		return product.getWriter().getId()
				.equals(modfyingUserId);
	}
}
