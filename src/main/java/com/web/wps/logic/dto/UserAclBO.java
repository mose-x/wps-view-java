package com.web.wps.logic.dto;

import lombok.Data;

@Data
public class UserAclBO {

    private int rename = 0; //重命名权限，1为打开该权限，0为关闭该权限，默认为0
    private int history = 0; //历史版本权限，1为打开该权限，0为关闭该权限,默认为1

}
