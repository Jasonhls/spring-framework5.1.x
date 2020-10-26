/*
 * Copyright 2002-2016 the original author or authors.
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

package org.springframework.web.servlet.config.annotation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * A subclass of {@code WebMvcConfigurationSupport} that detects and delegates
 * to all beans of type {@link WebMvcConfigurer} allowing them to customize the
 * configuration provided by {@code WebMvcConfigurationSupport}. This is the
 * class actually imported by {@link EnableWebMvc @EnableWebMvc}.
 *
 * @author Rossen Stoyanchev
 * @since 3.1
 */
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {

	private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();


	/**
	 * @Autowired标注在方法上，会在refresh方法的实例化DelegatingWebMvcConfiguration这个bean的过程中，会被解析，然后执行，
	 * 在AbstractAutowireCapableBeanFactory的doCreateBean方法中先通过AutowiredAnnotationBeanPostProcessor预先解析@Autowired的信息，
	 * 然后通过populate方法中执行依赖注入
	 * 注意：@Autowired和@Value进行依赖注入属性，即可标注在属性上，又可标注在方法上
	 * 因此在DelegatingWebMvcConfiguration的getBean方法中，会调用下面这个方法，下面这个方法会把自定义的配置文件添加到
	 * WebMvcConfigurerComposite类的delegates属性（该属性是List<WebMvcConfigurer>）中了
	 * @param configurers
	 */
	@Autowired(required = false)
	public void setConfigurers(List<WebMvcConfigurer> configurers) {
		if (!CollectionUtils.isEmpty(configurers)) {
			this.configurers.addWebMvcConfigurers(configurers);
		}
	}


	@Override
	protected void configurePathMatch(PathMatchConfigurer configurer) {
		this.configurers.configurePathMatch(configurer);
	}

	@Override
	protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		this.configurers.configureContentNegotiation(configurer);
	}

	@Override
	protected void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		this.configurers.configureAsyncSupport(configurer);
	}

	@Override
	protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		this.configurers.configureDefaultServletHandling(configurer);
	}

	@Override
	protected void addFormatters(FormatterRegistry registry) {
		this.configurers.addFormatters(registry);
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 添加拦截器
		 */
		this.configurers.addInterceptors(registry);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		this.configurers.addResourceHandlers(registry);
	}

	@Override
	protected void addCorsMappings(CorsRegistry registry) {
		this.configurers.addCorsMappings(registry);
	}

	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		this.configurers.addViewControllers(registry);
	}

	@Override
	protected void configureViewResolvers(ViewResolverRegistry registry) {
		this.configurers.configureViewResolvers(registry);
	}

	@Override
	protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		this.configurers.addArgumentResolvers(argumentResolvers);
	}

	@Override
	protected void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		this.configurers.addReturnValueHandlers(returnValueHandlers);
	}

	@Override
	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		this.configurers.configureMessageConverters(converters);
	}

	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		this.configurers.extendMessageConverters(converters);
	}

	@Override
	protected void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		this.configurers.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Override
	protected void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		this.configurers.extendHandlerExceptionResolvers(exceptionResolvers);
	}

	@Override
	@Nullable
	protected Validator getValidator() {
		return this.configurers.getValidator();
	}

	@Override
	@Nullable
	protected MessageCodesResolver getMessageCodesResolver() {
		return this.configurers.getMessageCodesResolver();
	}

}
