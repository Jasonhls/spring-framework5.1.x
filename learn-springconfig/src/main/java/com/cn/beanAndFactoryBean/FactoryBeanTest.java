package com.cn.beanAndFactoryBean;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-29 10:12
 **/
public class FactoryBeanTest {
    @Test
    public void testMyFactoryBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        Car car = (Car) context.getBean("myFactoryBean");
        System.out.println(car.getBrandName() + "; " + car.getColor() + "; " + car.getPrice());
    }

    @Test
    public void testMyBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(FactoryBeanConfig.class);
        MyBean myBean = (MyBean) context.getBean("myBean");
        System.out.println(myBean.getName() + "; " + myBean.getAge());
    }
}
