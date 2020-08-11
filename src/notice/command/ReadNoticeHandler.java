package notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.service.GetMessageListService;
import comment.service.GetMessageNoMax;
import comment.service.MessageListView;
import mvc.controller.CommandHandler;
import notice.service.NoticeContentNotFoundException;
import notice.service.NoticeData;
import notice.service.NoticeNotFoundException;
import notice.service.ReadNoticeService;

public class ReadNoticeHandler implements CommandHandler {
	
	private ReadNoticeService readService = new ReadNoticeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("no");
		int noticeNum = Integer.parseInt(noVal);
		
		String pageNumberStr = req.getParameter("page");
		int pageNumber = 1;
		if (pageNumberStr != null) {
			pageNumber = Integer.parseInt(pageNumberStr);
		}
		
		GetMessageListService messageListService = GetMessageListService.getInstance();
		/*
		if (parentNoStr == null) {
			MessageListView viewData = messageListService.getMessageList(pageNumber, noticeNum, 0);
			req.setAttribute("viewData", viewData);
		} else {
			MessageListView viewData = messageListService.getMessageList(pageNumber, noticeNum, 0);
			req.setAttribute("viewData", viewData);
			
			parentNo = Integer.parseInt(parentNoStr);
			MessageListView viewReplyData = messageListService.getMessageList(pageNumber, noticeNum, parentNo);
			req.setAttribute("viewReplyData", viewReplyData);
		}
		*/
		
		GetMessageNoMax getMaxNo = GetMessageNoMax.getInstance();
		int maxMessageNo = getMaxNo.getMessageNoMax();
		
		MessageListView[] viewData = new MessageListView[maxMessageNo];
		
		for (int i=0; i<maxMessageNo; i++ ) {
			viewData[i] = messageListService.getMessageList(pageNumber, noticeNum, i);
		}
		req.setAttribute("viewData", viewData);
		
		
		
		try {
			NoticeData noticeData = readService.getNotice(noticeNum, true);
			req.setAttribute("noticeData", noticeData);
			
			return "/WEB-INF/view/notice/readNotice.jsp";
		} catch (NoticeNotFoundException | NoticeContentNotFoundException e) {
			req.getServletContext().log("no notice", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			e.printStackTrace();
			return null;
		}
	}
	

}
