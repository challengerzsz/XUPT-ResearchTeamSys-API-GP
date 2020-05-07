package com.xupt.xiyoumobile.web.dao;


import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:45
 */
@Mapper
public interface IUserMapper {

    User findByUsername(String userAccount);

    List<Role> selectRoleByUserId(Long id);

    int register(User user);
}
