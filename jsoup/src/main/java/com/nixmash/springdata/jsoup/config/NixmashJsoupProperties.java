package com.nixmash.springdata.jsoup.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix="nixmashjsoup", ignoreInvalidFields = false, ignoreUnknownFields = true, ignoreNestedProperties = false)
public class NixmashJsoupProperties {

    private Connect connect;

    public Connect getConnect() {
        return connect;
    }

    public static class Connect {

        private String useragent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.63 Safari/537.36";

        public String getUseragent() {
            return useragent;
        }

        public void setUseragent(String useragent) {
            this.useragent = useragent;
        }

    }

}
