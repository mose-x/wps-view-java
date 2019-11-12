package com.web.wps.logic.entity;

import com.web.wps.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity
@Table(name = "w_user_acl_t")
@EqualsAndHashCode(callSuper = true)
public class UserAclEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(length = 40)
	private Long id;
	private String userId;
	private String fileId;

	private String permission = "read";

	// 用户权限
	private int rename = 0; //重命名权限，1为打开该权限，0为关闭该权限，默认为0
	private int history = 0; //历史版本权限，1为打开该权限，0为关闭该权限,默认为1

	public UserAclEntity(){super();}

	public UserAclEntity(String userId, String fileId) {
		this.userId = userId;
		this.fileId = fileId;
		this.permission = "write";
		this.rename = 1;
		this.history = 1;
	}
}
