package com.cn.configParser.annotatedElementUtils;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-09 15:39
 **/
public class Cat {
    @MyAutowired
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
}
