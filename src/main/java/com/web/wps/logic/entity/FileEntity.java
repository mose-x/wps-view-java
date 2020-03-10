package com.web.wps.logic.entity;

import com.web.wps.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "w_file_t")
@EqualsAndHashCode(callSuper = true)
public class FileEntity extends BaseEntity {

	/*
	id:  "132aa30a87064",                 //文件id,字符串长度小于40
    name : "example.doc",                //文件名
    version: 1,                        //当前版本号，位数小于11
    size: 200,                        //文件大小，单位为kb
    creator: "id0",                        //创建者id，字符串长度小于40
    create_time: 1136185445,            //创建时间，时间戳，单位为秒
    modifier: "id1000",                    //修改者id，字符串长度小于40
    modify_time: 1551409818,            //修改时间，时间戳，单位为秒
    download_url: "http://www.xxx.cn/v1/file?fid=f132aa30a87064",  //文档下载地址
    */

	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(length = 50)
	private String id;
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

	private String deleted;
	private String canDelete;

	public FileEntity(){super();}

	public FileEntity(String name, int version, int size, String creator,
					  String modifier, long create_time, long modify_time, String download_url) {
		this.name = name;
		this.version = version;
		this.size = size;
		this.creator = creator;
		this.modifier = modifier;
		this.create_time = create_time;
		this.modify_time = modify_time;
		this.download_url = download_url;
		this.deleted = "N";
		this.canDelete = "Y";
	}
}
