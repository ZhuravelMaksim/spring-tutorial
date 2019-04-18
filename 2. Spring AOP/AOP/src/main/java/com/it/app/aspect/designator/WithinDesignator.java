package com.it.app.aspect.designator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component//enable if you want to use it
@Aspect
public class WithinDesignator {
    //class or package
    @Before("within(com.it.app.bean.A)")// class
    //@Before("within(com.it.app.bean.*)")// package
    //@Before("within(com.it.app.*.*)")// for package
    //@Before("within(com.it.app..*)")// for  package and subpackages
    // Not allowed more than two .. or more than one * in a row
    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before within " + methodName);
    }

    @Before("@within(org.springframework.stereotype.Service)")// annotated class
    public void beforeAnnotation(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before @within " + methodName);
    }

    //Equal to above:
//    @Before("within(@org.springframework.stereotype.Service *)")// annotated class
//    public void beforeAnnotation(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//        System.out.println("Before @within" + methodName);
//    }

    //Difference between @ Within and @Target:
    // One difference between the two is that @within() is matched statically,
    // requiring the corresponding annotation type to have only the CLASS retention.
    // Whereas, @target() is matched at runtime, requiring the same to have the RUNTIME retention.
    // Other than that, within the context of Spring, here is no difference between the join points selected by two.
    //But Spring AOP is only method execution Aspect and cannot use static cause of Dynamic Proxy.
}