package notice.service;

import notice.model.Notice;
import notice.model.NoticeContent;

public class NoticeData {
	
	private Notice notice;
	private NoticeContent content;
	
	public NoticeData(Notice notice, NoticeContent content) {
		this.notice = notice;
		this.content = content;
	}

	public Notice getNotice() {
		return notice;
	}

	public String getContent() {
		return content.getContent();
	}
	
	public String getFileName() {
		return content.getFileName();
	}

}
