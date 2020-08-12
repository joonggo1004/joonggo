package product.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;
import product.service.ListProductService;
import product.service.ProductPage;

public class ListProductHandler implements CommandHandler {
	
	private ListProductService listService = new ListProductService();

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
		
		ProductPage productPage = listService.getProductPage(pageNo, search);
		req.setAttribute("productPage", productPage);
		return "/WEB-INF/view/product/listProduct.jsp";
	}
	
	

}
