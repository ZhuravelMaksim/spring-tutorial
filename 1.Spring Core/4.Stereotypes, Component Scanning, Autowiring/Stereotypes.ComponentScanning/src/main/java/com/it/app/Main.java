package com.it.app;

import com.it.app.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {

    /**
     * main method
     *
     * @param args - args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext packageToScanApplicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        printBeans(packageToScanApplicationContext);
    }


    private static void printBeans(AnnotationConfigApplicationContext annotatedClassApplicationContext) {
        System.out.println(Arrays.asList(annotatedClassApplicationContext.getBeanDefinitionNames()));
    }
}
