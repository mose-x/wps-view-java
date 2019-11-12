package com.web.wps.logic.dto;

import lombok.Data;

@Data
public class FileNewDTO {

    private String redirect_url;
    private String user_id;

    public FileNewDTO(){super();}

    public FileNewDTO(String redirect_url, String user_id) {
        this.redirect_url = redirect_url;
        this.user_id = user_id;
    }
}
