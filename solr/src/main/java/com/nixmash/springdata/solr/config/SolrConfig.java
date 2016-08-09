package com.nixmash.springdata.solr.config;

import com.nixmash.springdata.solr.common.NixmashSolrProperties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = "com.nixmash.springdata.solr.repository", namedQueriesLocation = "classpath:named-queries.properties")
//@Import({ EmbeddedServerSolrContext.class, HttpServerSolrContext.class })
@EnableConfigurationProperties({NixmashSolrProperties.class})
public class SolrConfig {


}
