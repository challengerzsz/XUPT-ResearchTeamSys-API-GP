package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IAdminMapper;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IAdminService;
import com.xupt.xiyoumobile.web.vo.AdminClaimExpenseStatisticsVo;
import com.xupt.xiyoumobile.web.vo.CountVo;
import com.xupt.xiyoumobile.web.vo.TeamMemberVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:15
 */
@Slf4j
@Service
public class AdminService implements IAdminService {

    private IAdminMapper adminMapper;

    private IUserMapper userMapper;

    @Autowired
    public AdminService(IAdminMapper adminMapper, IUserMapper userMapper) {
        this.adminMapper = adminMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<String> arrangeTeamMember(TeamMemberVo teamMemberVo) {

        int arrangeRes = adminMapper.arrangeTeamMember(teamMemberVo);
        if (arrangeRes == 0) {
            log.error("DB error ! arrangeTeamMember failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("安排小组成员成功");
    }

    @Override
    public ApiResponse<String> modifyUserRole(String userAccount, Integer roleId) {
        User user = userMapper.findByUsername(userAccount);
        if (user == null) {
            log.info("This user is not exist! {}", userAccount);
            return ApiResponse.createByErrorMsg("该用户不存在");
        }

        int modifyRes = adminMapper.modifyUserRole(user.getId(), roleId);
        if (modifyRes == 0) {
            log.error("DB error ! modifyUserRole failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB error");
        }

        return ApiResponse.createBySuccessMsg("修改用户权限成功");
    }

    /**
     * 管理员查看报销统计
     * @param type 0:按人 1:按类型
     * @return
     */
    @Override
    public ApiResponse<List<AdminClaimExpenseStatisticsVo>> getClaimExpenseStatistics(
            Integer type, String typeName, String beginDate, String endDate) {

        List<AdminClaimExpenseStatisticsVo> result = null;
        // 这switch case 有魔数了 如果后来有人维护这个API 把这改一下吧..
        switch (type) {
            case 0:
                result = adminMapper.getClaimExpenseStatisticsByUserAccount(beginDate, endDate);
                break;
            case 1:
                if (typeName == null) {
                    return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                            "按类型查询报销记录参数错误");
                }
                result = adminMapper.getClaimExpenseStatisticsByType(typeName, beginDate, endDate);
                break;
        }

        if (CollectionUtils.isEmpty(result)) {
            ApiResponse.createByErrorMsg("未查询到记录!");
        }

        return ApiResponse.createBySuccess("查询成功", result);
    }

    /**
     *
     * @param type 0:论文 1:竞赛 2:专利 3:软件著作权
     * @return
     */
    @Override
    public ApiResponse<Integer> getAchievementStatistics(Integer type) {

        Integer result = null;
        switch (type) {
            case 0:
                result = adminMapper.getPaperStatistics();
                break;
            case 1:
                result = adminMapper.getCompetitionStatistics();
                break;
            case 2:
                result = adminMapper.getPatentStatistics();
                break;
            case 3:
                result = adminMapper.getSoftWareCopyrightStatistics();
                break;
        }

        if (result == null) {
            return ApiResponse.createByErrorMsg("未查询到相关统计记录!");
        }

        return ApiResponse.createBySuccess("查询成功", result);
    }

    @Override
    public ApiResponse<CountVo> countAll() {
        CountVo countVo = new CountVo();
        countVo.setTeamCount(adminMapper.countTeam());
        countVo.setMemberCount(adminMapper.countMember());
        countVo.setAchievementCount(adminMapper.countAchievement());
        countVo.setDocumentCount(adminMapper.countDocument());
        countVo.setPaperCount(adminMapper.countPaper());

        return ApiResponse.createBySuccess("查询成功", countVo);
    }
}
