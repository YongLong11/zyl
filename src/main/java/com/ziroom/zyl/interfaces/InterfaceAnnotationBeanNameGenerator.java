package com.ziroom.zyl.interfaces;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;


/*
根据自定义的 BeanName 注解，可将使用了 这个注解的接口（可以有实现类）全部注入成一个map，key是 注解中的 name
eg：
   @Resource
   private Map<String, BaseInterfaces> interfacesMap;
 */
public class InterfaceAnnotationBeanNameGenerator extends AnnotationBeanNameGenerator {

    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        if (definition instanceof AnnotatedBeanDefinition) {
            AnnotationMetadata amd = ((AnnotatedBeanDefinition) definition).getMetadata();

            if (amd.hasAnnotation(BeanName.class.getName())) {
                String name = amd.getAllAnnotationAttributes(BeanName.class.getName()).getFirst(
                        "name").toString();
                return name;
            }
        }
        return super.generateBeanName(definition, registry);
    }
}