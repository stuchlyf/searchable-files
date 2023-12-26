package de.stuchlyf.savefileservice.conversion;

import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component(value = MediaType.TEXT_PLAIN_VALUE)
@Qualifier(value = MediaType.TEXT_PLAIN_VALUE)
public record TextConversionStrategy() implements ConversionStrategy {
    @Override
    public IngestionFile convert(SaveFile saveFile) {
        final var contentsAsString = new String(saveFile.contents(), StandardCharsets.UTF_8);

        return new IngestionFile(saveFile.path(), contentsAsString);
    }
}
