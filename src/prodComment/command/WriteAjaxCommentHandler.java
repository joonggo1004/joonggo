package prodComment.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.controller.CommandHandler;
import prodComment.model.Message;
import prodComment.service.WriteMessageService;

public class WriteAjaxCommentHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		Message message = new Message();
		HttpSession session = request.getSession();
		
		int productNo = Integer.parseInt(request.getParameter("productNo"));
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String body = request.getParameter("message");
		int result = 0;
		
		if (name == null || body == null || name.isEmpty() || body.isEmpty()) {
			errors.put("message", Boolean.TRUE);
		} else {
			message.setProductNo(productNo);
			message.setGuestId(id);
			message.setGuestName(name);
			message.setMessage(body);
			
			WriteMessageService service = WriteMessageService.getInstance();
			boolean success = service.write(message);
			if(success == true) result = 1;
			else result = -1;
			if (success) {
				session.setAttribute("info", "메시지가 등록되었습니다.");
				//response.sendRedirect(request.getContextPath()+"/comment/listComment.do");
			} else {
				session.setAttribute("info", "메시지 등록에 실패하였습니다.");
			}
		}
		
		response.getWriter().write(result+"");
		//return "/product/read.do?no="+productNo;
		return null;
	}

}
