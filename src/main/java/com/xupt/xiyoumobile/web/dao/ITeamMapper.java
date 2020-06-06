package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Team;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    List<Team> getAllTeam();

    List<Team> getTeamInfoByTeacherAccount(Integer teacherAccount);

    int addTeamStudentCount(@Param("teamId") Integer teamId, @Param("size") int size);
}
