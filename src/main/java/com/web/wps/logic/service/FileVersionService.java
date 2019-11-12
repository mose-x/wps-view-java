package com.web.wps.logic.service;

import com.web.wps.base.BaseRepository;
import com.web.wps.base.BaseService;
import com.web.wps.logic.entity.FileVersionEntity;
import com.web.wps.logic.repository.FileVersionRepository;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class FileVersionService extends BaseService<FileVersionEntity,Long> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = FileVersionRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public FileVersionRepository getRepository(){
        return (FileVersionRepository) this.baseRepository;
    }

}
