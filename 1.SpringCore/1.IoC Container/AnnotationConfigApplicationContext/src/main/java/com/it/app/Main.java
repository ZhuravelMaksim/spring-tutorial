package com.it.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

public class Main {

    /**
     * Initializing of AnnotationConfigApplicationContext
     * You can have multiple instances of ApplicationContexts, in that case, they will be completely isolated, each with its own configuration.
     *
     * @param args - args
     */
    public static void main(String[] args) {
        //Method 1
        defaultApplicationContext();

        //Method 2
        packageToScanApplicationContext();

        //Method 3
        annotatedClassApplicationContext();
    }

    /**
     * Create a new AnnotationConfigApplicationContext that needs to be populated through register() calls and then manually refresh().
     */
    private static void defaultApplicationContext() {
        AnnotationConfigApplicationContext defaultApplicationContext = new AnnotationConfigApplicationContext();
        defaultApplicationContext.register(Main.class);
        defaultApplicationContext.refresh();
        printBeans(defaultApplicationContext);
    }

    /**
     * Create a new AnnotationConfigApplicationContext, scanning for bean definitions in the given packages and automatically refreshing the context.
     */
    private static void packageToScanApplicationContext() {
        AnnotationConfigApplicationContext packageToScanApplicationContext = new AnnotationConfigApplicationContext("com.it.app");
        printBeans(packageToScanApplicationContext);

    }

    /**
     * Create a new AnnotationConfigApplicationContext, deriving bean definitions from the given annotated classes and automatically refreshing the context.
     */
    private static void annotatedClassApplicationContext() {
        AnnotationConfigApplicationContext annotatedClassApplicationContext = new AnnotationConfigApplicationContext(Main.class);
        printBeans(annotatedClassApplicationContext);

    }

    private static void printBeans(AnnotationConfigApplicationContext annotatedClassApplicationContext) {
        System.out.println(Arrays.asList(annotatedClassApplicationContext.getBeanDefinitionNames()));
    }
}
