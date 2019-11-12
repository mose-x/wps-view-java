package com.web.wps.logic.repository;

import com.web.wps.base.BaseRepository;
import com.web.wps.logic.entity.UserAclEntity;

public interface UserAclRepository extends BaseRepository<UserAclEntity,Long> {

    UserAclEntity findFirstByFileIdAndUserId(String fileId,String userId);

}
