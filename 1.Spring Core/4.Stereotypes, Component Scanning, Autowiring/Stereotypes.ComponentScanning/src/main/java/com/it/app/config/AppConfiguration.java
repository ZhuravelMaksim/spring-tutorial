package com.it.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.it.app",//package for scanning
        basePackageClasses = {AppConfiguration.class}, //class for scanning
        includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*User"),//include filters
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Deprecated.class)//exclude filters
)
public class AppConfiguration {

}
