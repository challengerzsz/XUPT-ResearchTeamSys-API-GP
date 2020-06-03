package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.security.util.FileUploadUtil;
import com.xupt.xiyoumobile.web.dao.IAchievementMapper;
import com.xupt.xiyoumobile.web.entity.Competition;
import com.xupt.xiyoumobile.web.entity.Patent;
import com.xupt.xiyoumobile.web.service.IAchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:53
 */
@Slf4j
@Service
public class AchievementService implements IAchievementService {

    @Value("${upload.patent}")
    private String PATENT_UPLOAD_PATH;

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

    @Override
    public ApiResponse<String> uploadPatent(Patent patent) {

        int uploadPatentRes = achievementMapper.insertPatent(patent);
        if (uploadPatentRes == 0) {
            log.error("DB Error! insertPatent failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传专利信息成功");
    }

    @Override
    public ApiResponse<String> deletePatent(Integer patentId) {

        Patent patent = achievementMapper.findPatentById(patentId);
        if (patent == null) {
            return ApiResponse.createByErrorMsg("该专利信息不存在，删除专利失败");
        }

        if (!FileUploadUtil.deleteFile(patent.getFilePath())) {
            return ApiResponse.createByErrorMsg("该专利附件不存在或删除失败!");
        }

        int deletePatentRes = achievementMapper.deletePatent(patentId);
        if (deletePatentRes == 0) {
            log.error("DB Error! deletePatent failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("删除专利信息成功");
    }

    @Override
    public ApiResponse<String> uploadPatentFile(Integer patentId, MultipartFile multipartFile) {

        Patent patent = achievementMapper.findPatentById(patentId);
        if (patent == null) {
            return ApiResponse.createByErrorMsg("该专利信息不存在，上传专利附件失败");
        }

        String destPath = FileUploadUtil.uploadFile(multipartFile, PATENT_UPLOAD_PATH);
        if (destPath == null) {
            return ApiResponse.createByErrorMsg("上传附件失败!");
        }

        patent.setFilePath(destPath);

        ApiResponse<String> modifyRes = commonModifyPatent(patent);
        if (!modifyRes.isSuccess()) {
            return modifyRes;
        }

        return ApiResponse.createBySuccessMsg("上传专利附件成功");
    }

    @Override
    public ApiResponse<List<Patent>> getAllPatent() {

        List<Patent> patents = achievementMapper.getAllPatent();
        if (CollectionUtils.isEmpty(patents)) {
            return ApiResponse.createByErrorMsg("还未上传任何专利信息!");
        }

        return ApiResponse.createBySuccess("查询成功", patents);
    }

    @Override
    public ApiResponse<String> modifyPatent(Patent patent) {
        return commonModifyPatent(patent);
    }

    private ApiResponse<String> commonModifyPatent(Patent patent) {
        int modifyPatentRes = achievementMapper.modifyPatent(patent);
        if (modifyPatentRes == 0) {
            log.error("DB Error! modifyPatent failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("更新专利信息成功");
    }
}
