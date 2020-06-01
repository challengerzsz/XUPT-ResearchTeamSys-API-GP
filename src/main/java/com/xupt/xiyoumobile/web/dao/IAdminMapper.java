package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.vo.TeamMemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:21
 */
@Mapper
public interface IAdminMapper {

    int arrangeTeamMember(TeamMemberVo teamMemberVo);

    int modifyUserRole(@Param("userId") Long userId, @Param("roleId") Integer roleId);
}
