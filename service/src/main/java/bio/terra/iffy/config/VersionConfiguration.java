package bio.terra.iffy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Read from the git.properties file auto-generated at build time */
@ConfigurationProperties("iffy.version")
public record VersionConfiguration(String gitHash, String gitTag, String build, String github) {}
