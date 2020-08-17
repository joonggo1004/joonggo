package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.EmailAuthService;
import member.service.SendMailService;
import mvc.controller.CommandHandler;
import util.SHA256;

public class EmailAuthSendHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/member/emailSendSuccess.jsp";
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		User user = (User)request.getSession().getAttribute("authUser");
		if(user == null) {
			response.sendRedirect("/login.do");
			return null;
		}
		EmailAuthService emailAuth = new EmailAuthService();
		if(emailAuth.getEmailChecked(user.getId())){
			response.sendRedirect("/index.jsp");
			return null;
		}
		
		String email = emailAuth.getEmail(user.getId());
		
		SendMailService sendMail = new SendMailService();
		
		// 메시지를 기입
		String host = "http://localhost/prj-joonggo/";
		String from = "wonder.bluedragon@gmail.com";
		String to = email;
		String subject = "이메일 인증 메일입니다.";
		String content = "다음 링크에 접속하여 이메일 확인을 진행하세요." +
			"<a href='" + host + "member/emailCheck.do?code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
		
		int result = sendMail.send(from, to, subject, content);
		if(result != 1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('오류가 발생했습니다. 웹사이트 담당자에게 연락해 주세요.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();		
		    return null;
		}
		return FORM_VIEW;
	}
}
