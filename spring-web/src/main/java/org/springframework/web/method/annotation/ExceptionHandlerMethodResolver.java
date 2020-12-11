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

package org.springframework.web.method.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ExceptionDepthComparator;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.ReflectionUtils.MethodFilter;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Discovers {@linkplain ExceptionHandler @ExceptionHandler} methods in a given class,
 * including all of its superclasses, and helps to resolve a given {@link Exception}
 * to the exception types supported by a given {@link Method}.
 *
 * @author Rossen Stoyanchev
 * @author Juergen Hoeller
 * @since 3.1
 */
public class ExceptionHandlerMethodResolver {

	/**
	 * A filter for selecting {@code @ExceptionHandler} methods.
	 * 带有@ExceptionHandler注解的方法集合，这些方法属于异常处理方法
	 */
	public static final MethodFilter EXCEPTION_HANDLER_METHODS = method ->
			AnnotatedElementUtils.hasAnnotation(method, ExceptionHandler.class);


	//异常类型作为key，处理该类型的异常的方法作为value
	private final Map<Class<? extends Throwable>, Method> mappedMethods = new HashMap<>(16);

	private final Map<Class<? extends Throwable>, Method> exceptionLookupCache = new ConcurrentReferenceHashMap<>(16);


	/**
	 * A constructor that finds {@link ExceptionHandler} methods in the given type.
	 * @param handlerType the type to introspect
	 */
	public ExceptionHandlerMethodResolver(Class<?> handlerType) {
		//从所有带有@ExceptionHandler注解的方法(这些方法属于异常处理方法)中过滤出属于handlerType的方法集合
		for (Method method : MethodIntrospector.selectMethods(handlerType, EXCEPTION_HANDLER_METHODS)) {
			//detectExceptionMappings方法会对method进行过滤，过滤出带有注解@ExceptionHandler，并且@ExceptionHandler注解的value值是Throwable的子类的class的集合
			//以这些过滤出的Throwable的Class为key，该方法method为value，存到ExceptionHandlerMethodResolver的属性mappedMethods中
			for (Class<? extends Throwable> exceptionType : detectExceptionMappings(method)) {
				//以异常Throwable为key，method为value丢入缓存this.mappedMethods中
				addExceptionMapping(exceptionType, method);
			}
		}
	}


	/**
	 * Extract exception mappings from the {@code @ExceptionHandler} annotation first,
	 * and then as a fallback from the method signature itself.
	 */
	@SuppressWarnings("unchecked")
	private List<Class<? extends Throwable>> detectExceptionMappings(Method method) {
		List<Class<? extends Throwable>> result = new ArrayList<>();
		//将方法Method上带有@ExceptionHandler注解的value值取出来转换为class集合，放入result中
		detectAnnotationExceptionMappings(method, result);
		if (result.isEmpty()) {
			//如果result为空，那么取方法method中所有入参中属于Throwable子类的入参Class放入result中
			for (Class<?> paramType : method.getParameterTypes()) {
				if (Throwable.class.isAssignableFrom(paramType)) {
					result.add((Class<? extends Throwable>) paramType);
				}
			}
		}
		if (result.isEmpty()) {
			throw new IllegalStateException("No exception types mapped to " + method);
		}
		return result;
	}

	/**
	 * 将方法Method上带有@ExceptionHandler注解的value值取出来转换为list集合，放入result中
	 * @param method
	 * @param result
	 */
	private void detectAnnotationExceptionMappings(Method method, List<Class<? extends Throwable>> result) {
		ExceptionHandler ann = AnnotatedElementUtils.findMergedAnnotation(method, ExceptionHandler.class);
		Assert.state(ann != null, "No ExceptionHandler annotation");
		result.addAll(Arrays.asList(ann.value()));
	}

	private void addExceptionMapping(Class<? extends Throwable> exceptionType, Method method) {
		//会把异常类型作为key，处理该类型的异常的方法作为value，缓存到mappedMethods中
		Method oldMethod = this.mappedMethods.put(exceptionType, method);
		if (oldMethod != null && !oldMethod.equals(method)) {
			throw new IllegalStateException("Ambiguous @ExceptionHandler method mapped for [" +
					exceptionType + "]: {" + oldMethod + ", " + method + "}");
		}
	}

	/**
	 * Whether the contained type has any exception mappings.
	 */
	public boolean hasExceptionMappings() {
		return !this.mappedMethods.isEmpty();
	}

	/**
	 * Find a {@link Method} to handle the given exception.
	 * Use {@link ExceptionDepthComparator} if more than one match is found.
	 * @param exception the exception
	 * @return a Method to handle the exception, or {@code null} if none found
	 */
	@Nullable
	public Method resolveMethod(Exception exception) {
		return resolveMethodByThrowable(exception);
	}

	/**
	 * Find a {@link Method} to handle the given Throwable.
	 * Use {@link ExceptionDepthComparator} if more than one match is found.
	 * @param exception the exception
	 * @return a Method to handle the exception, or {@code null} if none found
	 * @since 5.0
	 */
	@Nullable
	public Method resolveMethodByThrowable(Throwable exception) {
		Method method = resolveMethodByExceptionType(exception.getClass());
		if (method == null) {
			Throwable cause = exception.getCause();
			if (cause != null) {
				method = resolveMethodByExceptionType(cause.getClass());
			}
		}
		return method;
	}

	/**
	 * Find a {@link Method} to handle the given exception type. This can be
	 * useful if an {@link Exception} instance is not available (e.g. for tools).
	 * @param exceptionType the exception type
	 * @return a Method to handle the exception, or {@code null} if none found
	 */
	@Nullable
	public Method resolveMethodByExceptionType(Class<? extends Throwable> exceptionType) {
		/**
		 *this.exceptionLookupCache是在ExceptionHandlerExceptionResolver的afterPropertiesSet方法中创建ExceptionHandlerMethodResolver对象的时候，
		 * 在ExceptionHandlerMethodResolver的构造函数ExceptionHandlerMethodResolver(Class<?> handlerType)中
		 * 会向this.exceptionLookupCache中添加缓存 ，以@ExceptionHandler注解中的value的异常Class为key，value为@ExceptionHandler注解标注的方法
		 */

		Method method = this.exceptionLookupCache.get(exceptionType);
		if (method == null) {
			//获取异常类型的处理方法，如果有配置，就会返回处理这个异常类型的方法
			method = getMappedMethod(exceptionType);
			this.exceptionLookupCache.put(exceptionType, method);
		}
		return method;
	}

	/**
	 * Return the {@link Method} mapped to the given exception type, or {@code null} if none.
	 */
	@Nullable
	private Method getMappedMethod(Class<? extends Throwable> exceptionType) {
		List<Class<? extends Throwable>> matches = new ArrayList<>();
		for (Class<? extends Throwable> mappedException : this.mappedMethods.keySet()) {
			if (mappedException.isAssignableFrom(exceptionType)) {
				matches.add(mappedException);
			}
		}
		if (!matches.isEmpty()) {
			matches.sort(new ExceptionDepthComparator(exceptionType));
			return this.mappedMethods.get(matches.get(0));
		}
		else {
			return null;
		}
	}

}
