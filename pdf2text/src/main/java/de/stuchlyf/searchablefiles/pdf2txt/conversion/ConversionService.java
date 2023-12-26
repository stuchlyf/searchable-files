package de.stuchlyf.searchablefiles.pdf2txt.conversion;

import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.RandomAccessRead;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public record ConversionService() {

    public Optional<IngestionFile> convert(SaveFile saveFile) {
        final var fileContents = saveFile.contents();

        try {
            final var parser = new PDFParser(new RandomAccessReadBuffer(fileContents));
            final var document = parser.parse();
            final var pdfStripper = new PDFTextStripper();
            final var textInPdf = pdfStripper.getText(document);

            log.info("Successfully parsed PDF with path {}", saveFile.path());

            return Optional.of(new IngestionFile(saveFile.path(), textInPdf));
        } catch (IOException e) {
            log.error("There was an Error while parsing the file with the path {}. Returning a empty optional.", saveFile.path());
            return Optional.empty();
        }
    }
}
