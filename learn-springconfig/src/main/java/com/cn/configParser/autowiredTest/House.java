package com.cn.configParser.autowiredTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-09 13:59
 **/
@Service
public class House {

    @Autowired
    private PersonServiceImpl personServiceImpl;

    public String live() {
        String name = "小明";
        String s = personServiceImpl.liveInTheHouse(name);
        return s;
    }

}
