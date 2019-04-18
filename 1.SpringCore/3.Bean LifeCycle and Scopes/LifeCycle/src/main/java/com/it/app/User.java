package com.it.app;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class User implements InitializingBean, DisposableBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //For Bean init methods calls when Bean is created.

    @PostConstruct
    public void init1() {
        System.out.println("init1");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("init2");
    }

    public void init3() {
        System.out.println("init3");
    }

    //For Prototype scopes  destroy methods not managed by Context.

    @PreDestroy
    public void destroy1() {
        System.out.println("destroy1 ");
    }

    @Override
    public void destroy() {
        System.out.println("destroy2");
    }

    public void destroy3() {
        System.out.println("destroy3");
    }
}
