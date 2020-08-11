package notice.service;

import java.util.Map;

public class ModifyRequest {
	
	private String userId;
	private int noticeNumber;
	private String title;
	private String content;
	private String fileName;
	
	public ModifyRequest(String userId, int noticeNumber, String title, String content, String fileName) {
		this.userId = userId;
		this.noticeNumber = noticeNumber;
		this.title = title;
		this.content = content;
		this.fileName = fileName;
	}

	public String getUserId() {
		return userId;
	}

	public int getNoticeNumber() {
		return noticeNumber;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void validate(Map<String, Boolean> errors) {
		if (title == null || title.trim().isEmpty()) {
			errors.put("title", Boolean.TRUE);
		}
	}

}
