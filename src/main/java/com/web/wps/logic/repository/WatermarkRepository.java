package com.web.wps.logic.repository;

import com.web.wps.base.BaseRepository;
import com.web.wps.logic.entity.WatermarkEntity;

public interface WatermarkRepository extends BaseRepository<WatermarkEntity,Long> {

    WatermarkEntity findFirstByFileId(String fileId);

}
