package com.web.wps.logic.repository;

import com.web.wps.base.BaseRepository;
import com.web.wps.logic.entity.FileVersionEntity;

import java.util.List;

public interface FileVersionRepository extends BaseRepository<FileVersionEntity,Long> {

    List<FileVersionEntity> findByFileIdOrderByVersionDesc(String fileId);

    FileVersionEntity findByFileIdAndVersion(String fileId,int version);

}
