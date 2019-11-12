package com.web.wps.logic.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.web.wps.base.BaseController;
import com.web.wps.base.Response;
import com.web.wps.logic.dto.FileReqDTO;
import com.web.wps.logic.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * @author zm
 * 文件相关回调接口
 */
@RestController
@RequestMapping("v1/3rd/file")
public class FileCallBackController extends BaseController {

    @Autowired
    public FileCallBackController(FileService fileService) {
        this.fileService = fileService;
    }

    private final FileService fileService;

    /**
     * 获取文件元数据
     */
    @GetMapping("info")
    public ResponseEntity<Object> getFileInfo(
            String _w_userid, String _w_filepath, String _w_filetype
    ){
        logger.info("获取文件元数据userId:{},path:{},type:{}",_w_userid,_w_filepath,_w_filetype);
        try {
            Map<String,Object> map =
                    fileService.getFileInfo(_w_userid,_w_filepath,_w_filetype);
            return Response.success(map);
        }catch (Exception e){
            return Response.bad("获取文件元数据异常");
        }
    }

    /**
     * 通知此文件目前有哪些人正在协作
     */
    @PostMapping("online")
    public ResponseEntity<Object> fileOnline(
            @RequestBody JSONObject obj
    ){
        logger.info("通知此文件目前有哪些人正在协作param:{}",JSON.toJSON(obj));
        return Response.success();
    }

    /**
     * 上传文件新版本
     */
    @PostMapping("save")
    public ResponseEntity<Object> fileSave(
            @RequestBody MultipartFile file,
            String _w_userid
    ){
        logger.info("上传文件新版本");
        Map<String,Object> map = fileService.fileSave(file,_w_userid);
        return Response.success(map);
    }

    /**
     * 获取特定版本的文件信息
     */
    @GetMapping("version/{version}")
    public ResponseEntity<Object> fileVersion(
            @PathVariable Integer version
    ){
        logger.info("获取特定版本的文件信息version:{}",version);
        Map<String,Object> res = fileService.fileVersion(version);
        return Response.success(res);
    }

    /**
     * 文件重命名
     */
    @PutMapping("rename")
    public ResponseEntity<Object> fileRename(
            @RequestBody FileReqDTO req,
            String _w_userid
    ){
        logger.info("文件重命名param:{},userId:{}",JSON.toJSON(req),_w_userid);
        fileService.fileRename(req.getName(),_w_userid);
        return Response.success();
    }

    /**
     * 获取所有历史版本文件信息
     */
    @PostMapping("history")
    public ResponseEntity<Object> fileHistory(
            @RequestBody FileReqDTO req
    ){
        logger.info("获取所有历史版本文件信息param:{}",JSON.toJSON(req));
        Map<String,Object> result = fileService.fileHistory(req);
        return Response.success(result);
    }

    /**
     * 新建文件
     */
    @PostMapping("new")
    public ResponseEntity<Object> fileNew(
            @RequestBody MultipartFile file,
            String _w_userid
    ){
        logger.info("新建文件_w_userid:{}",_w_userid);
        Map<String,Object> res = fileService.fileNew(file,_w_userid);
        return Response.success(res);
    }

    /**
     * 回调通知
     */
    @PostMapping("onnotify")
    public ResponseEntity<Object> onNotify(
            @RequestBody JSONObject obj
    ){
        logger.info("回调通知param:{}",JSON.toJSON(obj));
        // TODO
        // 返回数据暂不处理
        return Response.success();
    }

}
