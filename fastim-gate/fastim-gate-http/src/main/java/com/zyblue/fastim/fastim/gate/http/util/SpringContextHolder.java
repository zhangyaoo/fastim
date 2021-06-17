package com.zyblue.fastim.fastim.gate.http.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author will
 * @date 2020/8/14 13:03
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    public static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) throws BeansException {
        return context.getBean(tClass);
    }
}
