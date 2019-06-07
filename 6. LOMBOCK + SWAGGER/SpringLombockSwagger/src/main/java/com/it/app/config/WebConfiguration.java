package com.it.app.config;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
public class WebConfiguration {
    @Bean
    public Mapper mapper() {
        return new DozerBeanMapper();
    }
}
