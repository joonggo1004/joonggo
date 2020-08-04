package member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import jdbc.JdbcUtil;
import member.model.Member;

public class MemberDao {

	public Member selectById(Connection conn, String id) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			
			pstmt = conn.prepareStatement(
					"select * from member where memberid = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			Member member = null;
			if (rs.next()) {
				member = new Member(
						rs.getString("memberid"),
						rs.getString("password"),
						rs.getString("name"),
						rs.getString("phone"),
						rs.getString("email"),
						toDate(rs.getTimestamp("regdate")));
			}
			return member;
		} finally {
			JdbcUtil.close(rs, pstmt);
		}
	}

	private Date toDate(Timestamp date) {
		return date == null? null: new Date(date.getTime());
	}
	
	public void insert(Connection conn, Member mem) throws SQLException {
		PreparedStatement pstmt = null;
		try {
			
			pstmt = conn.prepareStatement("insert into member values (?,?,?,?,?,?)");
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getPassword());
			pstmt.setString(3, mem.getName());
			pstmt.setString(4, mem.getPhone());
			pstmt.setString(5, mem.getEmail());
			pstmt.setTimestamp(6, new Timestamp(mem.getRegDate().getTime()));
			pstmt.executeUpdate();
		} finally {
			JdbcUtil.close(pstmt);
		}
		/*
		try (PreparedStatement pstmt = conn.prepareStatement("insert into member values (?,?,?,?,?)")) {
			pstmt.setString(1, mem.getId());
			pstmt.setString(2, mem.getPassword());
			pstmt.setString(3, mem.getName());
			pstmt.setString(4, mem.getPhone());
			pstmt.setString(5, mem.getEmail());
			pstmt.setTimestamp(6, new Timestamp(mem.getRegDate().getTime()));
			pstmt.executeUpdate();
		}
		*/
	}
	
	public void update(Connection conn, Member member) throws SQLException {
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update member set password = ?, name = ?, phone = ?, email = ? where memberid = ?")) {
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getId());
			pstmt.executeUpdate();
		}
	}

}
