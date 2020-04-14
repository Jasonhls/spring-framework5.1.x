package com.tuling.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/30 15:19
 **/
@RestController
@RequestMapping(value = "/test")
public class TestController {
	@GetMapping(value = "/sayHello")
	public String sayHello(){
		return "say hello";
	}
}
