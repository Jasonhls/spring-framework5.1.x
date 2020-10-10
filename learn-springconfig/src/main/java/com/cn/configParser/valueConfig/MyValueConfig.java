package com.cn.configParser.valueConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-27 09:30
 **/
@Configuration
@ComponentScan(value = {"com.cn.configParser.valueConfig"})
@PropertySource(value = {"/properties/db.properties"})
public class MyValueConfig {
    /**
     * 要想使用@Value 用${}占位符注入属性，这个bean是必须的，这个就是占位bean,另一种方式是不用value直接用Environment变量直接getProperty('key')
     *
     * 原理：
     * 添加自定义的PropertySourcesPlaceholderConfigurer，会被封装到StringValueResolver对象中，然后添加到AbstractBeanFactory的属性embeddedValueResolvers中
     * 这样在注入属性值的时候，就可以把@Value("${db.driverClass")中对应的配置db.properties中的db.driverClass的值设置进去，因为自定义的db.properties文件中的变量都会
     * 解析存放到容器的environment中，然后通过EnvironmentAware，将容器的environment赋值给自定义的PropertySourcesPlaceholderConfigurer，
     * 这样AbstractBeanFactory的属性embeddedValueResolver中就有environment存放的配置的变量
     * 具体注入属性值的逻辑看MyValueComponent对象的getBean方法中的populateBean方法中的执行AutowiredAnnotationBeanPostProcessor的postProcessPropertyValues方法中
     * @return
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
