package com.web.wps.util.upload.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.web.wps.propertie.OSSProperties;
import com.web.wps.util.file.FileType;
import com.web.wps.util.file.FileTypeJudge;
import com.web.wps.util.file.FileUtil;
import com.web.wps.util.upload.ResFileDTO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OSSUtil {

    @Autowired
    public OSSUtil(OSSProperties oss, FileTypeJudge fileTypeJudge) {
        this.oss = oss;
        this.fileTypeJudge = fileTypeJudge;
    }

    private final OSSProperties oss;
    private final FileTypeJudge fileTypeJudge;

    private OSSClient getOSSClient(){
        return new OSSClient(oss.getEndpoint(),oss.getAccessKey(), oss.getAccessSecret());
    }

    public String simpleUploadFilePath(String filePath){
        File file = new File(filePath);
        return (this.simpleUploadFile(file));
    }

    public String simpleUploadFile(File file){
        String fileName = file.getName();
        String newFileName = FileUtil.makeNewFileName(fileName);
        this.getOSSClient().putObject(oss.getBucketName(), oss.getDiskName() + newFileName, file);
        return (oss.getFileUrlPrefix() + oss.getDiskName() + newFileName);
    }

    public String simpleUploadMultipartFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String newFileName = FileUtil.makeNewFileName(fileName);
        try {
            this.getOSSClient().putObject(oss.getBucketName(), oss.getDiskName() + newFileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (oss.getFileUrlPrefix() + oss.getDiskName() + newFileName);
    }

    public ResFileDTO uploadMultipartFile(MultipartFile file){
        String fileName = file.getOriginalFilename();
        InputStream inputStream;
        ResFileDTO o = new ResFileDTO();
        String fileType ;
        long fileSize = file.getSize();
        try {
            inputStream = file.getInputStream();
            FileType type = fileTypeJudge.getType(inputStream);

            if(type == null || "null".equals(type.toString()) ||
                    "XLS_DOC".equals(type.toString())|| "XLSX_DOCX".equals(type.toString()) ||
                    "WPSUSER".equals(type.toString())|| "WPS".equals(type.toString())){
                fileType = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            }else{
                fileType = type.toString().toLowerCase();
            }

        } catch (Exception e) {
            e.printStackTrace();
            //用户上传的文件类型为空，并且通过二进制流获取不到文件类型，因为二进制流只列举了常用的
            fileType = "";
        }

        try {
            o = this.uploadDetailInputStream(file.getInputStream(),fileName,fileType,fileSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    public ResFileDTO uploadDetailInputStream (InputStream in, String fileName , String fileType, long fileSize) {

        String uuidFileName = FileUtil.getFileUUIDName(fileName,fileType);

        String fileUrl = oss.getFileUrlPrefix() + oss.getDiskName() + uuidFileName;

        String md5key = this.uploadFile(in, uuidFileName, fileSize, oss.getBucketName(),
                oss.getDiskName(),fileName);
        ResFileDTO o = new ResFileDTO();

        if(md5key != null){
            o.setFileType(fileType);
            o.setFileName(fileName);
            o.setCFileName(uuidFileName);
            o.setFileUrl(fileUrl);
            o.setFileSize(fileSize);
            o.setMd5key(md5key);
        }
        return o;
    }

    /**
     * 向阿里云的OSS存储中存储文件  --file也可以用InputStream替代
     * @param bucketName bucket名称
     * @param diskName 上传文件的目录  --bucket下文件的路径
     * @return String 唯一MD5数字签名
     * */
    public String uploadFile(InputStream inputStream, String fileName,long fileSize,String bucketName, String diskName,String localFileName) {
        String resultStr = null;
        try {
            OSSClient client =  this.getOSSClient();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(inputStream.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            if(StringUtils.isEmpty(localFileName)){
                metadata.setContentDisposition("filename=" + fileName);
            }else{
                metadata.setContentDisposition("filename=" + localFileName);
            }
            //上传文件
            PutObjectResult putResult = client.putObject(bucketName, diskName + fileName, inputStream, metadata);
            //解析结果
            resultStr = putResult.getETag();
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传阿里云OSS服务器异常:"+e.getMessage());
            //LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 修改文件元信息
     * @param fileName   原文件名
     * @param bucketName 桶名
     * @param downName   下载时的文件名称
     */
    public void changeFileMetaInfo(String fileName,String bucketName,String downName){
        try{
            // 创建OSSClient实例。
            OSS ossClient = this.getOSSClient();
            // 设置源文件与目标文件相同，调用ossClient.copyObject方法修改文件元信息。
            CopyObjectRequest request = new CopyObjectRequest(bucketName, fileName, bucketName, fileName);
            ObjectMetadata meta = new ObjectMetadata();
            // 指定上传的内容类型。内容类型决定浏览器将以什么形式、什么编码读取文件。如果没有指定则根据文件的扩展名生成，如果没有扩展名则为默认值application/octet-stream。
            meta.setContentType(getContentType(fileName));
            // 设置内容被下载时的名称。
            System.out.println("downNmme"+downName);
            meta.setContentDisposition("filename="+downName);
            // 设置内容被下载时网页的缓存行为。
            meta.setCacheControl("no-cache");
            // 设置缓存过期时间，格式是格林威治时间（GMT）。
            meta.setExpirationTime(com.aliyun.oss.common.utils.DateUtil.parseIso8601Date("2022-10-12T00:00:00.000Z"));
            // 设置内容被下载时的编码格式。
            meta.setContentEncoding("utf-8");
            request.setNewObjectMetadata(meta);

            //修改元信息。
            ossClient.copyObject(request);

            // 关闭OSSClient。
            ossClient.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 生成下载的url
     * @param fileName wjm
     * @return URL URL
     */
    public URL getDownLoadUrl(String fileName, String bucketName){
        // 创建OSSClient实例。
        OSS ossClient = this.getOSSClient();
        //设置链接时效60分钟
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MINUTE, 60);

        Date date= c.getTime();
        GeneratePresignedUrlRequest request=new GeneratePresignedUrlRequest(bucketName,fileName, HttpMethod.GET);
        request.addHeader("policy","");
        request.setExpiration(date);

        Map<String,String> map=new HashMap<String, String>();
        map.put("response-content-disposition","attachment");
        request.setQueryParameter(map);
        URL url=ossClient.generatePresignedUrl(request);
        System.out.println(url);
        return url;
    }

    /**
     * 新建Bucket  --Bucket权限:私有
     * @param bucketName bucket名称
     * @return true 新建Bucket成功
     * */
    public static boolean createBucket(OSSClient client, String bucketName){
        Bucket bucket = client.createBucket(bucketName);
        return bucketName.equals(bucket.getName());
    }

    /**
     * 删除Bucket
     * @param bucketName bucket名称
     * */
    public static void deleteBucket(OSSClient client, String bucketName){
        client.deleteBucket(bucketName);
        System.out.println("删除Bucket成功："+bucketName);
        //  logger.info("删除" + bucketName + "Bucket成功");
    }

    /**
     * 向阿里云的OSS存储中存储文件  --file也可以用InputStream替代
     * @param file 上传文件
     * @param bucketName bucket名称
     * @param diskName 上传文件的目录  --bucket下文件的路径
     * @return String 唯一MD5数字签名
     * */
    public String uploadObject(File file, String bucketName, String diskName) {
        String resultStr = null;
        try {
            OSSClient client =  this.getOSSClient();
            InputStream is = new FileInputStream(file);
            String fileName = file.getName();
            long fileSize = file.length();
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(is.available());
            metadata.setCacheControl("no-cache");
            metadata.setHeader("Pragma", "no-cache");
            metadata.setContentEncoding("utf-8");
            metadata.setContentType(getContentType(fileName));
            metadata.setContentDisposition("filename=" + fileName);
            //上传文件
            PutObjectResult putResult = client.putObject(bucketName, diskName + fileName, is, metadata);
            //解析结果
            resultStr = putResult.getETag();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传阿里云OSS服务器异常:"+e.getMessage());
            //LOG.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * 向阿里云的OSS存储中分片存储文件
     * @param bucketName bucket名称
     */
    public String multipartUploadObject(String bucketName, String key,File partFile) {
        String tag = null;
        String uploadid = null;
        int j=0;
        // 初始化一个OSSClient
        OSSClient client =  this.getOSSClient();
        ListMultipartUploadsRequest lmur = new ListMultipartUploadsRequest(bucketName);
        // 获取Bucket内所有上传事件
        MultipartUploadListing listing = client.listMultipartUploads(lmur);
        // 新建一个List保存每个分块上传后的ETag和PartNumber
        List<PartETag> partETags = new ArrayList<PartETag>();
        // 遍历所有上传事件  设置UploadId
        for (MultipartUpload multipartUpload : listing.getMultipartUploads()) {
            if (multipartUpload.getKey().equals(key)) {
                uploadid=multipartUpload.getUploadId();
                break;
            }
        }
        if(StringUtils.isEmpty(uploadid)){
            // 开始Multipart Upload,InitiateMultipartUploadRequest 来指定上传Object的名字和所属Bucke
            InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
            InitiateMultipartUploadResult initiateMultipartUploadResult = client.initiateMultipartUpload(initiateMultipartUploadRequest);
            uploadid=initiateMultipartUploadResult.getUploadId();
        }else{
            ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName,key, uploadid);
            //listParts 方法获取某个上传事件所有已上传的块
            PartListing partListing = client.listParts(listPartsRequest);
            // 遍历所有Part
            for (PartSummary part : partListing.getParts()) {
                partETags.add(new PartETag(part.getPartNumber(),part.getETag()));
                j++;
            }
        }
        // 设置每块为 5M（最小支持5M）
        final int partSize = 1024 * 1024 * 5;
        // 计算分块数目
        int partCount = (int) (partFile.length() / partSize);
        if (partFile.length() % partSize != 0) {
            partCount++;
        }
        try {
            for (int i=j ; i < partCount; i++) {
                // 获取文件流
                FileInputStream fis;
                fis = new FileInputStream(partFile);
                // 跳到每个分块的开头
                long skipBytes = partSize * i;
                fis.skip(skipBytes);
                // 计算每个分块的大小
                long size = partSize < partFile.length() - skipBytes ? partSize: partFile.length() - skipBytes;
                // 创建UploadPartRequest，上传分块
                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(uploadid);
                uploadPartRequest.setInputStream(fis);
                uploadPartRequest.setPartSize(size);
                uploadPartRequest.setPartNumber(i + 1);
                UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                // 将返回的PartETag保存到List中。
                partETags.add(uploadPartResult.getPartETag());
                // 关闭文件
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadid, partETags);
        // 完成分块上传
        CompleteMultipartUploadResult completeMultipartUploadResult = client.completeMultipartUpload(completeMultipartUploadRequest);
        // 打印Object的ETag（返回的ETag不是md5.具体是什么不详）
        tag = completeMultipartUploadResult.getETag();
        return tag;
    }



    /**
     * 从阿里云的OSS存储中下载文件  --file也可以用InputStream替代
     * @param bucketName bucket名称
     * @param diskName 上传文件的目录  --bucket下文件的路径
     * @return String 唯一MD5数字签名
     * */
    public String downloadFile(String bucketName, String diskName,String filePath) {
        String resultStr = null;
        try {
            OSSClient client =  this.getOSSClient();
            client.getObject(new GetObjectRequest(bucketName, diskName), new File(filePath));
            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传阿里云OSS服务器异常:"+e.getMessage());
        }
        return resultStr;
    }
    /**
     * 根据key获取OSS服务器上的文件输入流
     * @param client OSS客户端
     * @param bucketName bucket名称
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public static InputStream getInputStream(OSSClient client, String bucketName, String diskName, String key){
        OSSObject ossObj = client.getObject(bucketName, diskName + key);
        return ossObj.getObjectContent();
    }

    /**
     * 根据key删除OSS服务器上的文件
     * @param bucketName bucket名称
     * @param diskName 文件路径
     * @param key Bucket下的文件的路径名+文件名
     */
    public void deleteFile( String bucketName, String diskName, String key){
        OSSClient client =  this.getOSSClient();
        client.deleteObject(bucketName, diskName + key);
        client.shutdown();
        System.out.println("删除" + bucketName + "下的文件" + diskName + key + "成功");
    }

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public static String getContentType(String fileName){
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        if("bmp".equalsIgnoreCase(fileExtension)) return "image/bmp";
        if("gif".equalsIgnoreCase(fileExtension)) return "image/gif";
        if("jpeg".equalsIgnoreCase(fileExtension) || "jpg".equalsIgnoreCase(fileExtension)  || "png".equalsIgnoreCase(fileExtension) ) return "image/jpeg";
        if("html".equalsIgnoreCase(fileExtension)) return "text/html";
        if("txt".equalsIgnoreCase(fileExtension)) return "text/plain";
        if("vsd".equalsIgnoreCase(fileExtension)) return "application/vnd.visio";
        if("ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) return "application/x-ppt";
        if("xls".equalsIgnoreCase(fileExtension) || "xlsx".equalsIgnoreCase(fileExtension)) return "application/x-xls";
        if("doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) return "application/x-docx";
        if("xml".equalsIgnoreCase(fileExtension)) return "text/xml";
        if("pdf".equalsIgnoreCase(fileExtension)) return "application/pdf";
        if("mp3".equalsIgnoreCase(fileExtension)) return "audio/mp3";
        if("mp4".equalsIgnoreCase(fileExtension)) return "video/mpeg4";
        return "text/html";
    }

}
