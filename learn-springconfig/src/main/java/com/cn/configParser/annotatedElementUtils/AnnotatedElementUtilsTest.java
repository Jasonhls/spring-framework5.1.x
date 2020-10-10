package com.cn.configParser.annotatedElementUtils;

import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.reflect.Field;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-09 15:40
 **/
public class AnnotatedElementUtilsTest {
    @Test
    public void testAnnotationElementOne() {
        Cat cat = new Cat();
        Class<? extends Cat> catClass = cat.getClass();
        Field[] declaredFields = catClass.getDeclaredFields();
        for (Field field : declaredFields) {
            AnnotationAttributes mergedAnnotationAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(field, MyAutowired.class);
            if(mergedAnnotationAttributes != null) {
                System.out.println(mergedAnnotationAttributes.containsKey("required"));
            }
        }
    }
}
