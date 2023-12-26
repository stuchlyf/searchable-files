package de.stuchlyf.searchablefiles.savefileservice.ingestion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stuchlyf.searchablefiles.common.SaveFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public record IngestionListener(
        IngestionService ingestionService,
        ObjectMapper objectMapper
) {

    @RabbitListener(queues = "ingest-file")
    public void listen(String in) throws JsonProcessingException {
        final var input = objectMapper.readValue(in, SaveFile.class);

        ingestionService.ingestFile(input);
    }
}
