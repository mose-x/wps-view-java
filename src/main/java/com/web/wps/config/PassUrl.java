package com.web.wps.config;

public enum PassUrl {

    v1_3rd_user,v1_3rd_file,v1_api_file;

    public static boolean checkCode(String code){
        for (PassUrl mode : PassUrl.values()){
            if (code.contains(mode.toString())){
                return true;
            }
        }
        return false;
    }


}
