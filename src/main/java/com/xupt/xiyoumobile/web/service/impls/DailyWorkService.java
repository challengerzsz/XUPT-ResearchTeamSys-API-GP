package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IDailyWorkMapper;
import com.xupt.xiyoumobile.web.entity.ClaimExpense;
import com.xupt.xiyoumobile.web.entity.Project;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IDailyWorkService;
import com.xupt.xiyoumobile.web.service.IUserService;
import com.xupt.xiyoumobile.web.vo.AdminClaimExpenseStatisticsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 19:10
 */
@Slf4j
@Service
public class DailyWorkService implements IDailyWorkService {

    private IUserService userService;

    private IDailyWorkMapper dailyWorkMapper;

    @Autowired
    public DailyWorkService(IUserService userService, IDailyWorkMapper dailyWorkMapper) {
        this.userService = userService;
        this.dailyWorkMapper = dailyWorkMapper;
    }

    @Override
    public ApiResponse<String> uploadClaimExpense(String userAccount, ClaimExpense claimExpense) {

        User user = userService.selectUserByUserAccount(userAccount);
        claimExpense.setUserAccount(userAccount);
        claimExpense.setUserName(user.getUserName());

        int insertClaimExpenseRes = dailyWorkMapper.insertClaimExpense(claimExpense);
        if (insertClaimExpenseRes == 0) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传报销记录成功");
    }

    @Override
    public ApiResponse<List<ClaimExpense>> getAllMyClaimExpense(String userAccount) {

        List<ClaimExpense> claimExpenses = dailyWorkMapper.selectClaimExpenseByUserAccount(userAccount);
        if (CollectionUtils.isEmpty(claimExpenses)) {
            return ApiResponse.createByErrorMsg("未上传报销记录!");
        }

        return ApiResponse.createBySuccess("查询成功", claimExpenses);
    }

    @Override
    public ApiResponse<String> deleteClaimExpense(String userAccount, Integer claimExpenseId) {

        ClaimExpense claimExpense = dailyWorkMapper.findClaimExpenseById(claimExpenseId);
        if (claimExpense == null) {
            return ApiResponse.createByErrorMsg("该报销记录不存在，删除失败!");
        }

        if (!claimExpense.getUserAccount().equals(userAccount)) {
            log.warn("Someone is trying to delete other's claimExpense! {}", userAccount);
            // TODO: 2020-06-03 IMPUSH => ADMIN 感知越权操作
            return ApiResponse.createByErrorMsg("该报销记录非本人上传，删除失败，并已通知管理员");
        }

        int deleteClaimExpenseRes = dailyWorkMapper.deleteClaimExpenseById(claimExpenseId);
        if (deleteClaimExpenseRes == 0) {
            log.error("DB error ! deleteClaimExpenseById failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("删除报销记录成功");
    }

    @Override
    public ApiResponse<String> modifyClaimExpense(String userAccount, ClaimExpense claimExpense) {

        ClaimExpense dbClaimExpense = dailyWorkMapper.findClaimExpenseById(claimExpense.getId());
        if (dbClaimExpense == null) {
            return ApiResponse.createByErrorMsg("该报销记录不存在，无法修改!");
        }

        if (!dbClaimExpense.getUserAccount().equals(userAccount)) {
            log.warn("Someone is trying to modify other's claimExpense! {}", userAccount);
            return ApiResponse.createByErrorMsg("该报销记录非本人上传，修改失败，并已通知管理员");
        }

        int modifyClaimExpenseRes = dailyWorkMapper.modifyClaimExpense(claimExpense);
        if (modifyClaimExpenseRes == 0) {
            log.error("DB error ! modifyClaimExpense failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("报销记录修改成功");
    }

    @Override
    public ApiResponse<String> uploadProject(Project project) {

        int insertProjectRes = dailyWorkMapper.insertProject(project);
        if (insertProjectRes == 0) {
            log.error("DB error ! insertProject failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("上传项目信息成功");
    }

    @Override
    public ApiResponse<List<Project>> getAllProject() {

        List<Project> projects = dailyWorkMapper.getAllProject();
        if (CollectionUtils.isEmpty(projects)) {
            return ApiResponse.createByErrorMsg("未上传任何项目信息!");
        }
        return ApiResponse.createBySuccess("查询成功", projects);
    }

    @Override
    public ApiResponse<String> deleteProject(Integer projectId) {

        int deleteProjectRes = dailyWorkMapper.deleteProjectById(projectId);
        if (deleteProjectRes == 0) {
            log.error("DB error ! deleteProjectById failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("删除项目信息成功");
    }

    @Override
    public ApiResponse<String> modifyProject(Project project) {

        int modifyProjectRes = dailyWorkMapper.modifyProject(project);
        if (modifyProjectRes == 0) {
            log.error("DB error ! modifyProject failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("修改项目信息成功");
    }

    /**
     * 查看个人报销统计
     * @param type 0:总览 1:类型
     * @param typeName
     * @param principal
     * @return
     */
    @Override
    public ApiResponse<AdminClaimExpenseStatisticsVo> getClaimExpenseStatistics(Integer type, String typeName,
                                                                                      Principal principal) {

        String userAccount = principal.getName();
        AdminClaimExpenseStatisticsVo result = null;

        switch (type) {
            case 0:
                result = dailyWorkMapper.getClaimExpenseStatisticsByUserAccount(userAccount);
                break;
            case 1:
                if (typeName == null) {
                    return ApiResponse.createByErrorMsg("查看个人报销统计参数出错!");
                }
                result = dailyWorkMapper.getClaimExpenseStatisticsByTypeName(userAccount, typeName);
                break;
        }

        if (result == null) {
            return ApiResponse.createByErrorMsg("未查询到个人报销数据!");
        }

        return ApiResponse.createBySuccess("查询成功", result);
    }
}
