package com.web.wps.util;

import com.web.wps.propertie.WpsProperties;
import com.web.wps.util.file.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WpsUtil {

    private final WpsProperties wpsProperties;

    @Autowired
    public WpsUtil(WpsProperties wpsProperties) {
        this.wpsProperties = wpsProperties;
    }

    public String getWpsUrl(Map<String,String> values,String fileType,String fileId){
        String keyValueStr = SignatureUtil.getKeyValueStr(values);
        String signature = SignatureUtil.getSignature(values, wpsProperties.getAppsecret());
        String fileTypeCode = FileUtil.getFileTypeCode(fileType);

        return wpsProperties.getDomain() + fileTypeCode + "/" + fileId + "?"
                + keyValueStr + "_w_signature=" + signature;
    }

    public String getTemplateWpsUrl(String fileType,String userId){
        Map<String,String> values = new HashMap<String,String>(){
            {
                put("_w_appid", wpsProperties.getAppid());
                put("_w_userid", userId);
            }
        };
        String keyValueStr = SignatureUtil.getKeyValueStr(values);
        String signature = SignatureUtil.getSignature(values, wpsProperties.getAppsecret());
        String fileTypeCode = FileUtil.getTypeCode(fileType);

        return wpsProperties.getDomain() + fileTypeCode + "/new/0" + "?"
                + keyValueStr + "_w_signature=" + signature;
    }

}
