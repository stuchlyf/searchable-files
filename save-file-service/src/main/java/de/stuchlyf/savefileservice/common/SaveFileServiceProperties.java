package de.stuchlyf.savefileservice.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.NONE)
@ConfigurationProperties(prefix = "save-file-service")
public class SaveFileServiceProperties {

    private OpensearchAdapter opensearchAdapter;
    private PDF2TXTAdapter pdf2txtAdapter;

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.NONE)
    public static class OpensearchAdapter {
        private String scheme = "http";
        private String host;
        private int port = 9200;

        private String contextPath = "";

        private String ingestionPath = "";
        private String healthPath = "";
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.NONE)
    public static class PDF2TXTAdapter {
        private String scheme = "http";
        private String host;
        private int port = 9200;

        private String contextPath = "";

        private String conversionPath = "";
        private String healthPath = "";
    }
}
