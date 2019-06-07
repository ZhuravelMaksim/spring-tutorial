package com.it.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // Tells we use class\bean as config
@Import(ExternalAppConfiguration.class) // <-- AppConfiguration imports ExternalAppConfiguration
public class AppConfiguration {
    @Bean
    public static CustomBeanFactoryPostProcessor customBeanFactoryPostProcessor() {
        return new CustomBeanFactoryPostProcessor();
    }

    @Bean
    public static CustomBeanPostProcessor customBeanPostProcessor() {
        return new CustomBeanPostProcessor();
    }
}
