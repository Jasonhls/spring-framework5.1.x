package com.cn.beanAndFactoryBean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-29 11:10
 **/
@Component
public class MyBean implements InitializingBean {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @PostConstruct
    public void setParams() {
        this.name = "tom";
        this.age = 25;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.name = "hls";
        this.age = 29;
    }
}
