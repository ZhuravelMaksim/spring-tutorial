package com.it.app.config;

import org.springframework.context.annotation.Bean;

import java.util.function.Supplier;

// Exports class\bean as config
public class ExternalAppConfiguration {

    //create bean from methods

    @Bean
    public Supplier<String> stringSupplier1() {
        return () -> "Hello World1";
    }

    @Bean(name = "stringSupplier2Name")
    public Supplier<String> stringSupplier2() {
        return () -> "Hello World2";
    }

    @Bean(value = "stringSupplier3Value")
    public Supplier<String> stringSupplier3() {
        return () -> "Hello World3";
    }

    @Bean("stringSupplier4Default")
    public Supplier<String> stringSupplier4() {
        return () -> "Hello World4";
    }
}
