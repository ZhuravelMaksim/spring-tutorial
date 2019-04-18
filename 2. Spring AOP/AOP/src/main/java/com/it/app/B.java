package com.it.app;

import org.springframework.stereotype.Service;


@Service
public class B {

    public String getName() {
        return "Object B";
    }

    public String getException(String string) {
        throw new RuntimeException(string);
    }

}
