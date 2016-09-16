package com.igitras.hikari.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring Context Utils.
 *
 * @author mason
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    protected SpringContextUtil() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name, Class<T> tClass) throws BeansException {
        return applicationContext.getBean(name, tClass);
    }

    public static <T> T getBean(Class<T> tClass) throws BeansException {
        return applicationContext.getBean(tClass);
    }
}
