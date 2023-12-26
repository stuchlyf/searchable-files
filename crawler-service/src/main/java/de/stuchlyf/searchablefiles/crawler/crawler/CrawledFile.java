package de.stuchlyf.searchablefiles.crawler.crawler;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("CrawledFile")
public record CrawledFile(
        @Id
        String path
) {
}
