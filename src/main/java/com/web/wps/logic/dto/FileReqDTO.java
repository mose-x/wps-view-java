package com.web.wps.logic.dto;

import lombok.Data;

@Data
public class FileReqDTO {

    // 重命名用
    private String name;

    // 历史信息
    private String id;
    private int offset = 0;
    private int count;

}
