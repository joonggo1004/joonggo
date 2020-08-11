package notice.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.InvalidPasswordException;
import mvc.controller.CommandHandler;
import notice.service.DeleteNoticeService;
import notice.service.DeleteRequest;
import notice.service.NoticeData;
import notice.service.NoticeNotFoundException;
import notice.service.PermissionDeniedException;
import notice.service.ReadNoticeService;

public class DeleteNoticeHandler implements CommandHandler {

	private static String FORM_VIEW = "/WEB-INF/view/notice/deleteForm.jsp";
	private DeleteNoticeService deleteService = new DeleteNoticeService();
	private ReadNoticeService readService = new ReadNoticeService();

	@Override
	public String process(HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		String method = req.getMethod();

		switch (method) {

		case "GET":
			return processForm(req, res);

		case "POST":
			return processSubmit(req, res);

		default:
			res.sendError(
					HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;

		}

	}

	private String processForm(HttpServletRequest req,
			HttpServletResponse res) throws IOException {
		try {
			String noVal = req.getParameter("no");
			int no = Integer.parseInt(noVal);

			NoticeData noticeData = readService.getNotice(no,
					false);
			User authUser = (User) req.getSession()
					.getAttribute("authUser");

			if (!canModify(authUser, noticeData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}

			DeleteRequest delReq = new DeleteRequest(
					authUser.getId(), no, null);

			req.setAttribute("delReq", delReq);
			return FORM_VIEW;
		} catch (NoticeNotFoundException e) {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return null;
	}

	private String processSubmit(HttpServletRequest req,
			HttpServletResponse res) throws IOException {

		User authUser = (User) req.getSession()
				.getAttribute("authUser");
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);

		DeleteRequest delReq = new DeleteRequest(
				authUser.getId(), no,
				req.getParameter("password"));
		req.setAttribute("delReq", delReq);

		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		delReq.validate(errors);

		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}

		try {
			deleteService.delete(delReq);
			return "/WEB-INF/view/notice/deleteSuccess.jsp";
		} catch (NoticeNotFoundException e) {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (PermissionDeniedException e) {
			e.printStackTrace();
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
		} catch (InvalidPasswordException e) {
			errors.put("invalidPassword", true);
			return FORM_VIEW;
		}

		return null;

	}

	private boolean canModify(User authUser,
			NoticeData noticeData) {

		String writerId = noticeData.getNotice().getWriter()
				.getId();
		return authUser.getId().equals(writerId);
	}

}
