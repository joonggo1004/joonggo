package product.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;
import prodComment.service.GetMessageListService;
import prodComment.service.GetMessageNoMax_useless;
import prodComment.service.MessageListView;
import product.service.ProductContentNotFoundException;
import product.service.ProductData;
import product.service.ProductNotFoundException;
import product.service.ReadProductService;

public class ReadProductHandler_useless implements CommandHandler {
	
	private ReadProductService readService = new ReadProductService();
	private static final int MESSAGE_COUNT_PER_PAGE = 3;

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String productNoStr = req.getParameter("no");
		int productNo = Integer.parseInt(productNoStr);
		
		String pageNumberStr = req.getParameter("page");
		int pageNumber = 1;
		if (pageNumberStr != null) {
			pageNumber = Integer.parseInt(pageNumberStr);
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
		
		GetMessageNoMax_useless getMaxNo = GetMessageNoMax_useless.getInstance();
		int maxMessageNo = getMaxNo.getMessageNoMax();
		
		MessageListView[] arrayProdReplyData = new MessageListView[maxMessageNo];
		
		int messageParentTotalCount = getMaxNo.getMessageParentNo(productNo);
		int firstRow = 0;
		int endRow = 0;
		if (messageParentTotalCount > 0) {
			firstRow = (pageNumber-1) * MESSAGE_COUNT_PER_PAGE + 1;
			endRow = firstRow + MESSAGE_COUNT_PER_PAGE - 1;
		}
		for (int i=0; i<maxMessageNo; i++ ) {
			arrayProdReplyData[i] = messageListService.getMessageList(pageNumber, productNo, i);
		}
		req.setAttribute("arrayProdReplyData", arrayProdReplyData);
		
		
		
		try {
			ProductData productData = readService.getProduct(productNo, true);
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
