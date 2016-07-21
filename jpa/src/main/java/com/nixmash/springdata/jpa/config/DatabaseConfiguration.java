package com.nixmash.springdata.jpa.config;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.nixmash.springdata.jpa.enums.DataConfigProfile;
import com.nixmash.springdata.jpa.model.auditors.AuditingDateTimeProvider;
import com.nixmash.springdata.jpa.model.auditors.DateTimeService;
import com.nixmash.springdata.jpa.model.auditors.UsernameAuditorAware;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Malick on 21/07/2016.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.nixmash.springdata.jpa")
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableSpringDataWebSupport
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Autowired
    private Environment env;

    @Autowired(required = false)
    private MetricRegistry metricRegistry;

    @Bean(destroyMethod = "close")
    @ConditionalOnExpression("#{!environment.acceptsProfiles('" + DataConfigProfile.SPRING_PROFILE_CLOUD + "') && !environment.acceptsProfiles('" + DataConfigProfile.SPRING_PROFILE_HEROKU + "')}")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        log.debug("Configuring Datasource");
        if (dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                    " cannot start. Please check your Spring profile, current profiles are: {}",
                Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        HikariDataSource hikariDataSource =  (HikariDataSource) DataSourceBuilder
            .create(dataSourceProperties.getClassLoader())
            .type(HikariDataSource.class)
            .driverClassName(dataSourceProperties.getDriverClassName())
            .url(dataSourceProperties.getUrl())
            .username(dataSourceProperties.getUsername())
            .password(dataSourceProperties.getPassword())
            .build();

        if (metricRegistry != null) {
            hikariDataSource.setMetricRegistry(metricRegistry);
        }
        return hikariDataSource;
    }


    /**
     * Open the TCP port for the H2 database, so it is available remotely.
     *
     * @return the H2 database TCP server
     * @throws SQLException if the server failed to start
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile(DataConfigProfile.SPRING_PROFILE_DEVELOPMENT)
    public Server h2TCPServer() throws SQLException {
        return Server.createTcpServer("-tcp","-tcpAllowOthers");
    }


    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }

    // ==============================================

    @Value("#{ environment['entity.package'] }")
    private String entityPackage;
    @Bean
    AuditorAware<String> auditorProvider() {
        return new UsernameAuditorAware();
    }

    @Bean
    DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
        return new AuditingDateTimeProvider(dateTimeService);
    }

    /**
     * to populate, initialize a database
     *
     * @param dataSource
     * @return
     */
    @Bean
    @Profile(DataConfigProfile.SPRING_PROFILE_H2)
    public DatabasePopulator databasePopulator(DataSource dataSource) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.setIgnoreFailedDrops(true);
        populator.addScripts(new ClassPathResource("/db/h2schema.sql"),
            new ClassPathResource("/db/h2data.sql"));
        try {
            populator.populate(dataSource.getConnection());
        } catch (SQLException ignored) {
        }
        return populator;
    }
    // ==============================================



}
