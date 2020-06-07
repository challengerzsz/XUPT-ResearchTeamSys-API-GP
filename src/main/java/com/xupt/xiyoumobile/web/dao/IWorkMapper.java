package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.WorkReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:16
 */
@Mapper
public interface IWorkMapper {

    int insertReport(WorkReport report);

    WorkReport findReportByUserAccountAndType(@Param("userAccount") String userAccount, @Param("type") Integer type);

    int modifyReportSelective(WorkReport workReport);

    int checkValid(@Param("userAccount") String userAccount, @Param("id") Integer id);

    int deleteWorkReportByUserAccountAndId(@Param("userAccount") String userAccount, @Param("id") Integer workReportId);

    WorkReport findReportByReportId(Integer workReportId);

    List<WorkReport> getTeamWorkReports(@Param("userAccount") String userAccount, @Param("type") Integer type);
}
