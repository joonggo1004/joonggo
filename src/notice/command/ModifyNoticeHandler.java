package notice.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import auth.service.User;
import mvc.controller.CommandHandler;
import notice.service.ModifyNoticeService;
import notice.service.ModifyRequest;
import notice.service.NoticeData;
import notice.service.NoticeNotFoundException;
import notice.service.PermissionDeniedException;
import notice.service.ReadNoticeService;
import notice.service.WriteFileService;

public class ModifyNoticeHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/notice/modifyForm.jsp";
	
	private ReadNoticeService readService = new ReadNoticeService();
	private ModifyNoticeService modifyService = new ModifyNoticeService();
	private WriteFileService writeFile = new WriteFileService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("GET")) {
			return processForm(req, res);
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(req, res);
		} else {
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}

	private String processForm(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);
			NoticeData noticeData = readService.getNotice(no, false);
			User authUser = (User) req.getSession().getAttribute("authUser");
			if (!canModify(authUser, noticeData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, noticeData.getNotice().getTitle(), noticeData.getContent(), noticeData.getFileName());
			
			req.setAttribute("modReq", modReq);
			return FORM_VIEW;
		} catch (NoticeNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	private boolean canModify(User authUser, NoticeData noticeData) {
		String writerId = noticeData.getNotice().getWriter().getId();
		return authUser.getId().contentEquals(writerId);
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		User authUser = (User) req.getSession().getAttribute("authUser");
		String noticeNoStr = req.getParameter("no");
		int noticeNo = Integer.parseInt(noticeNoStr);
		
		String orgFileName = readService.getNotice(noticeNo, false).getFileName();
		
		Part filePart = req.getPart("file1");
		String fileName = filePart.getSubmittedFileName();
		//fileName = fileName == null ? "" : fileName;
		if(fileName == null || fileName.isEmpty()) {
			fileName = orgFileName;
		}
		
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), noticeNo, req.getParameter("title"), req.getParameter("content"), fileName);
		req.setAttribute("modReq", modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		modReq.validate(errors);
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		if ((fileName != orgFileName) && !(fileName == null || fileName.isEmpty() || filePart.getSize() == 0)) {
			writeFile.write(filePart, noticeNo);
		}
		try {
			modifyService.modify(modReq);
			return "/WEB-INF/view/notice/modifySuccess.jsp";
		} catch (NoticeNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}

}
