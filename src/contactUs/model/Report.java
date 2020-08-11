package contactUs.model;

public class Report {
	String userId;
	String reportTitle;
	String reportContent;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}
	public String getReportContent() {
		return reportContent;
	}
	public void setReportContent(String reportContent) {
		this.reportContent = reportContent;
	}
	public Report() {
	}
	public Report(String userId, String reportTitle, String reportContent) {
		super();
		this.userId = userId;
		this.reportTitle = reportTitle;
		this.reportContent = reportContent;
	}
}
