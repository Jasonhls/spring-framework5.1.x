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

package org.springframework.aop.framework.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * Default implementation of the {@link AdvisorAdapterRegistry} interface.
 * Supports {@link org.aopalliance.intercept.MethodInterceptor},
 * {@link org.springframework.aop.MethodBeforeAdvice},
 * {@link org.springframework.aop.AfterReturningAdvice},
 * {@link org.springframework.aop.ThrowsAdvice}.
 *
 * @author Rod Johnson
 * @author Rob Harrop
 * @author Juergen Hoeller
 */
@SuppressWarnings("serial")
public class DefaultAdvisorAdapterRegistry implements AdvisorAdapterRegistry, Serializable {

	private final List<AdvisorAdapter> adapters = new ArrayList<>(3);


	/**
	 * Create a new DefaultAdvisorAdapterRegistry, registering well-known adapters.
	 * DefaultAdvisorAdapterRegistry初始化了三个增强适配器
	 */
	public DefaultAdvisorAdapterRegistry() {
		registerAdvisorAdapter(new MethodBeforeAdviceAdapter());
		registerAdvisorAdapter(new AfterReturningAdviceAdapter());
		registerAdvisorAdapter(new ThrowsAdviceAdapter());
	}


	@Override
	public Advisor wrap(Object adviceObject) throws UnknownAdviceTypeException {
		//如果要封装的对象本身就是Advisor类型的，那么无须再做过多处理
		if (adviceObject instanceof Advisor) {
			return (Advisor) adviceObject;
		}
		//到这里表明不是Advisor类型了，如果也不是Advice类型，直接抛出异常
		//因为此封装方法只对Advisor与Advice两种类型的数据有效，如果不是将不能封装
		if (!(adviceObject instanceof Advice)) {
			throw new UnknownAdviceTypeException(adviceObject);
		}
		Advice advice = (Advice) adviceObject;
		if (advice instanceof MethodInterceptor) {
			// So well-known it doesn't even need an adapter.
			//如果是MethodInterceptor类型则使用DefaultPointcutAdvisor封装
			return new DefaultPointcutAdvisor(advice);
		}
		//如果存在Advisor的适配器那么也同样需要进行封装
		for (AdvisorAdapter adapter : this.adapters) {
			// Check that it is supported.
			if (adapter.supportsAdvice(advice)) {
				return new DefaultPointcutAdvisor(advice);
			}
		}
		throw new UnknownAdviceTypeException(advice);
	}

	@Override
	public MethodInterceptor[] getInterceptors(Advisor advisor) throws UnknownAdviceTypeException {
		List<MethodInterceptor> interceptors = new ArrayList<>(3);
		/**
		 * 如果是@Transactional注解，生成的cglib动态代理对象执行方法，会走到这里，
		 * 对应的advisor为BeanFactoryTransactionAttributeSourceAdvisor对象，
		 * 该对象的属性advice值为spring中的TransactionInterceptor实例对象，原因如下：
		 *     如果是java配置方式：在@EnableTransactionManagement注解中，
		 * 有一个selector为TransactionManagementConfigurationSelector，该类的import方法引入的配置类包括
		 * ProxyTransactionManagementConfiguration，而该配置类中，引入了BeanFactoryTransactionAttributeSourceAdvisor
		 * 这个bean实例，并且把spring中TransactionInterceptor实例设置到该类的advice属性中了
		 *     如果是xml方式，原理参考BeanFactoryTransactionAttributeSourceAdvice的getAdvice()方法的逻辑，该逻辑里面会getBean(this.adviceBeanName, Advisor.class)，
		 * 其中的this.adviceBeanName是在TxNamespaceHandler的init方法中定义的AnnotationDrivenBeanDefinitionParser的parse方法中，定义
		 * BeanFactoryTransactionAttributeSourceAdvisor的beanDefinition中，将adviceBeanName作为key，value为TransactionInterceptor的beanName设置到
		 * 该bean定义的propertySource中，在getBean的populate方法中会填充属性的，即会把beanDefinition的propertySource进行赋值。
		 */
		Advice advice = advisor.getAdvice();
		if (advice instanceof MethodInterceptor) {
			interceptors.add((MethodInterceptor) advice);
		}
		/**
		 * 遍历增强适配器
		 */
		for (AdvisorAdapter adapter : this.adapters) {
			if (adapter.supportsAdvice(advice)) {
				//获取方法拦截放入集合中
				interceptors.add(adapter.getInterceptor(advisor));
			}
		}
		if (interceptors.isEmpty()) {
			throw new UnknownAdviceTypeException(advisor.getAdvice());
		}
		return interceptors.toArray(new MethodInterceptor[0]);
	}

	@Override
	public void registerAdvisorAdapter(AdvisorAdapter adapter) {
		this.adapters.add(adapter);
	}

}
