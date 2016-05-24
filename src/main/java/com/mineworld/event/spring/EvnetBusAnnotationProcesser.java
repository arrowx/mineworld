
package com.mineworld.event.spring;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;


import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.mineworld.event.assignable.AsyncEventHandler;
import com.mineworld.event.assignable.EventHandler;
import com.mineworld.event.assignable.Handler;

/**
 * 
 * 
 * @author xuqingqing
 * @since 1.0
 * @version 2015年10月29日 xuqingqing
 */
@Component
public class EvnetBusAnnotationProcesser implements ApplicationContextAware {
    
    private AbstractApplicationContext applicationContext;
    
    /** eventBus */
    final EventBus eventBus = new EventBus();
    
    private final ExecutorService pool = Executors.newFixedThreadPool(5);
    
    /** eventBus */
    final AsyncEventBus asyncEventBus = new AsyncEventBus(pool);
    
    Map<String, Handler> customerValidationRules = null;
    
    /**
     * 
     */
    public void loadHandlerMapping() {
        
        customerValidationRules = applicationContext.getBeansOfType(Handler.class);
        for (Object o : customerValidationRules.values()) {
            Class<?> beanClass = o.getClass();
            for (Method method : beanClass.getMethods()) {
                Subscribe annotation = method.getAnnotation(Subscribe.class);
                if (annotation != null) {
                    if (o instanceof AsyncEventHandler)
                        asyncEventBus.register(o);
                    else if (o instanceof EventHandler)
                        eventBus.register(o);
                    else {
                        asyncEventBus.register(o);
                        eventBus.register(o);
                    }
                    break;
                }
            }
        }
        
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (AbstractApplicationContext) applicationContext;
        this.loadHandlerMapping();
    }
    
}
