package com.nixmash.springdata.jpa.config;

import com.nixmash.springdata.jpa.aop.logging.LoggingAspect;
import com.nixmash.springdata.jpa.enums.DataConfigProfile;
import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectConfiguration {

    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_DEVELOPMENT)
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
