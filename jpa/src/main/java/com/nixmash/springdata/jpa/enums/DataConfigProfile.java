package com.nixmash.springdata.jpa.enums;

/**
 * Profile and DB Configuration Types
 * <p/>
 *
 * @author Gordon Dickens
 */
public final class DataConfigProfile {

    private DataConfigProfile() {
    }

    // Spring profile for development and production, see http://jhipster.github.io/profiles/
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    // Spring profile for Embeded DB, or external Mysl, postgresql datasource
    public static final String SPRING_PROFILE_H2 = "h2";
    public static final String SPRING_PROFILE_POSTGRESQL = "postgres";
    public static final String SPRING_PROFILE_MYSQL = "mysql";

    // Spring profile used when deploying with Spring Cloud (used when deploying to CloudFoundry)
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    // Spring profile used when deploying to Heroku
    public static final String SPRING_PROFILE_HEROKU = "heroku";

    // Spring profile used to disable swagger
    public static final String SPRING_PROFILE_NO_SWAGGER = "no-swagger";


}

