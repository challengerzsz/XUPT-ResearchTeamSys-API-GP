package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.vo.TeamMemberVo;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:14
 */
public interface IAdminService {

    ApiResponse<String> arrangeTeamMember(TeamMemberVo teamMemberVo);

    ApiResponse<String> modifyUserRole(String userAccount, Integer roleId);
}
