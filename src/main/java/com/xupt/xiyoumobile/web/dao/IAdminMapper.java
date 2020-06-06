package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.vo.AdminClaimExpenseStatisticsVo;
import com.xupt.xiyoumobile.web.vo.ArrangeTeamVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:21
 */
@Mapper
public interface IAdminMapper {

    int arrangeTeamMember(ArrangeTeamVo arrangeTeamVo);

    int modifyUserRole(@Param("userId") Long userId, @Param("roleId") Integer roleId);

    List<AdminClaimExpenseStatisticsVo> getClaimExpenseStatisticsByUserAccount(@Param("beginDate") String beginDate,
                                                                               @Param("endDate") String endDate);

    List<AdminClaimExpenseStatisticsVo> getClaimExpenseStatisticsByType(@Param("typeName") String typeName,
                                                                        @Param("beginDate") String beginDate,
                                                                        @Param("endDate") String endDate);

    Integer getPaperStatistics();

    Integer getCompetitionStatistics();

    Integer getPatentStatistics();

    Integer getSoftWareCopyrightStatistics();

    Integer countTeam();

    Integer countMember();

    Integer countAchievement();

    Integer countPaper();

    Integer countDocument();
}
