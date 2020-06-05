package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Team;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-05 16:44
 */
@Mapper
public interface ITeamMapper {

    int insertTeam(Team team);

    Team findTeamByTeacherName(String teacherName);

    int deleteTeamById(Integer teamId);

    int modifyTeamInfo(Team team);
}
