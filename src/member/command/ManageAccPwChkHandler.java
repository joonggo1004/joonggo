package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.User;
import member.service.PasswordCheckService;
import mvc.controller.CommandHandler;

public class ManageAccPwChkHandler implements CommandHandler {
	
	private static final String FORM_VIEW1 = "/WEB-INF/view/passCheckForm.jsp";
	private static final String FORM_VIEW2 = "/WEB-INF/view/manageAccView.jsp";
	private PasswordCheckService passCheckService = new PasswordCheckService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW1;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {

		User authUser = (User) req.getSession().getAttribute("authUser");
		String password = req.getParameter("password");
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		if (password == null || password.isEmpty())
			errors.put("password", Boolean.TRUE);
		if (!errors.isEmpty()) {
			return FORM_VIEW1;
		}
		
		try {
			if(passCheckService.passwordCheck(authUser.getId(), password)) {
				req.getRequestDispatcher(FORM_VIEW2).forward(req, res);
			} else {
				errors.put("pwNotMatch", Boolean.TRUE);
				return FORM_VIEW1;
			}
			
			return null;
		} catch (LoginFailException e) {
			e.printStackTrace();
			errors.put("pwNotMatch", Boolean.TRUE);
			return FORM_VIEW1;
		}

	}

}
