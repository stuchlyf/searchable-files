package de.stuchlyf.crawler.fileimport;

import de.stuchlyf.crawler.crawler.CrawlerStatus;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import jakarta.annotation.Nonnull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import-service")
public record ImportController(
        ImportService importService
) {

    @Nonnull
    @PostMapping(value = "/start", produces = {MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<Void> start() {
        try {
            importService.start();

            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Nonnull
    @GetMapping("/status")
    public ResponseEntity<CrawlerStatus> status() {
        return ResponseEntity.ok(importService.getStatus());
    }
}
