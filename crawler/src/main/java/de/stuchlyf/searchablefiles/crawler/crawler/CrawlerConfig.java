package de.stuchlyf.searchablefiles.crawler.crawler;

import de.stuchlyf.searchablefiles.crawler.common.CrawlerServiceProperties;
import de.stuchlyf.searchablefiles.crawler.savefile.SaveFileStrategy;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CrawlerConfig {

    @Bean
    public CrawlConfig crawlConfig(CrawlerServiceProperties applicationProperties) {
        final var config = new CrawlConfig();

        final var storageFolder = applicationProperties.getCrawler().getDbStorageFolder();
        config.setCrawlStorageFolder(storageFolder.getAbsolutePath());
        config.setIncludeBinaryContentInCrawling(true);
        config.setMaxDepthOfCrawling(applicationProperties.getCrawler().getMaxDepthOfCrawling());
        config.setMaxDownloadSize(applicationProperties.getCrawler().getMaxDownloadSize());
        config.setMaxPagesToFetch(applicationProperties.getCrawler().getMaxPagesToFetch());

        return config;
    }

    @Bean
    public RobotstxtConfig robotstxtConfig() {
        final var robotstxtConfig = new RobotstxtConfig();
        robotstxtConfig.setEnabled(false);
        return robotstxtConfig;
    }

    @Bean
    public PageFetcher pageFetcher(CrawlConfig crawlConfig) {
        return new PageFetcher(crawlConfig);
    }

    @Bean
    public RobotstxtServer robotstxtServer(RobotstxtConfig robotstxtConfig, PageFetcher pageFetcher) {
        return new RobotstxtServer(robotstxtConfig, pageFetcher);
    }

    @Bean
    public CrawlController.WebCrawlerFactory<PdfCrawler> pdfCrawlerFactory(
            CrawlerServiceProperties applicationProperties,
            CrawledFileRepository crawledFileRepository,
            List<SaveFileStrategy> saveFileStrategies
    ) {
        return () -> new PdfCrawler(applicationProperties.getCrawler(), crawledFileRepository, saveFileStrategies);
    }

    @Bean
    public CrawlController crawlController(
            CrawlerServiceProperties applicationProperties,
            CrawlConfig crawlConfig,
            PageFetcher pageFetcher,
            RobotstxtServer robotstxtServer
    ) throws Exception {
        final var controller = new CrawlController(crawlConfig, pageFetcher, robotstxtServer);
        controller.addSeed(applicationProperties.getCrawler().getStartingSeedUrl());

        return controller;
    }
}
