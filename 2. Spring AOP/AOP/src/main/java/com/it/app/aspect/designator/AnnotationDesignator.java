package com.it.app.aspect.designator;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component//enable if you want to use it
@Aspect
public class AnnotationDesignator {


    //Some method which have an annotation:
    @Before("@annotation(Deprecated)")
    //@Before("@annotation(java.lang.Deprecated)")
    public void annotation(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before @annotation " + methodName);
    }
}