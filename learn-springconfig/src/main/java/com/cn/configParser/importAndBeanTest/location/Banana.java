package com.cn.configParser.importAndBeanTest.location;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-21 15:09
 **/
@Component
public class Banana {
    private String name;
    private String producingArea;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducingArea() {
        return producingArea;
    }

    public void setProducingArea(String producingArea) {
        this.producingArea = producingArea;
    }

    @Override
    public String toString() {
        return "Banana{" +
                "name='" + name + '\'' +
                ", producingArea='" + producingArea + '\'' +
                '}';
    }
}
