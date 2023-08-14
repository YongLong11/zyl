package com.ziroom.zyl.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 从spring容器中，根据类名获取对应的bean
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T findBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }


    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtils.applicationContext == null) {
            ApplicationContextUtils.applicationContext = applicationContext;
        }
    }

}
