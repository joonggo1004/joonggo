package product.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comment.service.GetMessageListService;
import comment.service.MessageListView;
import mvc.controller.CommandHandler;
import product.service.ProductContentNotFoundException;
import product.service.ProductData;
import product.service.ProductNotFoundException;
import product.service.ReadProductService;

public class ReadProductHandler_org implements CommandHandler {
	
	private ReadProductService readService = new ReadProductService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String noVal = req.getParameter("no");
		int productNum = Integer.parseInt(noVal);
		
		String parentNoStr = req.getParameter("parentNo");
		int parentNo = 0;
		
		String pageNumberStr = req.getParameter("page");
		int pageNumber = 1;
		if (pageNumberStr != null) {
			pageNumber = Integer.parseInt(pageNumberStr);
		}
		
		GetMessageListService messageListService = GetMessageListService.getInstance();
		
		if (parentNoStr == null) {
			MessageListView viewData = messageListService.getMessageList(pageNumber, productNum, 0);
			req.setAttribute("viewData", viewData);
		} else {
			MessageListView viewData = messageListService.getMessageList(pageNumber, productNum, 0);
			req.setAttribute("viewData", viewData);
			
			parentNo = Integer.parseInt(parentNoStr);
			MessageListView viewReplyData = messageListService.getMessageList(pageNumber, productNum, parentNo);
			req.setAttribute("viewReplyData", viewReplyData);
		}
		
		
		try {
			ProductData productData = readService.getProduct(productNum, true);
			req.setAttribute("productData", productData);
			return "/WEB-INF/view/product/readProduct.jsp";
		} catch (ProductNotFoundException | ProductContentNotFoundException e) {
			req.getServletContext().log("no product", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			e.printStackTrace();
			return null;
		}
	}
	

}