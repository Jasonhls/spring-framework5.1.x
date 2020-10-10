package com.cn.exception;

import org.junit.Test;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-10 09:48
 **/
public class MyExceptionTest {
    @Test
    public void testMethodExceptionHandler() {
        MyException myException = new MyException();
        Method[] declaredMethods = myException.getClass().getDeclaredMethods();
        List<Class<? extends Throwable>> result = new ArrayList<>();
        for (Method method : declaredMethods) {
            ExceptionHandler mergedAnnotation = AnnotatedElementUtils.findMergedAnnotation(method, ExceptionHandler.class);
            result.addAll(Arrays.asList(mergedAnnotation.value()));
        }
        System.out.println(result.toString());
    }

    @Test
    public void testMethodParameterTypes() {
        MyException myException = new MyException();
        Method[] declaredMethods = myException.getClass().getDeclaredMethods();
        List<Class<? extends Throwable>> parameterTypesResult = new ArrayList<>();
        for (Method method : declaredMethods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (Class<?> clazz : parameterTypes) {
                if(Throwable.class.isAssignableFrom(clazz)) {
                    parameterTypesResult.add((Class<? extends Throwable>) clazz);
                }
            }
        }
        System.out.print(parameterTypesResult.toString());
    }

}
