package com.web.wps.logic.entity;

import com.web.wps.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Table(name = "w_user_t")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

	/*
            id: "id1000",                //用户id，长度小于40
            name: "wps-1000",            //用户名称
            permission: "write",            //用户操作权限，write：可编辑，read：预览
            avatar_url: "http://xxx.cn/id=1000"    //用户头像地址
            */
	@Id
	@GenericGenerator(name="idGenerator", strategy="uuid")
	@GeneratedValue(generator="idGenerator")
	@Column(length = 50)
	private String id;
	private String name;
	@Column(name = "avatar_url")
	private String avatar_url;

}
