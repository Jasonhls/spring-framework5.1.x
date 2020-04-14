package com.cn.ioc2;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/26 16:43
 **/
@Component
public class Person {
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
