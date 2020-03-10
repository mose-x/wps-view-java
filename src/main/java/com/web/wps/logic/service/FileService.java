package com.web.wps.logic.service;

import com.web.wps.base.BaseRepository;
import com.web.wps.base.BaseService;
import com.web.wps.config.Context;
import com.web.wps.logic.dto.*;
import com.web.wps.logic.entity.*;
import com.web.wps.logic.repository.FileRepository;
import com.web.wps.propertie.WpsProperties;
import com.web.wps.util.*;
import com.web.wps.util.file.FileUtil;
import com.web.wps.util.oss.OSSDTO;
import com.web.wps.util.oss.OSSUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;

@Service
public class FileService extends BaseService<FileEntity,String> {

    private final WpsUtil wpsUtil;
    private final WpsProperties wpsProperties;
    private final OSSUtil ossUtil;
    private final UserAclService userAclService;
    private final WatermarkService watermarkService;
    private final UserService userService;
    private final FileVersionService fileVersionService;

    @Autowired
    public FileService(WpsUtil wpsUtil, WpsProperties wpsProperties, OSSUtil ossUtil,
                       UserAclService userAclService, WatermarkService watermarkService,
                       UserService userService, FileVersionService fileVersionService) {
        this.wpsUtil = wpsUtil;
        this.wpsProperties = wpsProperties;
        this.ossUtil = ossUtil;
        this.userAclService = userAclService;
        this.watermarkService = watermarkService;
        this.userService = userService;
        this.fileVersionService = fileVersionService;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = FileRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public FileRepository getRepository(){
        return (FileRepository) this.baseRepository;
    }

    public Token getViewUrl(String fileUrl,boolean checkToken){
        Token t = new Token();

        String fileType = FileUtil.getFileTypeByPath(fileUrl);
        // fileId使用uuid保证出现同样的文件而是最新文件
        UUID randomUUID = UUID.randomUUID();
        String uuid = randomUUID.toString().replace("-","");

        Map<String,String> values = new HashMap<String,String>(){
            {
                put("_w_appid", wpsProperties.getAppid());
                if (checkToken){
                    put("_w_tokentype","1");
                }
                put("_w_userid", "-1");
                put("_w_filepath",fileUrl);
                put("_w_filetype","web");
            }
        };

        String wpsUrl = wpsUtil.getWpsUrl(values,fileType,uuid);

        t.setToken(uuid);
        t.setExpires_in(600);
        t.setWpsUrl(wpsUrl);

        return t;
    }

    public Token getViewUrl(String fileId,String userId,boolean checkToken){
        FileEntity fileEntity = this.findOne(fileId);
        if (fileEntity != null){
            Token t = new Token();
            String fileName = fileEntity.getName();
            String fileType = FileUtil.getFileTypeByName(fileName);

            UUID randomUUID = UUID.randomUUID();
            String uuid = randomUUID.toString().replace("-","");

            Map<String,String> values = new HashMap<String,String>(){
                {
                    put("_w_appid", wpsProperties.getAppid());
                    if (checkToken){
                        put("_w_tokentype","1");
                    }
                    put("_w_filepath",fileName);
                    put("_w_userid", userId);
                    put("_w_filetype","db");
                }
            };

            String wpsUrl = wpsUtil.getWpsUrl(values,fileType,fileEntity.getId());

            t.setToken(uuid);
            t.setExpires_in(600);
            t.setWpsUrl(wpsUrl);

            return t;
        }
        return null;
    }

    public Map<String,Object> getFileInfo(String userId, String filePath,String _w_filetype){
        if ("web".equalsIgnoreCase(_w_filetype)){
            return getWebFileInfo(filePath);
        }else if ("db".equalsIgnoreCase(_w_filetype)){
            return getDbFileInfo(userId);
        }
        return null;
    }

    private Map<String,Object> getWebFileInfo(String filePath){
        logger.info("_w_filepath:{}",filePath);

        // 构建默认user信息
        UserDTO wpsUser = new UserDTO(
                "-1","我","read","https://zmfiletest.oss-cn-hangzhou.aliyuncs.com/user0.png"
        );

        // 构建文件
        FileDTO file = new FileDTO(
                Context.getFileId(), FileUtil.getFileName(filePath),
                1,2835,"-1",new Date().getTime(), filePath,
                // 默认设置为无水印，只读权限
                new UserAclBO(),new WatermarkBO()
        );

        return new HashMap<String, Object>(){
            {
                put("file", file);
                put("user", wpsUser);
            }
        };
    }

    private Map<String,Object> getDbFileInfo(String userId){
        String fileId = Context.getFileId();

        // 获取文件信息
        FileEntity fileEntity = this.findOne(fileId);

        // 初始化文件读写权限为read
        String permission = "read";

        // 增加用户权限
        UserAclEntity userAclEntity = userAclService.getRepository().findFirstByFileIdAndUserId(fileId,userId);
        UserAclBO userAcl = new UserAclBO();
        if (userAclEntity != null){
            BeanUtils.copyProperties(userAclEntity,userAcl);
            permission = userAclEntity.getPermission();
        }

        // 增加水印
        WatermarkEntity watermarkEntity = watermarkService.getRepository().findFirstByFileId(fileId);
        WatermarkBO watermark = new WatermarkBO();
        if (watermarkEntity != null){
            BeanUtils.copyProperties(watermarkEntity,watermark);
        }

        //获取user
        UserEntity wpsUser = userService.findOne(userId);
        UserDTO user = new UserDTO();
        if (wpsUser != null){
            BeanUtils.copyProperties(wpsUser,user);
            user.setPermission(permission);
        }

        // 构建fileInfo
        FileDTO file = new FileDTO();
        BeanUtils.copyProperties(fileEntity,file);
        file.setUser_acl(userAcl);
        file.setWatermark(watermark);

        return new HashMap<String, Object>(){
            {
                put("file", file);
                put("user", user);
            }
        };
    }

    public void fileRename(String fileName,String userId){
        String fileId = Context.getFileId();
        FileEntity file = this.findOne(fileId);
        if (file != null){
            file.setName(fileName);
            file.setModifier(userId);
            Date date = new Date();
            file.setModify_time(date.getTime());
            this.update(file);
        }
    }

    public Map<String,Object> fileNew(MultipartFile file, String userId){
        OSSDTO ossdto = ossUtil.uploadMultipartFile2OSS(file);
        String fileName = ossdto.getFileName();
        String fileUrl = ossdto.getFileUrl();
        int fileSize = (int) file.getSize();
        Date date = new Date();
        long dataTime = date.getTime();
        // 保存文件
        FileEntity f = new FileEntity(fileName,1,fileSize,userId,userId,dataTime,dataTime,fileUrl);
        this.save(f);

        // 处理权限
        userAclService.saveUserFileAcl(userId,f.getId());

        // 处理水印
        watermarkService.saveWatermark(f.getId());

        // 处理返回
        Map<String,Object> map = new HashMap<>();
        map.put("redirect_url",this.getViewUrl(f.getId(),userId,false).getWpsUrl());
        map.put("user_id",userId);
        return map;
    }

    public Map<String,Object> fileHistory(FileReqDTO req){
        List<FileHisDTO> result = new ArrayList<>(1);
        if (req.getId() != null){
            // 目前先实现获取所有的历史记录
            List<FileVersionEntity> versionList =
                    fileVersionService.getRepository().findByFileIdOrderByVersionDesc(req.getId());
            if (versionList != null && versionList.size()>0){
                Set<String> userIdSet = new HashSet<>();
                for (FileVersionEntity fileVersion : versionList){
                    userIdSet.add(fileVersion.getModifier());
                    userIdSet.add(fileVersion.getCreator());
                }
                List<String> userIdList = new ArrayList<>(userIdSet);
                // 获取所有关联的人
                List<UserEntity> userList = userService.getRepository().findByIdIn(userIdList);

                if (userList != null && userList.size()>0){
                    for (FileVersionEntity fileVersion : versionList){
                        FileHisDTO fileHis = new FileHisDTO();
                        BeanUtils.copyProperties(fileVersion,fileHis);
                        fileHis.setId(fileVersion.getFileId());
                        UserDTO creator = new UserDTO();
                        UserDTO modifier = new UserDTO();
                        for (UserEntity user : userList){
                            if (user.getId().equals(fileVersion.getCreator())){
                                BeanUtils.copyProperties(user,creator);
                            }
                            if (user.getId().equals(fileVersion.getModifier())){
                                BeanUtils.copyProperties(user,modifier);
                            }
                        }
                        fileHis.setModifier(modifier);
                        fileHis.setCreator(creator);
                        result.add(fileHis);
                    }
                }
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("histories",result);
        return map;
    }

    public Map<String,Object> fileSave(MultipartFile mFile,String userId){
        Date date = new Date();
        // 上传oss
        OSSDTO ossdto = ossUtil.uploadMultipartFile2OSS(mFile);
        int size = (int) ossdto.getFileSize();

        String fileId = Context.getFileId();
        FileEntity file = this.findOne(fileId);
        FileDTO fileInfo = new FileDTO();

        String oldFileUrl = file.getDownload_url();

        // 更新当前版本
        file.setVersion(file.getVersion() + 1);
        file.setDownload_url(ossdto.getFileUrl());
        file.setModifier(userId);
        file.setModify_time(date.getTime());
        file.setSize(size);
        this.update(file);

        // 保存历史版本
        FileVersionEntity fileVersion = new FileVersionEntity();
        BeanUtils.copyProperties(file,fileVersion);
        fileVersion.setFileId(fileId);
        fileVersion.setVersion(file.getVersion() - 1);
        fileVersion.setDownload_url(oldFileUrl);
        fileVersion.setSize(size);
        fileVersionService.save(fileVersion);

        // 返回当前版本信息
        BeanUtils.copyProperties(file,fileInfo);

        Map<String,Object> map = new HashMap<>();
        map.put("file",fileInfo);
        return map;
    }

    public Map<String,Object> fileVersion(int version){
        FileDTO fileInfo = new FileDTO();
        String fileId = Context.getFileId();
        FileVersionEntity fileVersion =
                fileVersionService.getRepository().findByFileIdAndVersion(fileId,version);
        if (fileVersion != null){
            BeanUtils.copyProperties(fileVersion,fileInfo);
            fileInfo.setId(fileVersion.getFileId());
        }
        Map<String,Object> map = new HashMap<>();
        map.put("file",fileInfo);
        return map;
    }

    public List<FileListDTO> getFileList(){
        return this.getRepository().findAllFile();
    }

    public Page<FileListDTO> getFileListByPage(com.web.wps.base.Page page){
        PageRequest pages = new PageRequest(page.getPage()-1,page.getSize());
        return this.getRepository().getAllFileByPage(pages);
    }

    public int delFile(String id){
        FileEntity file = this.findOne(id);
        if (file != null){
            if ("Y".equalsIgnoreCase(file.getCanDelete())){
                // del
                this.getRepository().delFile(id);
                return 1;
            }else {
                return 0;
            }
        }else {
            return -1;
        }
    }

    public void uploadFile(MultipartFile file){
        String uploadUserId = "3";
        OSSDTO ossdto = ossUtil.uploadMultipartFile2OSS(file);
        // 上传成功后，处理数据库记录值
        Date date = new Date();
        long dataTime = date.getTime();
        // 保存文件
        FileEntity f = new FileEntity(ossdto.getFileName(),1,((int) ossdto.getFileSize()),
                uploadUserId,uploadUserId,dataTime,dataTime,ossdto.getFileUrl());
        this.save(f);

        // 处理权限
        userAclService.saveUserFileAcl(uploadUserId,f.getId());

        // 处理水印
        watermarkService.saveWatermark(f.getId());
    }

    public String createTemplateFile(String template){
        boolean typeTrue = FileUtil.checkCode(template);
        if (typeTrue){
            return wpsUtil.getTemplateWpsUrl(template,"3");
        }
        return "";
    }

}
