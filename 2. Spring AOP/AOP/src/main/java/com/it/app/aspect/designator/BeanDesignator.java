package com.it.app.aspect.designator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component//enable if you want to use it@Aspect
@Aspect
public class BeanDesignator {


    //@Before("bean(a)")
    //@Before("bean(*Service)")
    @Before("bean(bean*)")
    public void bean(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before bean " + methodName);
    }
}