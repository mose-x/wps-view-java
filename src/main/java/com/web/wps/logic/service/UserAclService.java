package com.web.wps.logic.service;

import com.web.wps.base.BaseRepository;
import com.web.wps.base.BaseService;
import com.web.wps.logic.entity.UserAclEntity;
import com.web.wps.logic.repository.UserAclRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAclService extends BaseService<UserAclEntity,Long> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = UserAclRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public UserAclRepository getRepository(){
        return (UserAclRepository) this.baseRepository;
    }

    public void saveUserFileAcl(String userId,String fileId){
        this.save(new UserAclEntity(userId,fileId));
    }

}
