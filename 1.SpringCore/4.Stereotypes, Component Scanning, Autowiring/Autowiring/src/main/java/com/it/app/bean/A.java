package com.it.app.bean;

import org.springframework.stereotype.Component;

@Component
//@Qualifier("aa") - dont work, should use at @Bean point
public class A {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
