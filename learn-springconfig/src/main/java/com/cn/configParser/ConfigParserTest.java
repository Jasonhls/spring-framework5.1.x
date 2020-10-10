package com.cn.configParser;

import com.cn.configParser.autowiredTest.AutowiredConfig;
import com.cn.configParser.autowiredTest.House;
import com.cn.configParser.componentScanTest.MyConfig;
import com.cn.configParser.componentScanTest.Student;
import com.cn.configParser.importAndBeanTest.Apple;
import com.cn.configParser.importAndBeanTest.ImportConfig;
import com.cn.configParser.importAndBeanTest.location.Banana;
import com.cn.configParser.propertySourceConfig.EnvironmentPropertySourceConfig;
import com.cn.configParser.propertySourceConfig.TestBean;
import com.cn.configParser.valueConfig.MyValueComponent;
import com.cn.configParser.valueConfig.MyValueConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-17 17:00
 **/
public class ConfigParserTest {
    /**
     * 测试@ComponentScan，@ComponentScans
     */
    @Test
    public void testComponentScanConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Student student = (Student) context.getBean("student");
        System.out.println(student.getAge() + "; " + student.getName() + "; " +student.getSex());
    }

    /**
     * 测试注解@Import，@ImportResource
     */
    @Test
    public void testImportConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImportConfig.class);
        Apple apple = (Apple) context.getBean("com.cn.configParser.importAndBeanTest.Apple");
        System.out.println(apple.getName() + "; " + apple.getColor() + "; " + apple.getTaste());
        Banana banana = (Banana) context.getBean("banana");
        System.out.println(banana.getName() + "; " + banana.getProducingArea());
    }

    /**
     * 测试注解@PropertySource与Environment一起用
     */
    @Test
    public void testPropertySourceAndEnvironment() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EnvironmentPropertySourceConfig.class);
        TestBean tb = (TestBean) context.getBean("testBean");
        System.out.println(tb.toString());
    }


    /**
     * 使用xml方式测试@Value和@propertySource，xml方式解析property-placeholder标签的时候会向AbstractBeanFactory的
     * 属性embeddedValueResolvers添加StringValueResolver对象，用于解析@Value的
     */
    @Test
    public void testPropertySourceAndValueWithXml() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/xml/springValueTestOne.xml");
        MyValueComponent bean = context.getBean(MyValueComponent.class);
        System.out.println(bean.getDriverClass() + "; " + bean.getDbUrl() + "; " + bean.getUserName() + "; " + bean.getPassword());
    }

    /**
     * 使用注解方式测试注解@PropertySource与@Value，必须自定义一个PropertySourcesPlaceholderConfigurer，并注入到spring容器中
     */
    @Test
    public void testPropertySourceAndValueWithConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyValueConfig.class);
        MyValueComponent bean = context.getBean(MyValueComponent.class);
        System.out.println(bean.getDriverClass() + "; " + bean.getDbUrl() + "; " + bean.getUserName() + "; " + bean.getPassword());
    }

    /**
     * @Autowired注解源码分析
     */
    @Test
    public void testAutowired() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        House house = context.getBean(House.class);
        String result = house.live();
        System.out.println(result);
    }
}
