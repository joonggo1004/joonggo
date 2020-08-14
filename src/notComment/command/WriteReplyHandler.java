package notComment.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.controller.CommandHandler;
import notComment.model.Message;
import notComment.service.WriteMessageService;

public class WriteReplyHandler implements CommandHandler{
	
	private static final String FORM_VIEW = "/WEB-INF/view/notice/readNotice.jsp";

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
		String commentPageNoStr = request.getParameter("page");
		int commentPageNo = 1;
		if (commentPageNoStr != null) {
			commentPageNo = Integer.parseInt(commentPageNoStr);
		}
		
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		Message message = new Message();
		HttpSession session = request.getSession();		
		
		int index = Integer.parseInt(request.getParameter("index"));
		request.setAttribute("index", index);
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		int parentNo = Integer.parseInt(request.getParameter("parentNo"));
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String body = request.getParameter("message");
		
		if (name == null || body == null || name.isEmpty() || body.isEmpty()) {
			errors.put("message", Boolean.TRUE);
		} else {
			message.setNoticeNo(noticeNo);
			message.setParentNo(parentNo);
			message.setGuestId(id);
			message.setGuestName(name);
			message.setMessage(body);
			
			WriteMessageService service = WriteMessageService.getInstance();
			boolean success = service.write(message);
			
			if (success) {
				session.setAttribute("info", "메시지가 등록되었습니다.");
				//response.sendRedirect(request.getContextPath()+"/comment/listComment.do");
				//request.getRequestDispatcher("/notice/read.do?no="+noticeNo+"&parentNo="+parentNo).forward(request, response);
			} else {
				session.setAttribute("info", "메시지 등록에 실패하였습니다.");
			}
		}
		
		return "/notice/read.do?no="+noticeNo+"&&page="+commentPageNo;
	}

}
