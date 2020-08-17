package prodComment.command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mvc.controller.CommandHandler;
import prodComment.model.Message;
import prodComment.service.GetMessageListService;
import prodComment.service.GetMessageNoMax;
import prodComment.service.MessageListView;

public class ListAjaxCommentHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		String productNoStr = request.getParameter("productNo");
		int productNo = 0;
		if(productNoStr != null) {
			productNo = Integer.parseInt(productNoStr);
		} else {
			errors.put("productNo", Boolean.TRUE);
		}
		
		String pageNoStr = request.getParameter("pageNo");
		int pageNo = 1;
		if (pageNoStr != null) {
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		if(!errors.isEmpty()) return "/product/readAjax.do";
		
		GetMessageListService messageListService = GetMessageListService.getInstance();
		
		GetMessageNoMax getMaxNo = GetMessageNoMax.getInstance();
		int maxMessageNo = getMaxNo.getMessageNoMax();
		//System.out.println("maxMessageNo:"+maxMessageNo);
		MessageListView[] arrayProdReplyData = new MessageListView[maxMessageNo];
		
		for (int i=0; i<maxMessageNo; i++ ) {
			arrayProdReplyData[i] = messageListService.getMessageList(pageNo, productNo, i);
		}
		//System.out.println("size"+arrayProdReplyData[0].getMessageList().size());
		request.getSession().setAttribute("arrayProdReplyData", arrayProdReplyData);
		/*for(int i=0; i<arrayProdReplyData[0].getMessageList().size();i++) {
			System.out.println("arrayProdReplyData[0]:"+arrayProdReplyData[0].getMessageList().get(i).getMessage());
		}*/
		response.setContentType("text/html;charset=UTF-8");
		String result = getJSON(arrayProdReplyData);
		//System.out.println("result"+result);
		response.getWriter().write(result);
		
		//return "/product/read.do?no="+productNo+"&page="+pageNo;
		return null;
	}
	
	private String getJSON(MessageListView[] replyData) {
		List<Message> list = replyData[0].getMessageList();
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		for(int i = 0; i < list.size(); i++) {
			result.append("[{\"value\": \"" + list.get(i).getGuestName() + "\"},");
			result.append("{\"value\": \"" + list.get(i).getRegDate() + "\"},");
			result.append("{\"value\": \"" + list.get(i).getMessage() + "\"}]");
			if(i != list.size()-1) result.append(",");
		}
		result.append("]}");
		return result.toString();
	}

}
