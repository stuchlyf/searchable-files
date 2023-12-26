package de.stuchlyf.searchablefiles.savefileservice.conversion;

import de.stuchlyf.searchablefiles.savefileservice.pdf2txt.PDF2TXTAdapter;
import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component(value = MediaType.APPLICATION_PDF_VALUE)
@Qualifier(value = MediaType.APPLICATION_PDF_VALUE)
public record PdfConversionStrategy(
        PDF2TXTAdapter pdf2TXTAdapter
) implements ConversionStrategy {

    @Override
    public IngestionFile convert(SaveFile saveFile) {
        return pdf2TXTAdapter.convertPdfFileToText(saveFile);
    }
}
