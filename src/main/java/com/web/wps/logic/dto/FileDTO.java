package com.web.wps.logic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileDTO {

    public FileDTO() {
        super();
    }

    public FileDTO(String id, String name, int version, int size, String creator,
                      long create_time, String download_url, UserAclBO user_acl, WatermarkBO watermark) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.size = size;
        this.creator = creator;
        this.create_time = create_time;
        this.download_url = download_url;
        this.user_acl = user_acl;
        this.watermark = watermark;
    }

    	/*
	id:  "132aa30a87064",                 //文件id,字符串长度小于40
    name : "example.doc",                //文件名
    version: 1,                        //当前版本号，位数小于11
    size: 200,                        //文件大小，单位为kb
    creator: "id0",                        //创建者id，字符串长度小于40
    create_time: 1136185445,            //创建时间，时间戳，单位为秒
    modifier: "id1000",                    //修改者id，字符串长度小于40
    modify_time: 1551409818,            //修改时间，时间戳，单位为秒
    download_url: "http://www.xxx.cn/v1/file?fid=f132aa30a87064",  //文档下载地址
    */

    private String id;
    private String name;
    private int version;
    private int size;
    private String creator;
    private String modifier;
    private long create_time;
    private long modify_time;
    private String download_url;

    private UserAclBO user_acl;
    private WatermarkBO watermark;

}

