package com.it.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.it.app")
@EnableAspectJAutoProxy
public class AppConfiguration {

}
