package com.tuling.service;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/30 15:20
 **/
@Service
public class TestServiceImpl implements TestService{
	@Override
	public String getNameValue(String name) {
		return "hello to " + name;
	}
}
