package com.it.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component//enable if you want to use it@Aspect
@Aspect
public class ExampleAdvicesOfAspect {
//JoinPoint -jp, Used in Before, After, AfterThrowing, AfterReturning:
//        jp.getTarget();
//        jp.getThis();
//        jp.getSignature();
//        jp.getArgs();
//        jp.getKind();
//ProceedingJoinPoint -jp, Used in Around:
//        jp.proceed();
// Also can combine pointcuts with &&  ||  !

    @Pointcut("execution(* com.it.app.bean.A.get*(..))")
    //@Pointcut("execution(* com.it.app.bean.A.*(String,Long))") or @Pointcut("execution(* com.it.app.bean.A.*(java.lang.String))")

    //@Pointcut("execution(public * *.*(..))")
    //Public can be omitted
    // First * is a return type
    // Second * is a class
    // Third * is a method
    //(..) is a number of parameters
    public void componentMethods() {
    }

    @Before("componentMethods()")
    public void before(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("Before " + methodName);
    }

    @AfterReturning("componentMethods()")
    public void afterReturning(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("AfterReturning " + methodName);
    }

    @AfterThrowing("componentMethods()")
    public void afterThrowing(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("AfterThrowing " + methodName);
    }

    @After("componentMethods()")
    public void after(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        System.out.println("After (Finally) " + methodName);
    }

    @Around("componentMethods()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        Object object;
        String methodName = jp.getSignature().getName();
        System.out.println("Around Before " + methodName);
        try {
            object = jp.proceed();
        } catch (Throwable throwable) {
            System.out.println("Around Throwing " + methodName);
            throw throwable;
        }
        System.out.println("Around After " + methodName);
        return object;
    }
}