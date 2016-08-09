package com.nixmash.springdata.solr;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nixmash.springdata.solr.common.NixmashSolrProperties;
import com.nixmash.springdata.solr.config.SolrConfig;

/**
 *
 * NixMash Spring Notes: ---------------------------------------------------
 *
 * Based on Christoph Strobl's Spring Solr Repository Example for Spring Boot
 *
 * On GitHub: https://goo.gl/JoAYaT
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SolrConfig.class)
@ActiveProfiles("dev")
public class SolrContext {

	@Autowired
	private NixmashSolrProperties nixmashSolrProperties;

	@Test
	public void contextLoads() {
		assertNotNull(nixmashSolrProperties.getSolrServerUrl());
	}

}
