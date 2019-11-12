package com.web.wps.propertie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 属性文件
 * @author zm
 */
@Data
@Component
@ConfigurationProperties(prefix = "wps")
public class WpsProperties {

	private String domain;
	private String appid;
	private String appsecret;
	private String downloadHost;
	private String localDir;

}
