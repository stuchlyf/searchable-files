package de.stuchlyf.searchablefiles.crawler.crawler;

import de.stuchlyf.searchablefiles.crawler.common.CrawlerServiceProperties;
import de.stuchlyf.searchablefiles.crawler.crawler.exception.FileAlreadyDownloadedException;
import de.stuchlyf.searchablefiles.crawler.savefile.SaveFileStrategy;
import de.stuchlyf.searchablefiles.common.SaveFile;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.util.List;

@Slf4j
public class PdfCrawler extends WebCrawler {

    private final CrawlerServiceProperties.Crawler crawlerProperties;

    private final CrawledFileRepository crawledFileRepository;

    private final List<SaveFileStrategy> saveFileStrategies;

    public PdfCrawler(
            CrawlerServiceProperties.Crawler crawlerProperties,
            CrawledFileRepository crawledFileRepository,
            List<SaveFileStrategy> saveFileStrategies
    ) {
        this.crawlerProperties = crawlerProperties;
        this.crawledFileRepository = crawledFileRepository;
        this.saveFileStrategies = saveFileStrategies;
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        final var urlString = url.getURL().toLowerCase();
        if (crawledFileRepository.existsById(url.getPath())) {
            log.info("URL {} already visited, continuing...", url.getPath());
            return false;
        }

        return urlString.startsWith(crawlerProperties.getStartingSeedUrl());
    }

    @Override
    public void visit(Page page) {
        if (!MediaType.APPLICATION_PDF_VALUE.equals(page.getContentType())) return;

        final var urlPath = page.getWebURL().getPath();
        try {
            if (crawledFileRepository.existsById(urlPath)) throw new FileAlreadyDownloadedException();

            saveFileStrategies.forEach(saveFileStrategy ->
                    saveFileStrategy.save(new SaveFile(urlPath, MediaType.APPLICATION_PDF_VALUE, page.getContentData()))
            );

            final var crawledFile = new CrawledFile(urlPath);
            crawledFileRepository.save(crawledFile);
        } catch (FileAlreadyDownloadedException e) {
            log.warn("File already downloaded, won't continue.");
        }
    }
}
