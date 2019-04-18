package com.it.app.bean;

import com.it.app.B;
import org.springframework.stereotype.Service;


@Service
public class A {

    public String getName() {
        return "Object A";
    }

    public String getException(String string) {
        throw new RuntimeException(string);
    }

    public String getB(B b) {
        return "B";
    }

    @Deprecated
    public String getAnnotationName() {
        return "Deprecated";
    }
}
