package com.nixmash.springdata.solr;

import com.nixmash.springdata.solr.enums.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.ApplicationContext;
import org.springframework.core.SpringVersion;

import com.nixmash.springdata.solr.common.SolrUI;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

// Uncomment to test Solr module
// @SpringBootApplication
public class SolrLauncher {

    private static final Logger log = LoggerFactory.getLogger(SolrLauncher.class);

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

        if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
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
        SpringApplication app = new SpringApplication(SolrLauncher.class);

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

		SolrUI ui = ctx.getBean(SolrUI.class);
		ui.init();
        SpringApplication.exit(ctx);
	}

}
