package club.zhcs.thunder.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Files;
import org.nutz.resource.Scans;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project spring-thunder
 *
 * @file NutzContenxtInitListener.java
 *
 * @description  项目启动的时候做点儿事情,在ioc初始化之前
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
		Scans.me().init(event.getServletContext());// 初始化nutz的Scans

		try {
			if (Files.checkFile("/var/config/log/log4j.properties") != null) {// 找到了线上配置
				PropertyConfigurator.configure(new PropertiesProxy("/var/config/log/log4j.properties").toProperties());// 那么加载线上的配置吧!!!
			}
		} catch (Exception e) {
		}

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
