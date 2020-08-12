package prodComment.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.controller.CommandHandler;
import prodComment.model.Message;
import prodComment.service.WriteMessageService;

public class WriteCommentHandler implements CommandHandler{
	
	private static final String FORM_VIEW = "/WEB-INF/view/product/readProduct.jsp";

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		} else if (request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		} else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		Message message = new Message();
		HttpSession session = request.getSession();
		
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String body = request.getParameter("message");
		
		if (name == null || body == null || name.isEmpty() || body.isEmpty()) {
			errors.put("message", Boolean.TRUE);
		} else {
			message.setProductNo(productNo);
			message.setGuestId(id);
			message.setGuestName(name);
			message.setMessage(body);
			
			WriteMessageService service = WriteMessageService.getInstance();
			boolean success = service.write(message);
			
			if (success) {
				session.setAttribute("info", "메시지가 등록되었습니다.");
				//response.sendRedirect(request.getContextPath()+"/comment/listComment.do");
			} else {
				session.setAttribute("info", "메시지 등록에 실패하였습니다.");
			}
		}
		
		return "/product/read.do?no="+productNo;
	}

}
