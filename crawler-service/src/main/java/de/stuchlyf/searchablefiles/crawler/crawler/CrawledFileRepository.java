package de.stuchlyf.searchablefiles.crawler.crawler;

import org.springframework.data.repository.CrudRepository;

public interface CrawledFileRepository extends CrudRepository<CrawledFile, String> {
}
