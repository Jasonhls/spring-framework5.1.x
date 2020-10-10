package com.cn.sort;

import org.junit.Test;
import org.springframework.core.OrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description: 排序接口PriorityOrdered的用法
 * @author: helisen
 * @create: 2020-09-18 16:06
 **/
public class PriorityOrderTest {
    @Test
    public void testOrder() {
        List<Object> list = new ArrayList<>();
        list.add(new ClassThree());
        list.add(new ClassTwo());
        list.add(new ClassOne());
        System.out.println(list.toString());
        Collections.sort(list, OrderComparator.INSTANCE);
        System.out.println(list.toString());
    }
}
