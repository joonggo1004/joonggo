package contactUs.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import contactUs.model.Report;
import contactUs.service.SendMailService;
import contactUs.service.WriteToDBService;
import mvc.controller.CommandHandler;

public class ContactUsHandler implements CommandHandler{
	
	private static final String FORM_VIEW = "/WEB-INF/view/contactUs/reportForm.jsp";
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		User user = (User)request.getSession().getAttribute("authUser");
	
		String reportTitle = null;
		String reportContent = null;
		if(request.getParameter("reportTitle") != null){
			reportTitle = (String) request.getParameter("reportTitle");
		}
		if(request.getParameter("reportContent") != null){
			reportContent = (String) request.getParameter("reportContent");
		}	
		if (reportTitle == null || reportTitle.equals("") || reportContent == null || reportContent.equals("")) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return null;
		}
		Report report = new Report(user.getId(), reportTitle, reportContent);
		
		SendMailService sendMail = new SendMailService();
		WriteToDBService write = new WriteToDBService();
		
		// 메시지를 기입
		String from = "wonder.bluedragon@gmail.com";
		String to = "kkjc1004@naver.com";
		String subject = "문의 및 신고 메일입니다.";
		
		int result1 = sendMail.send(from, to, subject, report);
		int result2 = write.write(report);
		
		if(result1 == -1 || result2 == -1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('오류가 발생했습니다..');");
			script.println("history.back();");
			script.println("</script>");
			script.close();	
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('정상적으로 접수되었습니다..');");
			script.println("location.href='index.jsp'");
			script.println("</script>");
			script.close();
		}
		return null;
	}
}
