package com.web.wps.base;

import javax.servlet.http.HttpServletRequest;

public class Request {

    public static String getHeaderParam(HttpServletRequest request,String key) {
        return request.getHeader(key);
    }

    public static String getQueryParam(HttpServletRequest request,String key) {
        return request.getParameter(key);
    }

}
