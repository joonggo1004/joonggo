package comment.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comment.model.Message;

import jdbc.JdbcUtil;

public class MessageDao {
	
	private static MessageDao messageDao = new MessageDao();
	public static MessageDao getInstance() {
		return messageDao;
	}
	
	private MessageDao() {}
	
	public int insert(Connection conn, Message message) throws SQLException {
		// 1.클래스 로딩 : listener에서 이미 로딩됨
		// 2.연결 생성 : 누군가 생성한 것을 파리키터로 받음
		// 3.statement 생성 : 메소드 내
		// 4.쿼리 실행 : 메소드 내
		// 5.결과 처리 : 호출한 곳에서 처리
		// 6.자원 닫기 : 
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"insert into comment "+
					"(product_no, guest_id, guest_name, message, regdate) values (?,?,?,?,now())"); //p.448
			pstmt.setInt(1, message.getProductNo());
			pstmt.setString(2, message.getGuestId());
			pstmt.setString(3, message.getGuestName());
			pstmt.setString(4, message.getMessage());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt); //p.434
		}
	}
	
	public Message select(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from comment where comment_no = ?");
			pstmt.setInt(1, messageId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return makeMessageFromResultSet(rs);
			} else {
				return null;
			}
		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}
	
	private Message makeMessageFromResultSet(ResultSet rs) throws SQLException {
		Message message = new Message();
		message.setId(rs.getInt("message_id"));
		message.setProductNo(rs.getInt("product_no"));
		message.setGuestId(rs.getString("guest_id"));
		message.setGuestName(rs.getString("guest_name"));
		message.setMessage(rs.getString("message"));
		message.setRegDate(toDate(rs.getTimestamp("regdate")));
		
		return message;
	}
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public int selectCount(Connection conn, String productNo) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select count(*) from comment where product_no ="+ productNo +" group by product_no");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs,stmt);
		}
	}
	
	public List<Message> selectList(Connection conn, int firstRow, int endRow, String productNo) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from comment "+
					"where product_no = ? order by message_id desc limit ?, ?");
			pstmt.setInt(1, Integer.parseInt(productNo));
			pstmt.setInt(2, firstRow-1);
			pstmt.setInt(3, endRow-firstRow+1);
			rs = pstmt.executeQuery();
			/*
			if (rs.next()) {
				List<Message> messageList = new ArrayList<Message>();
				do {
					messageList.add(makeMessageFromResultSet(rs));
				} while (rs.next());
				return messageList;
			} else {
				return Collections.emptyList();
			}*/
			
			List<Message> messageList = new ArrayList<Message>();
			while(rs.next()) {
				messageList.add(makeMessageFromResultSet(rs));
			}
			return messageList;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int delete(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"delete from comment  where message_id = ?");
			pstmt.setInt(1, messageId);
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}


}
