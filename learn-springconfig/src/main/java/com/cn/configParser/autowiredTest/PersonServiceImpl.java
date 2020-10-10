package com.cn.configParser.autowiredTest;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-09 13:59
 **/
@Service
public class PersonServiceImpl implements PersonService {

    @Override
    public String liveInTheHouse(String name) {
        return name + "住在房子里面";
    }
}
