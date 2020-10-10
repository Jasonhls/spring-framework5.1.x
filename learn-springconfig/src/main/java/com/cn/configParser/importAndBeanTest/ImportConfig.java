package com.cn.configParser.importAndBeanTest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-21 15:06
 **/
@Configuration
/**
 * @Import三种用法：
 * 方式一：直接填class数组的方式
 *      @Import（{ 要导入的容器中的组件 } ）：容器会自动注册这个组件，id默认是全类名
 *      比如com.cn.configParser.importAndBeanTest.Apple
 * 方式二：ImportSelector方式
 * 方式三：ImportBeanDefinitionRegistrar方式
 *      ImportBeanDefinitionRegistrar：手动注册bean到容器
 */
//@Import(Apple.class)
//@Import(value = {Apple.class})
//@Import(value = {MyImportBeanDefinitionRegistrar.class})
@Import(value = {MyImportSelector.class})
/**
 * 导入配置类，相当于   <import resource="importResourceTest.xml"/>
 */
@ImportResource(value = {"/beans/importResourceTest.xml"})
public class ImportConfig {
    /*@Bean
    public Origin origin() {
        return new Origin();
    }*/
}
