package com.web.wps.logic.repository;

import com.web.wps.base.BaseRepository;
import com.web.wps.logic.dto.FileListDTO;
import com.web.wps.logic.entity.FileEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends BaseRepository<FileEntity,String> {

    FileEntity findByName(String name);

    // download_url 不显示到前台
    @Query(value = "select new com.web.wps.logic.dto.FileListDTO(a.id,a.name,a.version,a.size,b.name,c.name," +
            "a.create_time,a.modify_time,'_',a.modifier) " +
            "from FileEntity a,UserEntity b,UserEntity c " +
            "where a.creator = b.id and a.modifier = c.id")
    List<FileListDTO> findAllFile();

}
