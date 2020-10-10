package com.cn.beanAndFactoryBean;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-29 10:09
 **/
public class Car {
    private String brandName;
    private Integer price;
    private String color;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
