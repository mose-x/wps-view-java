package com.web.wps.logic.controller;

import com.alibaba.fastjson.JSON;
import com.web.wps.base.BaseController;
import com.web.wps.base.Response;
import com.web.wps.logic.dto.FileListDTO;
import com.web.wps.logic.service.FileService;
import com.web.wps.util.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 获取所有文件
     * @return db下的所有文件
     */
    @PostMapping("getFileListByPage")
    public ResponseEntity<Object> getFileListByPage(
            @RequestBody com.web.wps.base.Page page
    ){
        logger.info("获取所有文件-分页:{}", JSON.toJSON(page));
        Page<FileListDTO> result = fileService.getFileListByPage(page);
        return Response.success(result);
    }

    /**
     * 删除用户文件
     * @return true Or false
     */
    @GetMapping("delFile")
    public ResponseEntity<Object> delFile(String id){
        logger.info("删除文件：{}",id);
        int res = fileService.delFile(id);
        if (res == 1){
            return Response.success(true,"删除成功");
        }else if (res == 0){
            return Response.success(false,"删除失败，该文件不允许被删除");
        }else {
            return Response.success(false,"数据异常");
        }
    }

    /**
     * 上传文件
     */
    @PostMapping("uploadFile")
    public ResponseEntity<Object> uploadFile(
            @RequestParam("file") MultipartFile file
    ){
        try {
            fileService.uploadFile(file);
            return Response.success(true,"上传成功");
        }catch (Exception e){
            e.printStackTrace();
            return Response.success(false,"上传失败");
        }
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

    /**
     * 通过wps官方模版新建文件
     * template值 {"word", "excel", "ppt"}
     */
    @GetMapping("createTemplateFile")
    public ResponseEntity<Object> createTemplateFile(
            String template
    ){
        Object newUrl = fileService.createTemplateFile(template);
        return Response.success(newUrl);
    }

}
