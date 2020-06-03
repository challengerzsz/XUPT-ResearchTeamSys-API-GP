package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Competition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:55
 */
@Mapper
public interface IAchievementMapper {

    int insertCompetition(Competition competition);

    List<Competition> getAllCompetition();

    Competition findCompetitionById(Integer competitionId);

    int deleteCompetitionById(Integer competitionId);

    int modifyCompetition(Competition competition);
}
