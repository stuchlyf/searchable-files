package de.stuchlyf.searchablefiles.savefileservice.ingestion;

import de.stuchlyf.searchablefiles.savefileservice.conversion.ConversionStrategy;
import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;
import lombok.extern.slf4j.Slf4j;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public record IngestionService(
        Map<String, ConversionStrategy> conversionStrategyMap,
        @Qualifier("myOpenSearchClient") RestHighLevelClient openSearchClient
) {
    public void ingestFile(SaveFile file) {
        final var fileToBeIngested = prepareFileForIngestion(file);


        final var request = new IndexRequest("searchable-files")
                .source(
                        Map.of(
                                "path", fileToBeIngested.path(),
                                "contents", fileToBeIngested.clearTextContents(),
                                "originalMediaType", file.mediaType()
                        )
                );

        try {
            openSearchClient.index(request, RequestOptions.DEFAULT);
            log.info("Successfully sent file with path {} to OpenSearch", fileToBeIngested.path());
        } catch (IOException e) {
            log.error("There was an error while trying to send the File to openSearch: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private IngestionFile prepareFileForIngestion(SaveFile saveFile) {
        return conversionStrategyMap
                .get(saveFile.mediaType())
                .convert(saveFile);
    }
}
