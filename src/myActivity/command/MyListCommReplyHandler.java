package myActivity.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import myActivity.service.GetMessageListService;
import comment.service.MessageListView;
import mvc.controller.CommandHandler;

public class MyListCommReplyHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		User user = (User)req.getSession().getAttribute("authUser");
		String userId = user.getId();
		
		String pageNumberStr = req.getParameter("page");
		int pageNumber = 1;
		if (pageNumberStr != null) {
			pageNumber = Integer.parseInt(pageNumberStr);
		}
		
		GetMessageListService messageListService = GetMessageListService.getInstance();
		
		MessageListView viewData = messageListService.getMessageList(pageNumber, userId);
		req.setAttribute("viewData", viewData);
		
		return "/WEB-INF/view/myActivity/myListCommReply.jsp";
	}
	

}
