package member.command;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.EmailAuthService;
import member.service.SendMailService;
import mvc.controller.CommandHandler;
import util.SHA256;

public class EmailCheckHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/member/joinSuccess.jsp";
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=utf-8");
		User user = (User)request.getSession().getAttribute("authUser");
		if(user == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인 상태에서 이메일 인증을 클릭해 주시기 바랍니다.');");
			script.println("location.href = '/prj-joonggo/login.do'");
			script.println("</script>");
			script.close();
			return null;
		}
		String code = request.getParameter("code");
		if(code == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('문제가 있습니다. 다시 한번 이메일 인증을 클릭하시거나 인증메일을 다시 받아 주시기 바랍니다.');");
			script.println("location.href = '/prj-joonggo/index.jsp'");
			script.println("</script>");
			script.close();
			return null;
		}
		EmailAuthService emailAuth = new EmailAuthService();
		String email = emailAuth.getEmail(user.getId());
		
		boolean rightCode = (new SHA256().getSHA256(email).equals(code)) ? true : false;
		if(rightCode == true) {
			emailAuth.setEmailChecked(user.getId());
			user = emailAuth.getUser(user.getId());
			request.getSession().setAttribute("authUser", user);
			return FORM_VIEW;
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 코드입니다. 다시 한번 이메일 인증을 클릭하시거나 인증메일을 다시 받아 주시기 바랍니다.');");
			script.println("location.href = '/prj-joonggo/index.jsp'");
			script.println("</script>");
			script.close();		
			return null;
		}
	}
}
