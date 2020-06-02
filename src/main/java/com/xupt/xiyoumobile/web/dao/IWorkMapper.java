package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.WorkReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:16
 */
@Mapper
public interface IWorkMapper {

    int insertOpeningReport(WorkReport openingReport);

    WorkReport findReportByUserAccountAndType(@Param("userAccount") String userAccount, @Param("type") Integer type);

    int modifyReportSelective(WorkReport workReport);

    int checkValid(@Param("userAccount") String userAccount, @Param("id") Integer id);

    int deleteWorkReportByUserAccountAndId(@Param("userAccount") String userAccount, @Param("id") Integer workReportId);

    WorkReport findReportByReportId(Integer workReportId);
}
