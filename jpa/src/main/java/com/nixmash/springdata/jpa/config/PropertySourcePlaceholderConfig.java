package com.nixmash.springdata.jpa.config;

import com.nixmash.springdata.jpa.enums.DataConfigProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@PropertySources({
    @PropertySource("classpath:/config/jpa-application.properties"),
    @PropertySource("file:C:\\DevLabs\\Workspace-mars\\Spring-NixMash\\install\\samples\\external.properties")
})
public class PropertySourcePlaceholderConfig extends PropertySourcePlaceholderAbstractConfig {

    //@Autowired
   // private ConfigurableListableBeanFactory beanFactory;

	/*
     * PropertySourcesPlaceHolderConfigurer Bean only required for @Value("{}") annotations.
     * Remove this bean if you are not using @Value annotations for injecting properties.
     *
     * with spring boot auto detect application.config class : no need to precize the two
     * Spring Boot already provides that support out of the box. Only adding a @PropertySource
     */

    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_TEST)
    public static PropertySourcesPlaceholderConfigurer testProperties() {
        return createPropertySourcesPlaceholderConfigurer("config/jpa-application-test.properties");
    }

    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_DEVELOPMENT)
    public static PropertySourcesPlaceholderConfigurer devProperties() {
        return createPropertySourcesPlaceholderConfigurer("config/jpa-application-dev.properties");
    }

    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_H2)
    public static PropertySourcesPlaceholderConfigurer databaseH2Properties() {
        return createPropertySourcesPlaceholderConfigurer("config/jpa-application-h2.properties");
    }

    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_MYSQL)
    public static PropertySourcesPlaceholderConfigurer databaseMysqlProperties() {
        return createPropertySourcesPlaceholderConfigurer("config/jpa-application-mysql.properties");
    }

    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_PRODUCTION)
    public static PropertySourcesPlaceholderConfigurer prodProperties() {
        return createPropertySourcesPlaceholderConfigurer("config/jpa-application-prod.properties");
    }



}
