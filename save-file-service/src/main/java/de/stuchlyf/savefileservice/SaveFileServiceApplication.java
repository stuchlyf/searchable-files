package de.stuchlyf.savefileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class})
@ConfigurationPropertiesScan("de.stuchlyf")
public class SaveFileServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaveFileServiceApplication.class, args);
    }
}