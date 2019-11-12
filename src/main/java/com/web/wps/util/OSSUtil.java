package com.web.wps.util;

import com.aliyun.oss.OSSClient;
import com.web.wps.propertie.OSSProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

@Service
public class OSSUtil {

    @Autowired
    public OSSUtil(OSSProperties oss) {
        this.oss = oss;
    }

    private final OSSProperties oss;

    private OSSClient getOSSClient(){
        return new OSSClient(oss.getEndpoint(),oss.getAccessKey(), oss.getAccessSecret());
    }

    public String uploadFile2OSS(File file){
        String fileName = file.getName();
        String newFileName = this.makeNewFileName(fileName);
        this.getOSSClient().putObject(oss.getBucketName(), oss.getDiskName() + newFileName, file);
        return (oss.getFileUrlPrefix() + oss.getDiskName() + newFileName);
    }

    public String uploadStream2OSS(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String newFileName = this.makeNewFileName(fileName);
        try {
            this.getOSSClient().putObject(oss.getBucketName(), oss.getDiskName() + newFileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (oss.getFileUrlPrefix() + oss.getDiskName() + newFileName);
    }

    public String uploadFile2OSS(String filePath){
        File file = new File(filePath);
        return (this.uploadFile2OSS(file));
    }

    private String makeNewFileName(String oldFileName){
        String fileType = FileUtil.getFileTypeByName(oldFileName);
        String tempFileName = oldFileName.replace("."+fileType,"");
        Random ne = new Random();//实例化一个random的对象ne
        int uuid = ne.nextInt(9999-1000+1)+1000;//为变量赋随机值1000-9999
        return  tempFileName + uuid + "." + fileType;
    }

}
