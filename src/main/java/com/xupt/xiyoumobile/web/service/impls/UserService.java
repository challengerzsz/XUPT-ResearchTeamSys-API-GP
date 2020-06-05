package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import com.xupt.xiyoumobile.web.vo.SimpleUserInfoVo;
import com.xupt.xiyoumobile.web.vo.UserRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    public ApiResponse<String> register(User user, Integer role) {

        // passwordEncoder 加密
        user.setUserPassword(passwordEncoder.encode(user.getUserAccount()));

        int mod = userMapper.register(user);

        if (mod == 0) {
            log.error("userMapper insert user failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "注册失败");
        }

        // 因为没有用mybatis的插入回写主键 这里需要查一下userId
        Long userId = userMapper.findByUsername(user.getUserAccount()).getId();

        // 写用户权限表
        int insertUserRoleRes = userMapper.insertUserRole(userId, role);
        if (insertUserRoleRes == 0) {
            log.error("DB Error! insertUserRole failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("注册成功");
    }

    @Override
    public ApiResponse<String> modifyInfo(User user) {

        int updateRes = userMapper.updateUserBySelective(user);
        if (updateRes == 0) {
            log.error("userMapper modify user failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "更新用户信息失败");
        }
        return ApiResponse.createBySuccessMsg("更新用户信息成功");
    }

    @Override
    public ApiResponse<User> getUserInfoByUserAccount(String userAccount) {

        User user = userMapper.findByUsername(userAccount);
        if (user == null) {
            return ApiResponse.createByErrorMsg("无该用户账号信息");
        }

        return ApiResponse.createBySuccess("查询成功", user);
    }

    @Override
    public ApiResponse<String> modifyBanUserStatus(String userAccount, Integer banStatus) {

        int banRes = userMapper.banUserByUserAccount(userAccount, banStatus);
        if (banRes == 0) {
            log.error("userMapper insert user failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "修改用户禁用状态失败");
        }

        return ApiResponse.createBySuccessMsg("修改用户禁用状态成功");
    }

    @Override
    public ApiResponse<List<User>> getAllUsersByRoleId(Integer roleId) {

        List<User> allUsers = userMapper.getAllUsersByRoleId(roleId);

        return ApiResponse.createBySuccess("查询成功", allUsers);
    }

    @Override
    public ApiResponse<List<UserRoleVo>> getAllUserRole() {

        List<UserRoleVo> userRoleVoList = userMapper.getAllUserRole();

        return ApiResponse.createBySuccess("查询成功", userRoleVoList);
    }

    @Override
    public ApiResponse<String> resetPassword(String userAccount, String oldPwd, String newPwd) {

        User user = userMapper.findByUsername(userAccount);
        if (user == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.USER_NOTFOUND.getCode(), "用户不存在");
        }

        if (passwordEncoder.encode(oldPwd).equals(user.getUserPassword())) {
            user.setUserPassword(passwordEncoder.encode(newPwd));
            int update = userMapper.updateUserBySelective(user);
            if (update == 0) {
                log.error("db error! update user info failed!");
                return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
            }
        }

        return ApiResponse.createBySuccessMsg("重置密码成功");
    }

    @Override
    public ApiResponse<SimpleUserInfoVo> getUserSimpleInfo(Integer type, String userName) {

        SimpleUserInfoVo simpleUserInfoVo = userMapper.getUserSimpleInfo(type, userName);
        if (simpleUserInfoVo == null) {
            return ApiResponse.createByErrorMsg("不存在该类型用户!");
        }

        return ApiResponse.createBySuccess("查询成功", simpleUserInfoVo);
    }


}
