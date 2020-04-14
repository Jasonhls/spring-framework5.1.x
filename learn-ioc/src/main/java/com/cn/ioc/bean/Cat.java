package com.cn.ioc.bean;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/17 7:31
 **/
@Component
public class Cat {
	public Cat() {
		System.out.println("调用Cat()构造方法");
	}
}
