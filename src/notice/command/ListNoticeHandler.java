package notice.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;
import notice.service.ListNoticeService;
import notice.service.NoticePage;

public class ListNoticeHandler implements CommandHandler {
	
	private ListNoticeService listService = new ListNoticeService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String search = "";
		if(req.getParameter("search") != null) {
			search = req.getParameter("search");
		}
		
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		
		NoticePage noticePage = listService.getNoticePage(pageNo, search);
		req.setAttribute("noticePage", noticePage);
		return "/WEB-INF/view/notice/listNotice.jsp";
	}
	
	

}
