package myActivity.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import product.service.ProductPage;
import auth.service.User;
import mvc.controller.CommandHandler;
import myActivity.service.MyListProductService;

public class MyListProductHandler implements CommandHandler {
	
	private MyListProductService listService = new MyListProductService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		User user = (User)req.getSession().getAttribute("authUser");
		String userId = user.getId();
		String pageNoVal = req.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		ProductPage productPage = listService.getProductPage(pageNo, userId);
		req.setAttribute("productPage", productPage);
		return "/WEB-INF/view/myActivity/myListProduct.jsp";
	}
	
	

}
