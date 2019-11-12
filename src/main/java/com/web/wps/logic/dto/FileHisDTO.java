package com.web.wps.logic.dto;

import lombok.Data;

@Data
public class FileHisDTO {

    private String id;
    private String name;
    private int version;
    private int size;
    private long create_time;
    private long modify_time;
    private String download_url;

    private UserDTO creator;
    private UserDTO modifier;

    public FileHisDTO(){super();}

    public FileHisDTO(String id, String name, int version, int size,
                      long create_time, long modify_time, String download_url,
                      UserDTO creator, UserDTO modifier) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.size = size;
        this.create_time = create_time;
        this.modify_time = modify_time;
        this.download_url = download_url;
        this.creator = creator;
        this.modifier = modifier;
    }
}
