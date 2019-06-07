package com.it.app;

import com.it.app.config.AppConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;

public class Main {

    /**
     * main method
     *
     * @param args - args
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext packageToScanApplicationContext = new AnnotationConfigApplicationContext("com.it.app.config");
        printBeans(packageToScanApplicationContext);

        scopeExample(packageToScanApplicationContext);
        packageToScanApplicationContext.close();
    }

    private static void scopeExample(AnnotationConfigApplicationContext packageToScanApplicationContext) {
        User user11 = (User) packageToScanApplicationContext.getBean("user1");
        User user12 = (User) packageToScanApplicationContext.getBean("user1");
        user11.setName("user11");
        user12.setName("user12");

        AssertBeans(user11, user12);

        User user21 = (User) packageToScanApplicationContext.getBean("user2");
        User user22 = (User) packageToScanApplicationContext.getBean("user2");
        user21.setName("user21");
        user22.setName("user22");

        AssertBeans(user21, user22);
    }

    private static void AssertBeans(User user1, User user2) {
        String user1Name = user1.getName();
        String user2Name = user2.getName();
        if (Objects.equals(user1Name, user2Name)) {
            System.out.println("YES:" + user1Name + ":" + user2Name);
        } else {
            System.out.println("NO:" + user1Name + ":" + user2Name);
        }
    }

    private static void printBeans(AnnotationConfigApplicationContext annotatedClassApplicationContext) {
        System.out.println(Arrays.asList(annotatedClassApplicationContext.getBeanDefinitionNames()));
    }
}
