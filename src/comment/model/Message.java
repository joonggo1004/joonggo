package comment.model;

import java.util.Date;

public class Message {
	
	private int id;
	private int productNo;
	private String guestId;
	private String guestName;
	private String message;
	private Date regDate;
	
	@Override
	public String toString() {
		return "Message [id=" + id + ", productNo=" + productNo + ", guestName=" + guestName + ", message=" + message
				+ "]";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductNo() {
		return productNo;
	}
	public void setProductNo(int productNo) {
		this.productNo = productNo;
	}
	public String getGuestId() {
		return guestId;
	}
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
}
