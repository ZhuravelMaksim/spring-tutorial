package com.it.app;

import com.it.app.bean.A;
import com.it.app.bean.B;
import com.it.app.bean.C;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TestAutowired {
    private String name;

    //@Autowired
    //@Qualifier("a")
    private A a;

    //If we dont know anything about bean existence:
    //@Autowired(required = false)
    private B b;

    //@Autowired
    private C c;

    public TestAutowired() {
    }

    // Can have only one constructor with Autowired.
    // If we have only one constructor, we can define it without Autowired.
    // It can have any visibility
    // @Autowired
    private TestAutowired(B b) {
        this.b = b;
    }

    //if we dont know anything about bean existence we can:

    // @Autowired
    TestAutowired(@Nullable A a, @Nullable B b) {
        this.a = a;
        this.b = b;
    }

    //@Autowired
    protected TestAutowired(@Qualifier("a") Optional<A> a, @Qualifier("b") Optional<B> b, @Qualifier("c") Optional<C> c) {
        this.a = a.orElse(null);
        this.b = b.orElse(null);
        this.c = c.orElse(null);
    }

    //@Autowired(required = false)
    public TestAutowired(A a, C c) {
        this.a = a;
        this.c = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    // Method injection:
    // It can have any visibility

    //Regular Setter method
    //@Autowired
    public void setA(A a) {
        this.a = a;
    }

    //Method with arbitrary name
    //@Autowired
    public void injectA(A a) {
        this.a = a;
    }

    //Method with more than one parameters:
    //@Autowired
    public void injectAC(A a, C c) {
        this.a = a;
        this.c = c;
    }

    //if we dont know anything about bean existence we can:

    //@Autowired
    public void injectABUse(Optional<A> a, Optional<B> b) {
        this.a = a.orElse(null);
        this.b = b.orElse(null);
    }

    //@Autowired
    public void injectBUse(Optional<B> b) {
        this.b = b.orElse(null);
    }

    //@Autowired
    public void injectABUseNullable(@Nullable A a, @Nullable B b) {
        this.a = a;
        this.b = b;
    }

    //If method have required=false and it dont have a bean then method will not invoke:

    //@Autowired(required = false)
    public void injectABNotUse(A a, B b) {
        this.a = a;
        this.b = b;
    }

    //@Autowired(required = false)
    public void injectBNotUse(B b) {
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public C getC() {
        return c;
    }

    public void setC(C c) {
        this.c = c;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
