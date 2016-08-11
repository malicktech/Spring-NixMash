package com.nixmash.springdata.mail.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Malick on 24/07/2016.
 */
@ConfigurationProperties(prefix = "nixmashmail")
public class MailProperties {

    private final Contact contact = new Contact();

    public Contact getContact() {
        return contact;
    }

    public static class Contact {

        private Site site = new Site();
        private String subject = "New Contact Email from {0}";
        private Body body = new Body();
        private String greeting = "Hi Poppa Bear, you have a new Contact Message!";

        public Site getSite() {
            return site;
        }

        public Body getBody() {
            return body;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getGreeting() {
            return greeting;
        }

        public void setGreeting(String greeting) {
            this.greeting = greeting;
        }

        // SITE

        public static class Site {

            private String name = "NixMash Spring";

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        // BODY

        public static class Body {

            private String type = "HTML";

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

    }
}
