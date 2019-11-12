package com.web.wps.config;

import java.util.HashMap;
import java.util.Map;

public class Context {

    // wps bi传递的参数，统一放在上下文中，方便使用
    final static String TOKEN_KEY = "x-wps-weboffice-token";
    final static String FILE_ID_KEY = "x-weboffice-file-id";
    final static String USER_AGENT = "User-Agent";

    final static String APP_ID = "_w_appid";
    final static String SIGNATURE = "_w_signature";

    private static final ThreadLocal<Map<String, String>> securityMap = new ThreadLocal<>();

    private Context() {}

    private static void init() {
        if (securityMap.get() == null) {
            Map<String, String> map = new HashMap<>();
            securityMap.set(map);
        } else {
            securityMap.get().clear();
        }
    }

    static void setToken(String tokenKey) {
        init();
        securityMap.get().put(TOKEN_KEY, tokenKey);
    }

    public static String getToken() {
        if (securityMap.get() == null)
            return null;
        else
            return securityMap.get().get(TOKEN_KEY);
    }

    static void setFileId(String fileId) {
        securityMap.get().put(FILE_ID_KEY, fileId);
    }

    public static String getFileId() {
        if (securityMap.get() == null)
            return null;
        else
            return securityMap.get().get(FILE_ID_KEY);
    }

    static void setAgent(String agent) {
        securityMap.get().put(USER_AGENT, agent);
    }

    public static String getAgent() {
        if (securityMap.get() == null)
            return null;
        else
            return securityMap.get().get(USER_AGENT);
    }

    static void setAppId(String appId) {
        securityMap.get().put(APP_ID, appId);
    }

    public static String getAppId() {
        if (securityMap.get() == null)
            return null;
        else
            return securityMap.get().get(APP_ID);
    }

    static void setSignature(String signature) {
        securityMap.get().put(SIGNATURE, signature);
    }

    public static String getSignature() {
        if (securityMap.get() == null)
            return null;
        else
            return securityMap.get().get(SIGNATURE);
    }

}
