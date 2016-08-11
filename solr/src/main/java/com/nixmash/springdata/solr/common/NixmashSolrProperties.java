package com.nixmash.springdata.solr.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix="nixmashsolr", ignoreInvalidFields = false, ignoreUnknownFields = true, ignoreNestedProperties = false)
public class NixmashSolrProperties {

    private static final Logger log = LoggerFactory.getLogger(NixmashSolrProperties.class);
    private String separator = System.getProperty("file.separator");

    private String solrServerUrl; // ="http://localhost:8983/solr/#/collection1";
    private String solrEmbeddedPath; // ="C:"+separator+"DevLabs"+separator+"Workspace-mars"+separator+"Spring-NixMash"+separator+"install"+separator+"solr"+separator+"embedded";

	public String getSolrServerUrl() {
		return solrServerUrl;
	}

	public void setSolrServerUrl(String solrServerUrl) {
		this.solrServerUrl = solrServerUrl;
	}

	public String getSolrEmbeddedPath() {
		return solrEmbeddedPath;
	}

	public void setSolrEmbeddedPath(String solrEmbeddedPath) {
		this.solrEmbeddedPath = solrEmbeddedPath;
	}


	/* ------------------------------------------------------------- */

    @Override
    public String toString() {
        return solrServerUrl + " : " + solrEmbeddedPath;
    }

    @PostConstruct
    public void xxx() {
        log.info("NixmashSolrProperties Initialized [{}] [{}]", solrServerUrl, solrEmbeddedPath);
    }

}
