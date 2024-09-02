package com.gmm.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gmm
 * @date 2022/6/1
 */
@Configuration
public class SpringBeanConfig implements ApplicationContextAware {

    private static ApplicationContext appContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(appContext == null){
            appContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext(){
        return appContext;
    }

    public static <T> T getBean(String beanName, Class<T> clazz){
        return getApplicationContext().getBean(beanName, clazz);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static Object getBean(String beanName){
        return getApplicationContext().getBean(beanName);
    }

}
