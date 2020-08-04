package product.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.User;
import member.service.InvalidPasswordException;
import mvc.controller.CommandHandler;
import product.service.DeleteProductService;
import product.service.DeleteRequest;
import product.service.PermissionDeniedException;
import product.service.ProductData;
import product.service.ProductNotFoundException;
import product.service.ReadProductService;

public class DeleteProductHandler implements CommandHandler {

	private static String FORM_VIEW = "/WEB-INF/view/deleteForm.jsp";
	private DeleteProductService deleteService = new DeleteProductService();
	private ReadProductService readService = new ReadProductService();

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

			ProductData productData = readService.getProduct(no,
					false);
			User authUser = (User) req.getSession()
					.getAttribute("authUser");

			if (!canModify(authUser, productData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}

			DeleteRequest delReq = new DeleteRequest(
					authUser.getId(), no, null);

			req.setAttribute("delReq", delReq);
			return FORM_VIEW;
		} catch (ProductNotFoundException e) {
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
			return "/WEB-INF/view/deleteSuccess.jsp";
		} catch (ProductNotFoundException e) {
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
			ProductData productData) {

		String writerId = productData.getProduct().getWriter()
				.getId();
		return authUser.getId().equals(writerId);
	}

}
