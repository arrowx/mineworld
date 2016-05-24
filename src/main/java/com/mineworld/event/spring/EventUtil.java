
package com.mineworld.event.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhangwei_david
 * @version $Id: CustomerValidatorFactory.java, v 0.1 2015年5月30日 下午1:03:56 zhangwei_david Exp $
 */
@Component
public class EventUtil {
    
    @Autowired
    private EvnetBusAnnotationProcesser evnetBusAnnotationConfigProcesser;
    
    /**
     * @param target
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void postEvent(Object target) {
        evnetBusAnnotationConfigProcesser.eventBus.post(target);
    }
    
    /**
     * @param target
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    public void postAsyncEvent(Object target) {
        evnetBusAnnotationConfigProcesser.asyncEventBus.post(target);
    }
    
}
