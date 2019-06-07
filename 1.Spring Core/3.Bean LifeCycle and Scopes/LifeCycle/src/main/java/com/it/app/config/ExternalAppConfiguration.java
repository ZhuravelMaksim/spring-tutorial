package com.it.app.config;

import com.it.app.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.function.Supplier;

// Exports class\bean as config
public class ExternalAppConfiguration {

    @Bean(initMethod = "init3", destroyMethod = "destroy3")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public User user1() {

        return new User();
    }

    @Bean(initMethod = "init3", destroyMethod = "destroy3")
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public User user2() {
        return new User();
    }

}
