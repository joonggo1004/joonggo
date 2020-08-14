package notComment.dao;

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
import notComment.model.Message;

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
					"insert into not_comment "+
					"(notice_no, parent_no, guest_id, guest_name, message, regdate) values (?,?,?,?,?,now())"); //p.448
			pstmt.setInt(1, message.getNoticeNo());
			pstmt.setInt(2, message.getParentNo());
			pstmt.setString(3, message.getGuestId());
			pstmt.setString(4, message.getGuestName());
			pstmt.setString(5, message.getMessage());
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt); //p.434
		}
	}
	
	public Message selectByMsgNo(Connection conn, int messageNo) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from not_comment where message_no = ?");
			pstmt.setInt(1, messageNo);
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
	public Message selectByParentNo(Connection conn, int parentNo) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from not_comment where parent_no = ? order by message_no desc");
			pstmt.setInt(1, parentNo);
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
		message.setNo(rs.getInt("message_no"));
		message.setNoticeNo(rs.getInt("notice_no"));
		message.setParentNo(rs.getInt("parent_no"));
		message.setGuestId(rs.getString("guest_id"));
		message.setGuestName(rs.getString("guest_name"));
		message.setMessage(rs.getString("message"));
		message.setRegDate(toDate(rs.getTimestamp("regdate")));
		
		return message;
	}
	private Date toDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}
	
	public int selectCount(Connection conn, int noticeNo, int parentNo) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select count(*) from not_comment where notice_no = ? and parent_no = ?");
			pstmt.setInt(1, noticeNo);
			pstmt.setInt(2, parentNo);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			} else return 0;
		} finally {
			JdbcUtil.close(rs,pstmt);
		}
	}
	
	public int selectMaxMessageNo(Connection conn) throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(message_no) from not_comment");
			rs.next();
			return rs.getInt(1);
		} finally {
			JdbcUtil.close(rs,stmt);
		}
	}
	
	public List<Message> selectList(Connection conn, int firstRow, int endRow, int noticeNo, int parentNo) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(
					"select * from not_comment where notice_no = ? and parent_no = ? order by message_no desc limit ?, ?");
			pstmt.setInt(1, noticeNo);
			pstmt.setInt(2, parentNo);
			pstmt.setInt(3, firstRow-1);
			pstmt.setInt(4, endRow-firstRow+1);
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
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int delete(Connection conn, int messageId) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(
					"delete from not_comment  where message_no = ?");
			pstmt.setInt(1, messageId);
			return pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
	}


}
