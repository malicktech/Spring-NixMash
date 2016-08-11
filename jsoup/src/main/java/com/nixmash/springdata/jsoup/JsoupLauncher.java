package com.nixmash.springdata.jsoup;

import com.nixmash.springdata.jpa.config.DefaultProfileUtil;
import com.nixmash.springdata.jpa.enums.DataConfigProfile;
import com.nixmash.springdata.jsoup.components.JsoupUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

// Uncomment to test Jpa module
/*
@ComponentScan(basePackages = "com.nixmash.springdata")
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class })
@EnableConfigurationProperties({NixmashProperties.class, ApplicationSettings.class})
*/
public class JsoupLauncher {

    private static final Logger log = LoggerFactory.getLogger(JsoupLauncher.class);

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
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(JsoupLauncher.class);

        DefaultProfileUtil.addDefaultProfile(app);

        ApplicationContext ctx = app.run(args);
        Environment env = ctx.getEnvironment();

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

        JsoupUI ui = ctx.getBean(JsoupUI.class);
        ui.init();
        SpringApplication.exit(ctx);
    }
}
