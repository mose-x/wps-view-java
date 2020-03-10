package com.web.wps.util.oss;

import lombok.Data;

@Data
public class OSSDTO {

	private String fileUrl;
	private String fileName;
	private String ossFileName;
	private String fileType;
	private long fileSize;
	private String md5key;
	private String bookMark;

}
