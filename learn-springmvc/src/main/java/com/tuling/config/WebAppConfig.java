package com.tuling.config;

import com.tuling.interceptor.TuLingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

/**
 * @description: 子容器的配置类
 * @author: Administrator
 * @create: 2020/3/30 14:49
 **/
@Configuration
@ComponentScan(basePackages = {"com.tuling"},includeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class, Controller.class})
},useDefaultFilters = false)
public class WebAppConfig implements WebMvcConfigurer {

	/**
	 * 配置拦截器
	 * @return
	 */
	@Bean
	public TuLingInterceptor tuLingInterceptor(){
		return new TuLingInterceptor();
	}

	/**
	 * 文件上传下载的组件
	 * @return
	 */
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxUploadSize(1024*1024*10);
		return multipartResolver;
	}

	/**
	 * 注册处理国际化资源的组件
	 * @return
	 */
/*	@Bean
	public AcceptHeaderLocaleResolver localeResolver(){
		AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
		return acceptHeaderLocaleResolver;
	}*/

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(tuLingInterceptor()).addPathPatterns("/*");
	}

	/**
	 * 方法实现说明：配置视图解析器
	 * @return
	 */
	@Bean
	public InternalResourceViewResolver internalResourceViewResolver(){
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setSuffix(".jsp");
		viewResolver.setPrefix("/WEB-INF/jsp/");
		return viewResolver;
	}

	/**
	 * 消息转换器
	 * @param converters initially an empty list of converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(new MappingJackson2HttpMessageConverter());
	}
}
