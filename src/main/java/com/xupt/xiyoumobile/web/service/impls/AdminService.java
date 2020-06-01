package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IAdminMapper;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IAdminService;
import com.xupt.xiyoumobile.web.vo.TeamMemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:15
 */
@Slf4j
@Service
public class AdminService implements IAdminService {

    private IAdminMapper adminMapper;

    private IUserMapper userMapper;

    @Autowired
    public AdminService(IAdminMapper adminMapper, IUserMapper userMapper) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<String> arrangeTeamMember(TeamMemberVo teamMemberVo) {

        int arrangeRes = adminMapper.arrangeTeamMember(teamMemberVo);
        if (arrangeRes == 0) {
            log.error("DB error ! arrangeTeamMember failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("安排小组成员成功");
    }

    @Override
    public ApiResponse<String> modifyUserRole(String userAccount, Integer roleId) {
        User user = userMapper.findByUsername(userAccount);
        if (user == null) {
            log.info("This user is not exist! {}", userAccount);
            return ApiResponse.createByErrorMsg("该用户不存在");
        }

        int modifyRes = adminMapper.modifyUserRole(user.getId(), roleId);
        if (modifyRes == 0) {
            log.error("DB error ! modifyUserRole failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("修改用户权限成功");
    }
}
