package com.cn.configParser.importAndBeanTest;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-21 15:07
 **/
public class Apple {
    private String name;
    private String color;
    private String taste;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", taste='" + taste + '\'' +
                '}';
    }
}
