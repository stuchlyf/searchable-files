package de.stuchlyf.searchablefiles.savefileservice.pdf2txt;

import de.stuchlyf.searchablefiles.savefileservice.common.SaveFileServiceProperties;
import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public record PDF2TXTAdapter(
        SaveFileServiceProperties saveFileServiceProperties,
        @Qualifier("pdf2txtRestTemplate") RestTemplate restTemplate
) {

    public IngestionFile convertPdfFileToText(SaveFile saveFile) {
        final var url = UriComponentsBuilder
                .newInstance()
                .scheme(saveFileServiceProperties().getPdf2txtAdapter().getScheme())
                .host(saveFileServiceProperties().getPdf2txtAdapter().getHost())
                .port(saveFileServiceProperties().getPdf2txtAdapter().getPort())
                .pathSegment(saveFileServiceProperties().getPdf2txtAdapter().getContextPath(), saveFileServiceProperties().getPdf2txtAdapter().getConversionPath())
                .buildAndExpand()
                .toString();

        final var response = restTemplate.postForEntity(url, saveFile, IngestionFile.class);

        if (!response.getStatusCode().is2xxSuccessful()) throw new RuntimeException(); // TODO:

        return response.getBody();
    }
}
