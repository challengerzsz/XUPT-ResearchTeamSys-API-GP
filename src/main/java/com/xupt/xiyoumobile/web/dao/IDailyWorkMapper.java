package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.ClaimExpense;
import com.xupt.xiyoumobile.web.entity.Project;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 19:13
 */
@Mapper
public interface IDailyWorkMapper {

    int insertClaimExpense(ClaimExpense claimExpense);

    List<ClaimExpense> selectClaimExpenseByUserAccount(String userAccount);

    ClaimExpense findClaimExpenseById(Integer claimExpenseId);

    int deleteClaimExpenseById(Integer claimExpenseId);

    int modifyClaimExpense(ClaimExpense claimExpense);

    int insertProject(Project project);

    List<Project> getAllProject();

    int deleteProjectById(Integer projectId);

    int modifyProject(Project project);
}
