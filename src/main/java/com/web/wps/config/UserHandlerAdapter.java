package com.web.wps.config;

import com.web.wps.base.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 拦截器
 */

@Component
public class UserHandlerAdapter extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(UserHandlerAdapter.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler){

        String token = Request.getHeaderParam(request, Context.TOKEN_KEY);
        String fileId = Request.getHeaderParam(request,Context.FILE_ID_KEY);
        String agent = Request.getHeaderParam(request,Context.USER_AGENT);

        String appId = Request.getQueryParam(request,Context.APP_ID);
        String signature = Request.getQueryParam(request,Context.SIGNATURE);

        logger.info("_w_agent:{}",agent);

        String uri = request.getRequestURI();
        logger.info("request whole uri:{}", uri);

        Context.setToken(token);
        Context.setFileId(fileId);
        Context.setAgent(agent);
        Context.setAppId(appId);
        Context.setSignature(signature);

        // 当是用户自定义接口，直接通过(有点废话，举个例子而已)
        if (checkUri(request,response)){
            return true;
        }else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return false;
        }

        // 如果是三方回调接口，则需校验
        // 这里可以增加 处理token的校验，如果开启强制token,通过传递的signature以及相应的参数，再次生成该token数值，然后进行对比即可
//        return true;
    }

    private boolean checkUri(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
        String uri = httpServletRequest.getRequestURI();
        return PassUrl.checkCode(uri.replace("/", "_"));
    }

}
