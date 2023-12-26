package de.stuchlyf.savefileservice.conversion;

import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;

@FunctionalInterface
public interface ConversionStrategy {

    IngestionFile convert(SaveFile saveFile);
}
