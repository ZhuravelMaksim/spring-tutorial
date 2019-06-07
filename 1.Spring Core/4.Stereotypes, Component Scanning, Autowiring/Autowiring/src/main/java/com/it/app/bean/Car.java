package com.it.app.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@Order(2)
//@Primary - if we have to select between two or more candidates it tells us that it is a primary bean.
class Toyota extends Car implements Ordered {
    public String getName() {
        return "Toyota";
    }

    public int getOrder() {
        return 2;
    }
}

@Component
//@Order(1)
class Mazda extends Car implements Ordered {
    public String getName() {
        return "Mazda";
    }

    public int getOrder() {
        return 1;
    }
}

@Component
public class Car {
    @Autowired
    private List<Car> cars;

    private String name;

    public void printNames() {

        for (Car car : cars) {
            System.out.println(car.getName());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
