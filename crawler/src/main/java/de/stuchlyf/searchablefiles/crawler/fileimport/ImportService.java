package de.stuchlyf.searchablefiles.crawler.fileimport;

import de.stuchlyf.searchablefiles.crawler.common.CrawlerServiceProperties;
import de.stuchlyf.searchablefiles.crawler.crawler.CrawlerStatus;
import de.stuchlyf.searchablefiles.crawler.crawler.PdfCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import org.springframework.stereotype.Service;

@Service
public record ImportService(
        CrawlController crawlController,
        CrawlController.WebCrawlerFactory<PdfCrawler> pdfCrawlerFactory,
        CrawlerServiceProperties applicationProperties
) {

    public void start() {
        crawlController.startNonBlocking(
                pdfCrawlerFactory,
                applicationProperties.getCrawler().getNumberOfCrawlers()
        );
    }

    public CrawlerStatus getStatus() {
        if (crawlController.isShuttingDown()) return CrawlerStatus.SHUTTING_DOWN;

        if (crawlController.isFinished()) return CrawlerStatus.FINISHED;
        else return CrawlerStatus.IN_PROGRESS;
    }
}
