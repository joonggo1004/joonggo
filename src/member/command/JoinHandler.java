package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.DuplicateIdException;
import member.service.JoinRequest;
import member.service.JoinService;
import mvc.controller.CommandHandler;

public class JoinHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/member/joinForm.jsp";
	private JoinService joinService = new JoinService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) {
		
		JoinRequest joinReq = new JoinRequest();
		String id = req.getParameter("id");
		String password = req.getParameter("password");
		joinReq.setId(id);
		joinReq.setPassword(password);
		joinReq.setConfirmPassword(req.getParameter("confirmPassword"));
		joinReq.setName(req.getParameter("name"));
		joinReq.setPhone(req.getParameter("phone"));
		joinReq.setEmail(req.getParameter("email"));
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		joinReq.validate(errors);
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			
			joinService.join(joinReq);
			User user = new User(id, password);
			req.getSession().setAttribute("authUser", user);
			return "/WEB-INF/view/member/joinSuccess.jsp";
		} catch (DuplicateIdException e) {
			e.printStackTrace();
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
		
	}
	
	

}
