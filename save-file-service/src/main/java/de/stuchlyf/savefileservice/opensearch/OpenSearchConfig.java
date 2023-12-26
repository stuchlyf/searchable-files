package de.stuchlyf.savefileservice.opensearch;

import de.stuchlyf.savefileservice.common.SaveFileServiceProperties;
import lombok.RequiredArgsConstructor;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration;
import org.opensearch.data.client.orhlc.ClientConfiguration;
import org.opensearch.data.client.orhlc.RestClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class OpenSearchConfig extends AbstractOpenSearchConfiguration {

    private final SaveFileServiceProperties saveFileServiceProperties;

    @Bean
    public RestTemplate openSearchRestTemplate() {
        return new RestTemplate();
    }

    @Override
    @Bean
    @Qualifier("myOpenSearchClient")
    public RestHighLevelClient opensearchClient() {
        final var clientConfiguration = ClientConfiguration.builder()
                .connectedTo("%s:%s".formatted(saveFileServiceProperties.getOpensearchAdapter().getHost(), saveFileServiceProperties.getOpensearchAdapter().getPort()))
                .withBasicAuth("admin", "admin")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
