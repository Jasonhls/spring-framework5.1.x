package com.cn.beanAndFactoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-29 10:09
 **/
@Component
public class MyFactoryBean implements FactoryBean<Car> {
    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        car.setBrandName("宝马");
        car.setColor("white");
        car.setPrice(300000);
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
