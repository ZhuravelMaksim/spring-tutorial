package com.it.app;

import org.springframework.stereotype.Component;

@Component//Stereotype Parent Annotation. See info in Spring docs.
//Also can use Stereotype Annotations as Mata-Info Annotations, mean as inner annotations in other annotations
public class Project {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
