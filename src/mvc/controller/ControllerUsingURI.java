package mvc.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ControllerUsingURI
 */
//@WebServlet("/ControllerUsingURI")
public class ControllerUsingURI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Map<String, CommandHandler> map = new HashMap<>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControllerUsingURI() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	// TODO Auto-generated method stub
    	super.init();
    	FileInputStream fis = null;
    	try {
    		String filePath = getInitParameter("configFile");
    		//System.out.println("filePath: "+filePath); //filePath: /WEB-INF/commandHandlerURI.properties
    		String realPath = getServletContext().getRealPath(filePath);
    		//System.out.println("realPath: "+realPath); //realPath: C:\workspace_jee\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\myjsp\WEB-INF\commandHandlerURI.properties    		
    		
    		fis = new FileInputStream(realPath);
    		Properties props = new Properties();
    		props.load(fis);
    		
    		Enumeration<String> keys = (Enumeration<String>) props.propertyNames();
    		while(keys.hasMoreElements()) {
    			String key = keys.nextElement();
    			String className = props.getProperty(key);
    			
    			//System.out.println(key+":"+className);
    			
    			Class<CommandHandler> clazz = (Class<CommandHandler>)Class.forName(className);
    			
    			CommandHandler comm = clazz.newInstance();
    			
    			map.put(key, comm);
    		}
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		if (fis != null) {
    			try {
    				fis.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    			
    		}
    	}
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 어떤 경로로 왔는 지 파악
		// ex) /a.do, /b.do, /c.do
		String requestUri = request.getRequestURI();
		//System.out.println("requestURI: "+requestUri); //requestURI: /myjsp/a/some.do
		
		// localhost:8080/contextRoot/some/a.do
		String contextPath = request.getContextPath(); 
		//System.out.println("contextPath: "+contextPath); //contextPath: /myjsp
		int startIndex = requestUri.indexOf(contextPath);
		
		String command = requestUri.substring(startIndex + contextPath.length());
		//System.out.println("command: "+command); //command: /a/some.do
		
		CommandHandler com = map.get(command);
		String view = null;
		try {
			view = com.process(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//request.setAttribute("result", view);
		if (view != null) {
			request.getRequestDispatcher(view).forward(request, response);
		}
	}

}
