package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IAchievementMapper;
import com.xupt.xiyoumobile.web.entity.Competition;
import com.xupt.xiyoumobile.web.service.IAchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:53
 */
@Slf4j
@Service
public class AchievementService implements IAchievementService {

    private IAchievementMapper achievementMapper;

    @Autowired
    public AchievementService(IAchievementMapper achievementMapper) {
        this.achievementMapper = achievementMapper;
    }

    @Override
    public ApiResponse<String> uploadCompetition(Competition competition) {
        int insertCompetitionRes = achievementMapper.insertCompetition(competition);
        if (insertCompetitionRes == 0) {
            log.error("DB Error! insertCompetition failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传竞赛信息成功");
    }

    @Override
    public ApiResponse<List<Competition>> getAllCompetition() {

        List<Competition> competitions = achievementMapper.getAllCompetition();
        if (CollectionUtils.isEmpty(competitions)) {
            return ApiResponse.createByErrorMsg("目前还未存在有上传的竞赛信息");
        }

        return ApiResponse.createBySuccess("查询成功", competitions);
    }

    @Override
    public ApiResponse<String> deleteCompetition(Integer competitionId) {

        Competition competition = achievementMapper.findCompetitionById(competitionId);
        if (competition == null) {
            return ApiResponse.createByErrorMsg("该竞赛信息不存在，删除失败");
        }

        int deleteCompetitionRes = achievementMapper.deleteCompetitionById(competitionId);
        if (deleteCompetitionRes == 0) {
            log.error("DB Error! deleteCompetitionById failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("删除该竞赛信息成功");
    }

    @Override
    public ApiResponse<String> modifyCompetition(Competition competition) {

        int modifyCompetitionRes = achievementMapper.modifyCompetition(competition);
        if (modifyCompetitionRes == 0) {
            log.error("DB Error! modifyCompetition failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("更新竞赛信息成功");
    }
}
