package hr.fer.zemris.java.hw13.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Servlet listener that sets creation time of this 
 * application which is used for telling how long
 * is application running.
 * @author Teo Toplak
 *
 */
public class ServletContext implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//nothing
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		arg0.getServletContext().setAttribute("startTime", System.currentTimeMillis());
	}

}
