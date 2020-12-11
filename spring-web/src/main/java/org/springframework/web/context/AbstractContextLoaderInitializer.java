/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.lang.Nullable;
import org.springframework.web.WebApplicationInitializer;

/**
 * Convenient base class for {@link WebApplicationInitializer} implementations
 * that register a {@link ContextLoaderListener} in the servlet context.
 *
 * <p>The only method required to be implemented by subclasses is
 * {@link #createRootApplicationContext()}, which gets invoked from
 * {@link #registerContextLoaderListener(ServletContext)}.
 *
 * @author Arjen Poutsma
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.2
 */
public abstract class AbstractContextLoaderInitializer implements WebApplicationInitializer {

	/** Logger available to subclasses. */
	protected final Log logger = LogFactory.getLog(getClass());


	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		registerContextLoaderListener(servletContext);
	}

	/**
	 * Register a {@link ContextLoaderListener} against the given servlet context. The
	 * {@code ContextLoaderListener} is initialized with the application context returned
	 * from the {@link #createRootApplicationContext()} template method.
	 * @param servletContext the servlet context to register the listener against
	 */
	protected void registerContextLoaderListener(ServletContext servletContext) {
		/**
		 * 创建我们的根容器，此时为空容器对象，没有组件，根容器类为AnnotationConfigWebApplicationContext
		 * 如果为xml方式，这里返回rootAppContext为空，也就不会创建ContextLoaderListener
		 */
		WebApplicationContext rootAppContext = createRootApplicationContext();
		if (rootAppContext != null) {
			/**
			 * <listener>
			 *     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
			 * </listener>
			 *
			 * <context-param>
			 *     <param-name>contextConfigLocation</param-name>
			 *     <param-value>/WEB-INF/app-context.xml</param-value>
			 * </context-param>
			 * 创建一个监听器对象
			 * ContextLoaderListener实现了javax.servlet包中的ServletContextListener接口，
			 * 容器(比如tomcat)启动的时候，会调用ServletContextListener的contextInitialized(ServletContextEvent sce)方法，具体可以参考tomcat启动源码，
			 * 因此这里执行完之后，就会执行这里创建ContextLoaderListener(其父类是ServletContextListener)的contextInitialized方法
			 * 如果为java配置方式，这里的ContextLoaderListener构造器会把上面得到的context赋值给其父类ContextLoader的属性context
			 */
			ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
			/**
			 * 第一步：获取根应用的getRootApplicationContextInitializers()，现在得到的是空的
			 * 第二步：把初始化器设置到监听器中
			 */
			listener.setContextInitializers(getRootApplicationContextInitializers());
			/**
			 * 把监听器加入上下文中
			 */
			servletContext.addListener(listener);
		}
		else {
			logger.debug("No ContextLoaderListener registered, as " +
					"createRootApplicationContext() did not return an application context");
		}
	}

	/**
	 * Create the "<strong>root</strong>" application context to be provided to the
	 * {@code ContextLoaderListener}.
	 * <p>The returned context is delegated to
	 * {@link ContextLoaderListener#ContextLoaderListener(WebApplicationContext)} and will
	 * be established as the parent context for any {@code DispatcherServlet} application
	 * contexts. As such, it typically contains middle-tier services, data sources, etc.
	 * @return the root application context, or {@code null} if a root context is not
	 * desired
	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer
	 */
	@Nullable
	protected abstract WebApplicationContext createRootApplicationContext();

	/**
	 * Specify application context initializers to be applied to the root application
	 * context that the {@code ContextLoaderListener} is being created with.
	 * @since 4.2
	 * @see #createRootApplicationContext()
	 * @see ContextLoaderListener#setContextInitializers
	 */
	@Nullable
	protected ApplicationContextInitializer<?>[] getRootApplicationContextInitializers() {
		return null;
	}

}
