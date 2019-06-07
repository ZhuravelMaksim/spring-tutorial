package com.it.app;

import com.it.app.bean.Car;
import com.it.app.config.AppConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Main {

    @Autowired
    private TestAutowired testAutowired;

    /**
     * main method
     *
     * @param args - args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext packageToScanApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        printBeans(packageToScanApplicationContext);
        Main main = (Main) packageToScanApplicationContext.getBean("main");
        TestAutowired testAutowired = main.testAutowired;
        System.out.println(testAutowired.getA());
        System.out.println(testAutowired.getB());
        System.out.println(testAutowired.getC());

        Car car = (Car) packageToScanApplicationContext.getBean("car");
        car.printNames();
    }

    private static void printBeans(AnnotationConfigApplicationContext annotatedClassApplicationContext) {
        System.out.println(Arrays.asList(annotatedClassApplicationContext.getBeanDefinitionNames()));
    }
}
