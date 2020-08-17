package product.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;
import prodComment.service.GetMessageListService;
import prodComment.service.GetMessageNoMax;
import prodComment.service.MessageListView;
import product.service.ProductContentNotFoundException;
import product.service.ProductData;
import product.service.ProductNotFoundException;
import product.service.ReadProductService;

public class ReadAjaxProductHandler implements CommandHandler {
	
	private ReadProductService readService = new ReadProductService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String productNoStr = req.getParameter("no");
		int productNo = Integer.parseInt(productNoStr);
		
		String commentPageNoStr = req.getParameter("page");
		int commentPageNo = 1;
		if (commentPageNoStr != null) {
			commentPageNo = Integer.parseInt(commentPageNoStr);
		}
		
		GetMessageListService messageListService = GetMessageListService.getInstance();
		/*
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
		*/
		
		GetMessageNoMax getMaxNo = GetMessageNoMax.getInstance();
		int maxMessageNo = getMaxNo.getMessageNoMax();
		
		MessageListView[] arrayProdReplyData = new MessageListView[maxMessageNo];
		
		for (int i=0; i<maxMessageNo; i++ ) {
			arrayProdReplyData[i] = messageListService.getMessageList(commentPageNo, productNo, i);
		}
		req.setAttribute("arrayProdReplyData", arrayProdReplyData);
		
		
		
		try {
			ProductData productData = readService.getProduct(productNo, true);
			req.setAttribute("productData", productData);
			
			return "/WEB-INF/view/product/readAjaxProduct.jsp";
		} catch (ProductNotFoundException | ProductContentNotFoundException e) {
			req.getServletContext().log("no product", e);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			e.printStackTrace();
			return null;
		}
	}
	

}
