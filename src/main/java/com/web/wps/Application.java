package com.web.wps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// 防止xml xxE xxd 注入
		DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
		dbf.setExpandEntityReferences(false);
		String FEATURE ;
		try {
			FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
			dbf.setFeature(FEATURE, true);
			FEATURE = "http://xml.org/sax/features/external-general-entities";
			dbf.setFeature(FEATURE, false);
			FEATURE = "http://xml.org/sax/features/external-parameter-entities";
			dbf.setFeature(FEATURE, false);
			FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
			dbf.setFeature(FEATURE, false);
			dbf.setXIncludeAware(false);
			dbf.setExpandEntityReferences(false);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		SpringApplication.run(Application.class, args);
	}

}
