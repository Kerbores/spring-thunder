package club.zhcs.thunder.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.nutz.resource.Scans;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project spring-thunder
 *
 * @file NutzContenxtInitListener.java
 *
 * @description NutzContenxtInitListener.java
 *
 * @time 2016年9月8日 上午10:22:30
 *
 */
public class NutzContenxtInitListener implements ServletContextListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		Scans.me().init(event.getServletContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
