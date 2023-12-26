package de.stuchlyf.crawler.savefile;


import de.stuchlyf.searchablefiles.common.SaveFile;

@FunctionalInterface
public interface SaveFileStrategy {

    enum Strategies {
        SAVE_FILE_TO_FS,
        SEND_FILE_TO_SAVE_SERVICE
    }

    void save(SaveFile file);
}
