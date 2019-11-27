package com.web.wps.logic.controller;

import com.web.wps.base.BaseController;
import com.web.wps.base.Response;
import com.web.wps.logic.dto.FileListDTO;
import com.web.wps.logic.entity.FileEntity;
import com.web.wps.logic.service.FileService;
import com.web.wps.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zm
 * 用户实现获取wps可预览URL
 */
@RestController
@RequestMapping("v1/api/file")
public class UserImplController extends BaseController {

    @Autowired
    public UserImplController(FileService fileService) {
        this.fileService = fileService;
    }

    private final FileService fileService;

    /**
     * 获取网络文件预览URL
     * @param fileUrl fileUrl
     * @return t
     */
    @GetMapping("getViewUrlWebPath")
    public ResponseEntity<Object> getViewUrlWebPath(String fileUrl){
        logger.info("getViewUrlWebPath：fileUrl={}",fileUrl);
        Token t = fileService.getViewUrl(fileUrl,true);
        return Response.success(t);
    }

    /**
     * 获取所有文件
     * @return db下的所有文件
     */
    @GetMapping("getFileList")
    public ResponseEntity<Object> getFileList(){
        logger.info("获取所有文件");
        List<FileListDTO> result = fileService.getFileList();
        return Response.success(result);
    }

    /**
     * 通过fileId获取wpsUrl以及token
     * @param fileId 文件id
     * @return token（包含url）
     */
    @GetMapping("getViewUrlDbPath")
    public ResponseEntity<Object> getViewUrlDbPath(String fileId,String userId){
        logger.info("getViewUrlDbPath：fileId={},userId={}",fileId,userId);
        Token t = fileService.getViewUrl(fileId,userId,true);
        if (t != null){
            return Response.success(t);
        }else {
            return Response.bad("文件不存在或其它异常！");
        }
    }

}
