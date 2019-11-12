package com.web.wps.logic.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.web.wps.base.BaseRepository;
import com.web.wps.base.BaseService;
import com.web.wps.logic.entity.UserEntity;
import com.web.wps.logic.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<UserEntity,String> {

    @Override
    @SuppressWarnings("unchecked")
    @Resource(type = UserRepository.class)
    protected void setDao(BaseRepository baseRepository) {
        this.baseRepository = baseRepository;
    }

    public UserRepository getRepository(){
        return (UserRepository) this.baseRepository;
    }

    public Map<String,Object> userInfo(JSONObject reqObj){
        List<String> ids = null;

        if(reqObj != null) {
            if(reqObj.containsKey("ids")) {
                ids = JSONArray.parseArray(reqObj.getString("ids"), String.class);
            }
        }

        Map<String,Object> map = new HashMap<>();
        List<UserEntity> users = new ArrayList<>();
        if(ids != null && !ids.isEmpty()) {
            UserEntity user;
            for (String id : ids) {
                user = this.findOne(id);
                if (user != null){
                    users.add(user);
                }
            }
        }
        map.put("users", users);

        return map;
    }

}
