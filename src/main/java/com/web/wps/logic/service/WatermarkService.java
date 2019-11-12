package com.web.wps.logic.service;

import com.web.wps.base.BaseRepository;
import com.web.wps.base.BaseService;
import com.web.wps.logic.entity.WatermarkEntity;
import com.web.wps.logic.repository.WatermarkRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WatermarkService extends BaseService<WatermarkEntity,Long> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = WatermarkRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public WatermarkRepository getRepository(){
        return (WatermarkRepository) this.baseRepository;
    }

    public void saveWatermark(String fileId){
        this.save(new WatermarkEntity(fileId));
    }

}
