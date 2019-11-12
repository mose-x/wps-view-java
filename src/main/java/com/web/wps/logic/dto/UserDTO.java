package com.web.wps.logic.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private String id = "-1";
    private String name = " ";
    private String permission = "read";
    private String avatar_url = "";

    public UserDTO(){super();}

    public UserDTO(String id, String name, String permission, String avatar_url) {
        this.id = id;
        this.name = name;
        this.permission = permission;
        this.avatar_url = avatar_url;
    }
}
