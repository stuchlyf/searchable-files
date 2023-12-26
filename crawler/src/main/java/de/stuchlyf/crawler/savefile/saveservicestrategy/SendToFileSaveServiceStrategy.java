package de.stuchlyf.crawler.savefile.saveservicestrategy;

import de.stuchlyf.crawler.savefile.SaveFileStrategy;
import de.stuchlyf.searchablefiles.common.SaveFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnExpression(value = "#{'${crawler-service.save-file-strategy}'.contains('send_file_to_save_service')}")
public record SendToFileSaveServiceStrategy(
        RabbitTemplate rabbitTemplate
) implements SaveFileStrategy {

    public void save(SaveFile file) {
        log.info("Sending file with path {} to queue {}", file.path(), "ingest-file");

        rabbitTemplate.convertAndSend("ingest-file", file);
    }
}
