package com.ziroom.zyl.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 从spring容器中，根据类名获取对应的bean
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtils.applicationContext == null) {
            ApplicationContextUtils.applicationContext = applicationContext;
        }
    }
    public static <T> T findBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public <T> List<T>  getClassList(Class<T> clazz){
        if(applicationContext == null){
            return null;
        }
        Map<String, T> beansOfType = applicationContext.getBeansOfType(clazz);
        if(CollectionUtils.isEmpty(beansOfType)){
            return null;
        }
        return new ArrayList<>(beansOfType.values());
    }

}
