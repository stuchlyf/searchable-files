package de.stuchlyf.searchablefiles.crawler.common;

import de.stuchlyf.searchablefiles.crawler.savefile.SaveFileStrategy;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.util.Set;

@Getter
@Setter
@ConfigurationProperties(prefix = "crawler-service")
@NoArgsConstructor(access = AccessLevel.NONE)
public class CrawlerServiceProperties {

    private Set<SaveFileStrategy.Strategies> saveFileStrategy;


    private Crawler crawler;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.NONE)
    public static class Crawler {
        private File dbStorageFolder;
        private File outputStorageFolder;

        private String baseSeedUrl;
        private String startingSeedUrl;

        private int numberOfCrawlers = 1;

        private int maxDepthOfCrawling = 1;
        private int maxDownloadSize = 1048576;
        private int maxPagesToFetch = -1;
    }
}
