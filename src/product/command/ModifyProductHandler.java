package product.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import auth.service.User;
import mvc.controller.CommandHandler;
import product.service.ModifyProductService;
import product.service.ModifyRequest;
import product.service.PermissionDeniedException;
import product.service.ProductData;
import product.service.ProductNotFoundException;
import product.service.ReadProductService;
import product.service.WriteFileService;

public class ModifyProductHandler implements CommandHandler {
	
	private static final String FORM_VIEW = "/WEB-INF/view/product/modifyForm.jsp";
	
	private ReadProductService readService = new ReadProductService();
	private ModifyProductService modifyService = new ModifyProductService();
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
			ProductData productData = readService.getProduct(no, false);
			User authUser = (User) req.getSession().getAttribute("authUser");
			if (!canModify(authUser, productData)) {
				res.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, productData.getProduct().getTitle(), productData.getContent(), productData.getFileName());
			
			req.setAttribute("modReq", modReq);
			return FORM_VIEW;
		} catch (ProductNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	private boolean canModify(User authUser, ProductData productData) {
		String writerId = productData.getProduct().getWriter().getId();
		return authUser.getId().contentEquals(writerId);
	}

	private String processSubmit(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		Part filePart = req.getPart("file1");
		String fileName = filePart.getSubmittedFileName();
		fileName = fileName == null ? "" : fileName;
		
		User authUser = (User) req.getSession().getAttribute("authUser");
		String noVal = req.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, req.getParameter("title"), req.getParameter("content"), fileName);
		req.setAttribute("modReq", modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		req.setAttribute("errors", errors);
		modReq.validate(errors);
		if (!errors.isEmpty()) {
			return FORM_VIEW;
		}
		if (!(fileName == null || fileName.isEmpty() || filePart.getSize() == 0)) {
			writeFile.write(filePart, no);
		}
		try {
			modifyService.modify(modReq);
			return "/WEB-INF/view/product/modifySuccess.jsp";
		} catch (ProductNotFoundException e) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}

}
