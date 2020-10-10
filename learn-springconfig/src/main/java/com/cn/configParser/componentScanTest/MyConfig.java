package com.cn.configParser.componentScanTest;

import com.cn.conditional.MyCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-17 16:58
 **/
@Configuration
@ComponentScan(value = "com.cn.configParser.componentScanTest")
public class MyConfig {
    @Bean
    @Conditional(value = {MyCondition.class})
    public Person person() {
        Person p = new Person();
        p.setName("tom");
        p.setAge(28);
        p.setSex("男");
        return p;
    }
}
