package com.it.app.aspect.designator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component//enable if you want to use it@Aspect
public class TargetAndThisDesignator {
//Spring AOP is a proxy-based system and differentiates between the proxy object itself (bound to 'this')
// and the target object behind the proxy (bound to 'target').
// By default spring Use Dynamic Proxy, But if Class not implements methods, then it use CGLIB Proxy - subclassing.

//Not allowed to use * and ..


    //calls on proxy object
    @Before("this(com.it.app.bean.A)")
    public void thiss(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before this " + methodName);
    }

    //calls on object itself
    @Before("target(com.it.app.bean.A)")
    public void target(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before target " + methodName);
    }

    //calls on object itself
    @Before("@target(org.springframework.stereotype.Service)")
    public void targetAnnotation(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before @target " + methodName);
    }

}