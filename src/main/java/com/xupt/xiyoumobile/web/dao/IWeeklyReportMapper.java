package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.WeeklyReport;
import com.xupt.xiyoumobile.web.entity.WeeklyReportComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-14 23:43
 */
@Mapper
public interface IWeeklyReportMapper {

    int insertWeeklyReport(WeeklyReport weeklyReport);

    List<WeeklyReport> getAllWeeklyReportByUserAccount(String userAccount);

    WeeklyReport findWeeklyReportById(Integer weeklyReportId);

    int insertWeeklyReportComment(@Param("weeklyReportId") Integer weeklyReportId,
                                  @Param("userAccount") String userAccount,
                                  @Param("teacherName") String teacherName,
                                  @Param("comment") String comment);

    List<WeeklyReportComment> findWeeklyReportCommentById(Integer weeklyReportId);

    List<WeeklyReportComment> getCommentsByWeeklyReportId(Integer weeklyReportId);

    List<WeeklyReport> getTeamWeeklyReport(Integer teamId);
}
