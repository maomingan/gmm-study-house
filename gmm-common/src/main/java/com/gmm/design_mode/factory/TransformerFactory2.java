package com.gmm.design_mode.factory;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gmm
 * @date 2024/8/30
 */
@Component
public class TransformerFactory2 implements InitializingBean, ApplicationContextAware {

    private Map<String, Transformer> beanMap = new HashMap<>();

    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        context.getBeansOfType(Transformer.class).values().forEach(transformer -> beanMap.put(transformer.getName(), transformer));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public Transformer getTransformerByName(String name){
        return beanMap.get(name);
    }

}
