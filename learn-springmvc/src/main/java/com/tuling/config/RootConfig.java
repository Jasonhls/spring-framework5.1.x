package com.tuling.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 根容器的配置类
 * @author: Administrator
 * @create: 2020/3/30 14:15
 **/
@Configuration
@ComponentScan(basePackages = {"com.tuling"},excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {RestController.class, Controller.class}),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = WebAppConfig.class)
})
public class RootConfig {
}
