package member.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.DuplicateIdException;
import member.service.ModifyAccService;
import member.service.ModifyRequest;
import mvc.controller.CommandHandler;

public class ModifyAccHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/modifyAccForm.jsp";
	private ModifyAccService modifyService = new ModifyAccService();

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
		
		User authUser = (User) req.getSession().getAttribute("authUser");
		
		ModifyRequest modifyReq = new ModifyRequest();
		modifyReq.setId(authUser.getId());
		modifyReq.setPassword(req.getParameter("password"));
		modifyReq.setConfirmPassword(req.getParameter("confirmPassword"));
		modifyReq.setName(authUser.getName());
		modifyReq.setPhone(req.getParameter("phone"));
		modifyReq.setEmail(req.getParameter("email"));
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		
		modifyReq.validate(errors);
		
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			
			modifyService.update(modifyReq);
			return "/WEB-INF/view/modifyAccSuccess.jsp";
		} catch (DuplicateIdException e) {
			e.printStackTrace();
			errors.put("duplicateId", Boolean.TRUE);
			return FORM_VIEW;
		}
		
	}
	
	

}
