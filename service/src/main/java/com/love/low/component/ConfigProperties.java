package com.love.low.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConfigProperties {

    @Value("${spring.profiles.active}")
    private String someProperty;

    private final Environment environment;

    public ConfigProperties(Environment environment) {
        this.environment = environment;
    }

    public String getSomeProperty() {
        return someProperty;
    }

    public String getPropertyFromEnvironment(String key) {
        return environment.getProperty(key);
    }
}