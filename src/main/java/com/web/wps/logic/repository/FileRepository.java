package com.web.wps.logic.repository;

import com.web.wps.base.BaseRepository;
import com.web.wps.logic.dto.FileListDTO;
import com.web.wps.logic.entity.FileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FileRepository extends BaseRepository<FileEntity,String> {

    FileEntity findByName(String name);

    // download_url 不显示到前台
    @Query(value = "select new com.web.wps.logic.dto.FileListDTO(a.id,a.name,a.version,a.size,b.name,c.name," +
            "a.create_time,a.modify_time,'_',a.modifier) " +
            "from FileEntity a,UserEntity b,UserEntity c " +
            "where a.creator = b.id and a.modifier = c.id and a.deleted = 'N' order by a.create_time desc ")
    List<FileListDTO> findAllFile();


    @Query(value = "select new com.web.wps.logic.dto.FileListDTO(a.id,a.name,a.version,a.size,b.name,c.name," +
            "a.create_time,a.modify_time,'_',a.modifier) " +
            "from FileEntity a,UserEntity b,UserEntity c " +
            "where a.creator = b.id and a.modifier = c.id and a.deleted = 'N' order by a.create_time desc ")
    Page<FileListDTO> getAllFileByPage(Pageable page);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "update w_file_t set deleted = 'Y' where id = ?1",nativeQuery = true)
    void delFile(String id);

}
