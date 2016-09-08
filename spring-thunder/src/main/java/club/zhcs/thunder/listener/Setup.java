package club.zhcs.thunder.listener;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project spring-thunder
 *
 * @file Setup.java
 *
 * @description Setup.java
 *
 * @time 2016年9月8日 下午12:31:36
 *
 */
public class Setup implements ApplicationListener<ContextRefreshedEvent> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org.
	 * springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Dao dao = event.getApplicationContext().getBean(Dao.class);
		Daos.createTablesInPackage(dao, "zlub.zhcs", false);//初始化一下
	}

}
