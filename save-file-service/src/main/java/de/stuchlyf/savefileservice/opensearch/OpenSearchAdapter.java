package de.stuchlyf.savefileservice.opensearch;

import de.stuchlyf.savefileservice.common.SaveFileServiceProperties;
import de.stuchlyf.searchablefiles.common.IngestionFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public record OpenSearchAdapter(
        SaveFileServiceProperties saveFileServiceProperties,
        @Qualifier("openSearchRestTemplate") RestTemplate restTemplate
) {

    public void sendFile(IngestionFile file) {
        final var url = UriComponentsBuilder
                .newInstance()
                .scheme(saveFileServiceProperties().getOpensearchAdapter().getScheme())
                .host(saveFileServiceProperties().getOpensearchAdapter().getHost())
                .port(saveFileServiceProperties().getOpensearchAdapter().getPort())
                .pathSegment(saveFileServiceProperties().getOpensearchAdapter().getContextPath(), saveFileServiceProperties().getOpensearchAdapter().getIngestionPath())
                .buildAndExpand()
                .toString();



        restTemplate().postForEntity(url, file, Void.class);
    }
}
