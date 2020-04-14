package com.cn.ioc2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;

/**
 * @description:
 * @author: Administrator
 * @create: 2020/3/26 16:41
 **/
@ComponentScan("com.cn.ioc2")
public class AppTwoConfig {
	@Bean
	@Conditional(value = {MyCondition.class})
	public Teacher teacher(){
		return new Teacher();
	}
}
