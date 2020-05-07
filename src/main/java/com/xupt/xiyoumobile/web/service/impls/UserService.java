package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 16:41
 */
@Slf4j
@Service
public class UserService implements IUserService {

    private IUserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserMapper userMapper, @Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public ApiResponse<String> register(User user) {

        // passwordEncoder 加密
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        int mod = userMapper.register(user);

        if (mod == 0) {
            log.error("userMapper insert user failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "注册失败");
        }

        return ApiResponse.createBySuccessMsg("register user success");
    }


}
