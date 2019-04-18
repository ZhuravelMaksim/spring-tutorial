package com.it.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration // Tells we use class\bean as config
@Import(ExternalAppConfiguration.class) // <-- AppConfiguration imports ExternalAppConfiguration
public class AppConfiguration {
}
