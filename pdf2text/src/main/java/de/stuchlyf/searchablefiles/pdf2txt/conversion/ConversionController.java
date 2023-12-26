package de.stuchlyf.searchablefiles.pdf2txt.conversion;

import de.stuchlyf.searchablefiles.common.IngestionFile;
import de.stuchlyf.searchablefiles.common.SaveFile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("conversion-service")
public record ConversionController(
        ConversionService conversionService
) {
    @PostMapping("convert")
    public ResponseEntity<IngestionFile> convert(@RequestBody SaveFile saveFile) {
        final var conversionResult = conversionService.convert(saveFile);

        return conversionResult
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
