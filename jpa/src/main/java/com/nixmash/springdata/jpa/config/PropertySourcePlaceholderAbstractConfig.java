package com.nixmash.springdata.jpa.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Extend and include {@link PropertySourcesPlaceholderConfigurer} beans in Configuration classes.
 */
public abstract class PropertySourcePlaceholderAbstractConfig {

    /**
     * Acess a classic properties file
     */
    protected static PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer(Resource... resources) {
        final PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        ppc.setIgnoreUnresolvablePlaceholders(true);
        ppc.setIgnoreResourceNotFound(false);
        ppc.setLocations(resources);
        return ppc;
    }

    /**
     * access a YAML properties file
     */
    public static PropertySourcesPlaceholderConfigurer createYamlPropertySourcesPlaceholderConfigurer(Resource... resources) {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(resources);
        propertySourcesPlaceholderConfigurer.setProperties(factory.getObject());
        return propertySourcesPlaceholderConfigurer;
    }

    protected static PropertySourcesPlaceholderConfigurer createPropertySourcesPlaceholderConfigurer(String... propertiesLocations) {
        final Resource[] resources = new Resource[propertiesLocations.length];
        for (int i = 0; i < propertiesLocations.length; i++) {
            String propertiesLocation = propertiesLocations[i];
            resources[i] = new ClassPathResource(propertiesLocation);
        }
        // check if yml or classic properties file
        if (propertiesLocations[0].endsWith (".yml")){
            return createYamlPropertySourcesPlaceholderConfigurer(resources);
        }
        return createPropertySourcesPlaceholderConfigurer(resources);
    }
}
