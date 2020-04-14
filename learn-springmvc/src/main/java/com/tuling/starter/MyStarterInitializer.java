package com.tuling.starter;

import com.tuling.config.RootConfig;
import com.tuling.config.WebAppConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/30 13:58
 **/
public class MyStarterInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	/**
	 * 方法实现说明：IOC 父容器的启动类
	 * @return
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{RootConfig.class};
	}

	/**
	 * 方式实现说明 IOC子容器配置  web容器配置
	 * @return
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{WebAppConfig.class};
	}

	/**
	 * 方法实现说明
	 * @return 我们前端控制器DispatcherServlet的拦截路径
	 */
	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}
}
