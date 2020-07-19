package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.ITeamMapper;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.Team;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.ITeamService;
import com.xupt.xiyoumobile.web.vo.SimpleUserInfoVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-05 16:39
 */
@Slf4j
@Service
public class TeamService implements ITeamService {

    private ITeamMapper teamMapper;

    private IUserMapper userMapper;

    @Autowired
    public TeamService(ITeamMapper teamMapper, IUserMapper userMapper) {
        this.teamMapper = teamMapper;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ApiResponse<String> createTeam(Team team) {

        // 这里修改过逻辑 之前teacher <=> team 是 1:1
        // 现在修改为 m:n
        // 借助teacher_team_table保存m:n关系

        int insertTeamRes = teamMapper.insertTeam(team);
        if (insertTeamRes == 0) {
            log.error("DB Error! insertTeam failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error");
        }

        int insertTeacherTeamRelationRes = teamMapper.insertTeacherTeamRelation(team.getGuideTeachers(), team.getId());
        if (insertTeacherTeamRelationRes == 0) {
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
    @Transactional
    public ApiResponse<String> deleteTeam(Integer teamId) {

        int deleteTeamIdRes = teamMapper.deleteTeamById(teamId);
        if (deleteTeamIdRes == 0) {
            log.error("DB Error! deleteTeamById failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error");
        }

        int deleteTeacherTeamRelationRes = teamMapper.deleteTeacherTeamRelation(teamId);
        if (deleteTeacherTeamRelationRes == 0) {
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

    @Override
    @Transactional
    public ApiResponse<List<Team>> getAllTeam() {

        List<Team> teams = teamMapper.getAllTeam();
        if (CollectionUtils.isEmpty(teams)) {
            return ApiResponse.createByErrorMsg("该系统中不存在小组信息，请创建小组!");
        }

        for (Team team : teams) {
            List<SimpleUserInfoVo> guideTeachers = teamMapper.getTeamGuideTeachers(team.getId());
            if (CollectionUtils.isEmpty(guideTeachers)) {
                log.error("This Team has no guideTeacher occurred an error! teamId : {}", team.getId());
                return ApiResponse.createByErrorMsg("查询所有小组信息失败!");
            }
            team.setGuideTeachers(guideTeachers);
        }

        return ApiResponse.createBySuccess("查询成功", teams);
    }

    @Override
    public ApiResponse<List<Team>> getTeamInfoByTeacherAccount(Integer teacherAccount) {

        List<Team> teams = teamMapper.getTeamInfoByTeacherAccount(teacherAccount);
        if (CollectionUtils.isEmpty(teams)) {
            return ApiResponse.createByErrorMsg("该教师尚未分配指导小组,可通过创建小组指定该老师!");
        }

        return ApiResponse.createBySuccess("查询成功", teams);
    }

    @Override
    @Transactional
    public ApiResponse<Team> getMyTeamInfo(String userAccount) {

        User user = userMapper.findByUsername(userAccount);
        if (user == null) {
            return ApiResponse.createByErrorMsg("该用户不存在,查询小组信息失败");
        }
        if (user.getTeam() == null) {
            return ApiResponse.createByErrorMsg("您尚未加入小组，联系管理员加入小组!");
        }
        Team team = teamMapper.findTeamById(user.getTeam());
        if (team == null) {
            return ApiResponse.createByErrorMsg("查询小组信息失败!");
        }

        List<SimpleUserInfoVo> guideTeachers = teamMapper.getTeamGuideTeachers(team.getId());
        if (CollectionUtils.isEmpty(guideTeachers)) {
            log.error("This Team has no guideTeacher occurred an error! teamId : {}", team.getId());
            return ApiResponse.createByErrorMsg("该小组无指导老师!");
        }

        team.setGuideTeachers(guideTeachers);

        return ApiResponse.createBySuccess("查询小组信息成功", team);
    }

    @Override
    public ApiResponse<List<Team>> getMyTeams(String userAccount) {

        List<Team> teams = teamMapper.getMyTeams(userAccount);
        if (CollectionUtils.isEmpty(teams)) {
            return ApiResponse.createByErrorMsg("您目前还未担任指导老师职责,请联系管理员!");
        }

        return ApiResponse.createBySuccess("查询成功", teams);
    }
}
