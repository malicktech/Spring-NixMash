package com.nixmash.springdata.solr.config;

import javax.annotation.Resource;

import com.nixmash.springdata.solr.enums.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.server.support.HttpSolrServerFactoryBean;

import com.nixmash.springdata.solr.common.NixmashSolrProperties;
import com.nixmash.springdata.solr.repository.simple.SimpleProductRepository;

@Configuration
@Profile(Constants.SPRING_PROFILE_PRODUCTION)
@PropertySource("classpath:config/solr_application_prod.properties")
public class HttpServerSolrContext {


	@Autowired
	private Environment environment;

	@Autowired
	private NixmashSolrProperties nixmashSolrProperties;

    /**
     * The solrServer bean is used to connect to the running Solr instance. Since Spring Data Solr uses Solrj we create a Solrj HttpSolrServer instance.
     * @return
     */
	@Bean(name = "solrServer")
	public HttpSolrServerFactoryBean solrServerFactoryBean() {
		HttpSolrServerFactoryBean factory = new HttpSolrServerFactoryBean();
		factory.setUrl(nixmashSolrProperties.getSolrServerUrl());
		return factory;
	}

	@Bean
	public SolrTemplate solrTemplate() throws Exception {
		return new SolrTemplate(solrServerFactoryBean().getObject());
	}

	@Bean
	public SimpleProductRepository simpleRepository() throws Exception {
		SimpleProductRepository simpleRepository = new SimpleProductRepository();
		simpleRepository.setSolrOperations(solrTemplate());
		return simpleRepository;
	}

}
