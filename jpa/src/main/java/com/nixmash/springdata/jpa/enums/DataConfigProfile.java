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
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";

    // Spring profile for Embeded DB, or external Mysl, postgresql datasource
    public static final String SPRING_PROFILE_H2 = "h2";
    public static final String SPRING_PROFILE_POSTGRESQL = "postgres";
    public static final String SPRING_PROFILE_MYSQL = "mysql";
}

