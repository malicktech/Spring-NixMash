package com.nixmash.springdata.solr.config;

import com.nixmash.springdata.solr.common.NixmashSolrProperties;
import com.nixmash.springdata.solr.enums.Constants;
import com.nixmash.springdata.solr.repository.simple.SimpleProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.server.support.EmbeddedSolrServerFactoryBean;
import org.springframework.data.solr.server.support.HttpSolrServerFactoryBean;

import javax.annotation.Resource;

@Configuration
@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
public class EmbeddedServerSolrContext {


	@Resource
	private Environment environment;

	@Autowired
	private NixmashSolrProperties nixmashSolrProperties;

    @Bean(name = "solrServer")
    public EmbeddedSolrServerFactoryBean solrServerFactoryBean() {
        EmbeddedSolrServerFactoryBean factory = new EmbeddedSolrServerFactoryBean();
        factory.setSolrHome(nixmashSolrProperties.getSolrEmbeddedPath());
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
