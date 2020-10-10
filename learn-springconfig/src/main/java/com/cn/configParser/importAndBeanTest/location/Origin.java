package com.cn.configParser.importAndBeanTest.location;

/**
 * @description: 如果没有包扫描，只有@Component，是不会把这个类作为bean注入spring容器中的
 * @author: helisen
 * @create: 2020-09-21 15:08
 **/
public class Origin {
    private String name;
    private String taste;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Override
    public String toString() {
        return "Origin{" +
                "name='" + name + '\'' +
                ", taste='" + taste + '\'' +
                '}';
    }
}
