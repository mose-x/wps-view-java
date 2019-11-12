package com.web.wps.util;

import lombok.Data;

@Data
public class Token {

    private int expires_in;
    private String token;
    private String wpsUrl;

}
