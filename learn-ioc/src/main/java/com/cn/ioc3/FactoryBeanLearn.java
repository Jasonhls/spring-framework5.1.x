package com.cn.ioc3;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: helisen
 * @create: 2020-08-10 09:54
 **/
//@Component
public class FactoryBeanLearn implements FactoryBean<FactoryBeanService> {

	@Override
	public FactoryBeanService getObject() throws Exception {
		return new FactoryBeanServiceImpl();
	}

	@Override
	public Class<?> getObjectType() {
		return FactoryBeanService.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
