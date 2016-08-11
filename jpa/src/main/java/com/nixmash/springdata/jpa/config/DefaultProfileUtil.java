package com.nixmash.springdata.jpa.config;

import com.nixmash.springdata.jpa.enums.DataConfigProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class to load a Spring profile to be used as default
 * when there is no <code>spring.profiles.active</code> set in the environment or as command line argument.
 * If the value is not available in <code>application.yml</code> then <code>dev</code> profile will be used as default.
 */
public final class DefaultProfileUtil {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(DefaultProfileUtil.class);

    private static final String SPRING_PROFILE_ACTIVE = "spring.profiles.active";


    /**
     * Get a default profile from <code>application.yml</code>.
     */
    public static String getDefaultActiveProfiles(String propertyLocation) {

        final Properties BUILD_PROPERTIES;

        // check if yml or classic properties file
        if (propertyLocation.endsWith (".yml")){
            BUILD_PROPERTIES = readYamlProperties(propertyLocation);
        } else {
            BUILD_PROPERTIES = readProperties(propertyLocation);
        }

        if (BUILD_PROPERTIES != null) {
            String activeProfile = BUILD_PROPERTIES.getProperty(SPRING_PROFILE_ACTIVE);
            if (activeProfile != null && !activeProfile.isEmpty() &&
                (activeProfile.contains(DataConfigProfile.SPRING_PROFILE_DEVELOPMENT) ||
                    activeProfile.contains(DataConfigProfile.SPRING_PROFILE_PRODUCTION))) {
                return activeProfile;
            }
        }
        log.warn("No Spring profile configured, running with default profile: {}", DataConfigProfile.SPRING_PROFILE_DEVELOPMENT);
        return DataConfigProfile.SPRING_PROFILE_DEVELOPMENT;
    }

    /**
     * Set a default to use when no profile is configured.
     */
    public static void addDefaultProfile(SpringApplication app, String propertyLocation) {
        Map<String, Object> defProperties = new HashMap<>();
        /*
        * The default profile to use when no other profiles are defined
        * This cannot be set in the <code>application.yml</code> file.
        * See https://github.com/spring-projects/spring-boot/issues/1219
        */
        defProperties.put(SPRING_PROFILE_ACTIVE, getDefaultActiveProfiles(propertyLocation));
        app.setDefaultProperties(defProperties);
    }

    /**
     * Load application.yml from classpath.
     */
    private static Properties readYamlProperties(String propertyLocation) {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(new ClassPathResource(propertyLocation));
            return factory.getObject();
        } catch (Exception e) {
            log.error("Failed to read {} to get default profile", propertyLocation);
        }
        return null;
    }

    /**
     * Load application.properties from classpath.
     */
    private static Properties readProperties(String propertyLocation) {

        Properties prop = new Properties();
        InputStream input = null;
        try {
            // loading resource using getResourceAsStream() method
            input = DefaultProfileUtil.class.getResourceAsStream(propertyLocation);
            //load a properties file from class path, inside static method
            prop.load(input);
            return prop;
        } catch (Exception e) {
            log.error("Failed to read {} to get default profile", propertyLocation);
        }
        return null;
    }

}
