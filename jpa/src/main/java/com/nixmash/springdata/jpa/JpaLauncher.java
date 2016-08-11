package com.nixmash.springdata.jpa;

import com.nixmash.springdata.jpa.components.JpaUI;
import com.nixmash.springdata.jpa.config.DefaultProfileUtil;
import com.nixmash.springdata.jpa.enums.DataConfigProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

/**
 * This project is meant to be included as a library, and is not intended to run as a standalone application
 */
// Uncomment to test Jpa module
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class JpaLauncher {

    private static final Logger log = LoggerFactory.getLogger(JpaLauncher.class);

    @Inject
    private Environment env;

    /**
     * Initializes jpa Console app.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     */
    @PostConstruct
    public void initApplication() {
        log.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(DataConfigProfile.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(DataConfigProfile.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " +
                "with both the 'dev' and 'prod' profiles at the same time.");
        }
        if (activeProfiles.contains(DataConfigProfile.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(DataConfigProfile.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not" +
                "run with both the 'dev' and 'cloud' profiles at the same time.");
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {

        SpringApplication app = new SpringApplication(JpaLauncher.class);
        // ... customize app settings here
        DefaultProfileUtil.addDefaultProfile(app, "/config/jpa-application.properties");
        // ... run app -> Run the Spring application, creating and refreshing a new ApplicationContext
        // ApplicationContext ctx = app.run(args);
        ConfigurableApplicationContext ctx = app.run(args);
        // get environment for log properties
        Environment env = ctx.getEnvironment();

        // Log
        log.info("\n----------------------------------------------------------\n\t" +
                "Spring Framework Version : '{}' :\n\t" +
                "Spring Boot Version : '{}' :\n\t" +
                "Application '{}' is running! Access URLs:\n\t" +
                "Local: \t\thttp://127.0.0.1:{}\n\t" +
                "External: \thttp://{}:{}\n----------------------------------------------------------",
            SpringVersion.getVersion(),
            SpringBootVersion.getVersion(),
            env.getProperty("spring.application.name"),
            env.getProperty("server.port"),
            InetAddress.getLocalHost().getHostAddress(),
            env.getProperty("server.port"));

        // start jpa console
        JpaUI ui = ctx.getBean(JpaUI.class);
        ui.init();
        // exit a SpringApplication with the context to close
        ctx.close();


    }

}
