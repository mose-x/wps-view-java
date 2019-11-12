package com.web.wps.logic.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zm
 * @date 2019.10.7
 */

@Data
public class FileListDTO {

    private String id;
    private String name;
    private int version;
    private int size;
    private String userId;
    private String creator;
    private String modifier;
    private String createTime;
    private String modifyTime;
    private String download_url;

    public FileListDTO(String id, String name, int version, int size, String creator,
                       String modifier, long createTime, long modifyTime, String download_url,String userId) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.size = size;
        this.creator = creator;
        this.modifier = modifier;
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(Long.valueOf(createTime)));
        this.modifyTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(Long.valueOf(modifyTime)));
        this.download_url = download_url;
        this.userId = userId;
    }
}
