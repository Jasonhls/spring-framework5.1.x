package com.cn.sort;

import org.springframework.core.PriorityOrdered;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-18 16:05
 **/
public class ClassThree implements PriorityOrdered {
    @Override
    public int getOrder() {
        return 2;
    }
}
