package com.it.app.aspect.designator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component//enable if you want to use it@Aspect
@Aspect
public class ArgsDesignator {
//use to find any method with some arguments

    @Before("args()")
    //@Before("args(String)")
    //@Before("args(..,*)")
    //@Before("args(..,java.lang.String)")
    //Not allowed to use more than one .. or *
    public void args(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before args " + methodName);
    }

    //Arguments should annotated with some annotation
    //Not allowed to use .. or *
    @Before("@args(org.springframework.stereotype.Service)")
    public void argsAnnotation(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before @args " + methodName);
    }
}