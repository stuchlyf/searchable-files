package de.stuchlyf.searchablefiles.savefileservice.pdf2txt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PDF2TXTConfig {

    @Bean("pdf2txtRestTemplate")
    public RestTemplate pdf2txtRestTemplate() {
        return new RestTemplate();
    }
}
