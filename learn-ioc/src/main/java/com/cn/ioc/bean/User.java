package com.cn.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/17 7:31
 **/
@Component
public class User {

	@Autowired
	private Fox fox;   /** fox 早期没有填充属性的bean */

	public User(){
		System.out.println("调用User()无参构造器");
	}

	public User(Fox fox){
		System.out.println(fox);
		System.out.println("调用User(Fox fox)构造方法");
	}
}
