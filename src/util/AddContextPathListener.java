package util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class AddContextPathListener
 *
 */
//@WebListener
public class AddContextPathListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public AddContextPathListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
        ServletContext application = sce.getServletContext();
        String path = application.getContextPath();
        
    	application.setAttribute("ctxPath", path);
    }
	
}
