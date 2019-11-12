package com.web.wps.logic.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WatermarkBO {

    private Integer type = 0;
    private String value = "";
    private String fillstyle = "rgba( 192, 192, 192, 0.6 )";
    private String font = "bold 20px Serif";
    private BigDecimal rotate = new BigDecimal("0");
    private Integer horizontal = 50;
    private Integer vertical = 50;

}
