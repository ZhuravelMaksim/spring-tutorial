package com.it.app;

import com.it.app.bean.A;
import com.it.app.bean.BeanService;
import com.it.app.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Main {

    /**
     * main method
     *
     * @param args - args
     */
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext packageToScanApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        printBeans(packageToScanApplicationContext);
        A a = packageToScanApplicationContext.getBean("a", A.class);
        String aName = a.getName();
        System.out.println("------------" + aName);
        B b = packageToScanApplicationContext.getBean("b", B.class);
        String bName = b.getName();
        System.out.println("------------" + bName);
        String aB = a.getB(new B());
        System.out.println("------------" + aB);
        String annotationName = a.getAnnotationName();
        System.out.println("------------" + annotationName);
        BeanService beanService = packageToScanApplicationContext.getBean("beanService", BeanService.class);
        String beanServiceName = beanService.getName();
        System.out.println("------------" + beanServiceName);
        //Only for AdviceExamples:
        //Thread.sleep(500);
        //String error = a.getException("ERROR");
    }

    private static void printBeans(AnnotationConfigApplicationContext annotatedClassApplicationContext) {
        System.out.println(Arrays.asList(annotatedClassApplicationContext.getBeanDefinitionNames()));
    }
}
