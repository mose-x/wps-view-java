package com.web.wps.propertie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "qn")
public class QNProperties {

    private String url;
    private String bucket;
    private String accessKey;
    private String secretKey;
    private String diskName;

}
