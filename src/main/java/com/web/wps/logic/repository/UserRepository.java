package com.web.wps.logic.repository;

import com.web.wps.base.BaseRepository;
import com.web.wps.logic.entity.UserEntity;

import java.util.List;

public interface UserRepository extends BaseRepository<UserEntity,String> {

    List<UserEntity> findByIdIn(List<String> id);

}
