package com.web.wps.util;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.HmacUtils;


public class SignatureUtil {

    public static String getKeyValueStr(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(){
            {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    add(entry.getKey());
                }
            }
        };
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = params.get(key) + "&";
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    public static String getSignature(Map<String, String> params, String appSecret) {
        List<String> keys = new ArrayList<String>(){
            {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    add(entry.getKey());
                }
            }
        };

        // 将所有参数按key的升序排序
        keys.sort(String::compareTo);

        // 构造签名的源字符串
        StringBuilder contents = new StringBuilder();
        for (String key : keys) {
            if (key.equals("_w_signature")) {
                continue;
            }
            contents.append(key).append("=").append(params.get(key));
            System.out.println("key:" + key + ",value:" + params.get(key));
        }
        contents.append("_w_secretkey=").append(appSecret);

        System.out.println(appSecret);
        System.out.println(contents.toString());

        // 进行hmac sha1 签名
        byte[] bytes = HmacUtils.hmacSha1(appSecret.getBytes(), contents.toString().getBytes());

        //字符串经过Base64编码
        String sign = encodeBase64String(bytes);
        System.out.println(sign);
        try {
            return URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> paramToMap(String paramStr) {
        String[] params = paramStr.split("&");
        return new HashMap<String, String>(){
            {
                for (String param1 : params) {
                    String[] param = param1.split("=");
                    if (param.length >= 2) {
                        String key = param[0];
                        StringBuilder value = new StringBuilder(param[1]);
                        for (int j = 2; j < param.length; j++) {
                            value.append("=").append(param[j]);
                        }
                        put(key, value.toString());
                    }
                }
            }
        };
    }

}
