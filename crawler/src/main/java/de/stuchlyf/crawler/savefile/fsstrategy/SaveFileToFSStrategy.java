package de.stuchlyf.crawler.savefile.fsstrategy;

import de.stuchlyf.crawler.common.CrawlerServiceProperties;
import de.stuchlyf.crawler.savefile.SaveFileStrategy;
import de.stuchlyf.searchablefiles.common.SaveFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
@ConditionalOnExpression(value = "#{'${crawler-service.save-file-strategy}'.contains('save_file_to_fs')}")
public class SaveFileToFSStrategy implements SaveFileStrategy {

    private final File baseOutputFolder;

    public SaveFileToFSStrategy(CrawlerServiceProperties applicationProperties) {
        baseOutputFolder = applicationProperties.getCrawler().getOutputStorageFolder();
    }

    public void save(SaveFile file) {
        final var fsPath = URLDecoder.decode(file.path(), StandardCharsets.UTF_8);

        final var absloluteOutputFilePath = Path.of(baseOutputFolder.getAbsolutePath(), fsPath);
        final var outputFileParent = absloluteOutputFilePath.getParent().toFile();

        if (!outputFileParent.exists()) outputFileParent.mkdirs();

        try {
            Files.write(absloluteOutputFilePath, file.contents());
            log.info("Successfully downloaded File to path {}", file.path());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
