package notice.service;

import java.util.Map;

public class DeleteRequest {
	private String userId;
	private int noticeNumber;
	private String password;
	public DeleteRequest(String userId, int no,
			String password) {
		super();
		this.userId = userId;
		this.noticeNumber = no;
		this.password = password;
	}
	
	public int getNoticeNumber() {
		return noticeNumber;
	}
	public String getPassword() {
		return password;
	}
	public String getUserId() {
		return userId;
	}

	public void validate(Map<String, Boolean> errors) {

		if (password == null || password.isEmpty()) {
			errors.put("password", true);
		}
	}
	
	
}
