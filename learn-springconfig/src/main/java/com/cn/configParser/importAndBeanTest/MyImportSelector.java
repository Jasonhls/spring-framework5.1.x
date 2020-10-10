package com.cn.configParser.importAndBeanTest;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-22 11:29
 **/
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[] {"com.cn.configParser.importAndBeanTest.location.Origin"};
    }
}
