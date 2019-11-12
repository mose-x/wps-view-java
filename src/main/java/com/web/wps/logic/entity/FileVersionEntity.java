package com.web.wps.logic.entity;

import com.web.wps.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "w_file_version_t")
@EqualsAndHashCode(callSuper = true)
public class FileVersionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 40)
    private Long id;
    private String fileId;
    private String name;
    private int version;
    private int size;
    private String creator;
    private String modifier;
    // JPA无法识别为驼峰，故指定字段
    @Column(name = "create_time")
    private long create_time;
    @Column(name = "modify_time")
    private long modify_time;
    @Column(name = "download_url")
    private String download_url;

}
