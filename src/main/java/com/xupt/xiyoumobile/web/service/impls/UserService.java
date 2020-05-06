package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 16:41
 */
@Service
public class UserService implements IUserService {

    private IUserMapper userMapper;

    @Autowired
    public UserService(IUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User selectUserByUserAccount(String username) {

        return userMapper.findByUsername(username);
    }

    @Override
    public List<Role> selectRoleByUserId(Long id) {
        return userMapper.selectRoleByUserId(id);
    }
}
