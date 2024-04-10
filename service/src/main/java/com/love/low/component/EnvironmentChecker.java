package com.love.low.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
@Component
public class EnvironmentChecker {
 
    @Value("${spring.profiles.active}")
    private String activeProfile;
 
    public String getActiveProfile() {
        return activeProfile;
    }
 
    public boolean isDevEnvironment() {
        return "dev".equals(activeProfile);
    }
 
    public boolean isProdEnvironment() {
        return "prod".equals(activeProfile);
    }

}