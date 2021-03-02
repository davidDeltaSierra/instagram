package br.com.instagram.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        BeanUtil.CONTEXT = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return CONTEXT.getBean(beanClass);
    }
}
