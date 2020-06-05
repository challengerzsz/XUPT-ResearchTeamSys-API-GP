package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.ITeamMapper;
import com.xupt.xiyoumobile.web.entity.Team;
import com.xupt.xiyoumobile.web.service.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-05 16:39
 */
@Slf4j
@Service
public class TeamService implements ITeamService {


    private ITeamMapper teamMapper;

    @Autowired
    public TeamService(ITeamMapper teamMapper) {
        this.teamMapper = teamMapper;
    }

    @Override
    public ApiResponse<String> createTeam(Team team) {

        int insertTeamRes = teamMapper.insertTeam(team);
        if (insertTeamRes == 0) {
            log.error("DB Error! insertTeam failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error");
        }

        return ApiResponse.createBySuccessMsg("新建小组成功");
    }

    @Override
    public ApiResponse<Team> getTeamInfo(String teacherName) {

        Team team = teamMapper.findTeamByTeacherName(teacherName);
        if (team == null) {
            return ApiResponse.createByErrorMsg("未查询到该老师管理的小组");
        }

        return ApiResponse.createBySuccess("查询小组信息成功", team);
    }

    @Override
    public ApiResponse<String> deleteTeam(Integer teamId) {

        int deleteTeamIdRes = teamMapper.deleteTeamById(teamId);
        if (deleteTeamIdRes == 0) {
            log.error("DB Error! deleteTeamById failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error");
        }

        return ApiResponse.createBySuccessMsg("删除小组成功");
    }

    @Override
    public ApiResponse<String> modifyTeamInfo(Team team) {

        int modifyTeamInfoRes = teamMapper.modifyTeamInfo(team);
        if (modifyTeamInfoRes == 0) {
            log.error("DB Error! modifyTeamInfo failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error");
        }

        return ApiResponse.createBySuccessMsg("修改小组信息成功");
    }
}
