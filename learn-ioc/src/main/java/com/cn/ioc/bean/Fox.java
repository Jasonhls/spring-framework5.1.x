package com.cn.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/17 7:31
 **/
@Component
public class Fox {

	@Autowired
	private User user;    //完整的User实例

	public Fox() {
		System.out.println("调用Fox()无参构造器");
	}

	public Fox(User user){
		System.out.println(user);
		System.out.println("调用Fox(User user)有参构造器");
	}
}
