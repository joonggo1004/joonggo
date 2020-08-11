package notice.model;

public class NoticeContent {
	
	private Integer number;
	private String content;
	private String fileName;
	
	public NoticeContent(Integer number, String content) {
		this(number, content, "");
	}

	public NoticeContent(Integer number, String content, String fileName) {
		this.number = number;
		this.content = content;
		this.fileName = fileName;
	}

	public Integer getNumber() {
		return number;
	}

	public String getContent() {
		return content;
	}
	
	public String getFileName() {
		return fileName;
	}


}
