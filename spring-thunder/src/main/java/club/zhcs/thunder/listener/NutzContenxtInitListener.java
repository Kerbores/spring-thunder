package club.zhcs.thunder.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Files;
import org.nutz.resource.Scans;

/**
 * 
 * @author admin
 *
 * @email kerbores@gmail.com
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
		String logConfigPath = "/var/config/log4j.properties"; // 线上日志配置路径
		try {
			if (Files.checkFile(logConfigPath) != null) {// 找到了线上配置
				PropertyConfigurator.configure(new PropertiesProxy(logConfigPath).toProperties());// 那么加载线上的配置吧!!!
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
