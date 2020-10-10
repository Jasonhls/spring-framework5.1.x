package com.cn.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-10 09:47
 **/
@ControllerAdvice
public class MyException {
    @ExceptionHandler(value = Exception.class)
    public String sayHello() {
        return "hls say hello to the world";
    }

    public String eatLunch(String name, ClassNotFoundException exception) {
        return "hls eat lunch";
    }
}
