package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:45
 */
@Mapper
public interface IUserMapper {

    User getUserAccountPassword(String username);
}
