package pl.agh.edu.to.project.back.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationApp {

    @Value("${columns.names}")
    private String[] columnsNames;

    @Bean
    public String[] columnsIndex() {
        return this.columnsNames;
    }

}
