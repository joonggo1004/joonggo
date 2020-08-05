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
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		ProductPage productPage = listService.getProductPage(pageNo);
		req.setAttribute("productPage", productPage);
		return "/WEB-INF/view/product/listProduct.jsp";
	}
	
	

}
