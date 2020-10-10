package com.cn.configParser.propertySourceConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-22 14:37
 **/
@Configuration
@PropertySource(value = {"/properties/application.properties"})
public class EnvironmentPropertySourceConfig {
    @Resource
    private Environment environment;

    @Bean
    public TestBean testBean() {
        TestBean tb = new TestBean();
        tb.setName(environment.getProperty("com.spring.name"));
        tb.setAge(Integer.valueOf(environment.getProperty("com.spring.age")));
        return tb;
    }
}
