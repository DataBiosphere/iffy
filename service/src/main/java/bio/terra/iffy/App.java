package bio.terra.iffy;

import bio.terra.common.logging.LoggingInitializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(
    scanBasePackages = {
      // Scan for iam components & configs
      "bio.terra.common.iam",
      // Scan for logging-related components & configs
      "bio.terra.common.logging",
      // Scan for tracing-related components & configs
      "bio.terra.common.tracing",
      // Scan all service-specific packages beneath the current package
      "bio.terra.iffy"
    },
    // exclude DataSourceAutoConfiguration because this service does not use a
    // relational database
    exclude = {DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan("bio.terra.iffy")
@EnableRetry
@EnableConfigurationProperties
public class App {
  public static void main(String[] args) {
    new SpringApplicationBuilder(App.class).initializers(new LoggingInitializer()).run(args);
  }

  public App() {}

  @Bean("objectMapper")
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule())
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_ABSENT);
  }
}
