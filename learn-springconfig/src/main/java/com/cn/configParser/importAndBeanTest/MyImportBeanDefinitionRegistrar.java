package com.cn.configParser.importAndBeanTest;

import com.cn.configParser.importAndBeanTest.location.Origin;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-22 11:16
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Origin.class);
        beanDefinitionRegistry.registerBeanDefinition("origin", rootBeanDefinition);
    }
}
