package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.security.util.FileUploadUtil;
import com.xupt.xiyoumobile.web.dao.IAchievementMapper;
import com.xupt.xiyoumobile.web.entity.Competition;
import com.xupt.xiyoumobile.web.entity.Patent;
import com.xupt.xiyoumobile.web.entity.SoftWareCopyright;
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

    @Value("${upload.softWareCopyright.document}")
    private String SOFTWARE_COPYRIGHT_DOCUMENT_PATH;

    @Value("${upload.softWareCopyright.project}")
    private String SOFTWARE_COPYRIGHT_PROJECT_PATH;

    @Value("${upload.softWareCopyright.certificate}")
    private String SOFTWARE_COPYRIGHT_CERTIFICATE_PATH;

    private static final int DOCUMENT_FILE = 0;

    private static final int PROJECT_FILE = 1;

    private static final int CERTIFICATE_FILE = 2;


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
    public ApiResponse<Integer> uploadPatent(Patent patent) {

        int uploadPatentRes = achievementMapper.insertPatent(patent);
        if (uploadPatentRes == 0) {
            log.error("DB Error! insertPatent failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccess("上传专利信息成功", patent.getId());
    }

    @Override
    public ApiResponse<String> deletePatent(Integer patentId) {

        Patent patent = achievementMapper.findPatentById(patentId);
        if (patent == null) {
            return ApiResponse.createByErrorMsg("该专利信息不存在，删除专利失败");
        }

//        if (!FileUploadUtil.deleteFile(patent.getFilePath())) {
//            return ApiResponse.createByErrorMsg("该专利附件不存在或删除失败!");
//        }

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

        if (commonModifyPatent(patent)) {
            log.error("DB Error! modifyPatent failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
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
        if (commonModifyPatent(patent)) {
            log.error("DB Error! modifyPatent failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("修改专利信息成功");
    }

    @Override
    public ApiResponse<Integer> uploadSoftWareCopyright(SoftWareCopyright softWareCopyright) {

        int uploadSoftWareCopyrightRes = achievementMapper.insertSoftWareCopyright(softWareCopyright);
        if (uploadSoftWareCopyrightRes == 0) {
            log.error("DB Error! insertSoftWareCopyright failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccess("上传软件著作权信息成功", softWareCopyright.getId());
    }

    @Override
    public ApiResponse<String> uploadSoftWareCopyrightFile(Integer softWareCopyrightId, MultipartFile file,
                                                           Integer type) {

        SoftWareCopyright softWareCopyright = achievementMapper.findSoftWareCopyrightById(softWareCopyrightId);
        if (softWareCopyright == null) {
            return ApiResponse.createByErrorMsg("软件著作权信息不存在，上传附件失败!");
        }
        String destPath;
        switch (type) {
            case DOCUMENT_FILE:
                destPath = FileUploadUtil.uploadFile(file, SOFTWARE_COPYRIGHT_DOCUMENT_PATH);
                if (destPath != null) {
                    softWareCopyright.setDocumentPath(destPath);
                }
                break;
            case PROJECT_FILE:
                destPath = FileUploadUtil.uploadFile(file, SOFTWARE_COPYRIGHT_PROJECT_PATH);
                if (destPath != null) {
                    softWareCopyright.setProjectPath(destPath);
                }
                break;
            case CERTIFICATE_FILE:
                destPath = FileUploadUtil.uploadFile(file, SOFTWARE_COPYRIGHT_CERTIFICATE_PATH);
                if (destPath != null) {
                    softWareCopyright.setCertificatePath(destPath);
                }
                break;
        }

        if (commonModifySoftWareCopyright(softWareCopyright)) {
            log.error("DB Error! modifySoftwareCopyright failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传软件著作权附件成功");
    }

    @Override
    public ApiResponse<String> modifySoftWareCopyright(SoftWareCopyright softWareCopyright) {
        if (commonModifySoftWareCopyright(softWareCopyright)) {
            log.error("DB Error! modifySoftwareCopyright failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("更新专利信息成功");
    }

    @Override
    public ApiResponse<List<SoftWareCopyright>> getAllSoftWareCopyright() {

        List<SoftWareCopyright> softWareCopyrights = achievementMapper.getAllSoftWareCopyright();
        if (CollectionUtils.isEmpty(softWareCopyrights)) {
            return ApiResponse.createByErrorMsg("未上传任何软件著作权信息!");
        }

        return ApiResponse.createBySuccess("查询成功", softWareCopyrights);
    }

    @Override
    public ApiResponse<String> deleteSoftWareCopyright(Integer scId) {

        int deleteSCRes = achievementMapper.deleteSoftWareCopyright(scId);
        if (deleteSCRes == 0) {
            log.error("DB Error! deleteSoftWareCopyright failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("删除软著信息成功");
    }

    private Boolean commonModifySoftWareCopyright(SoftWareCopyright softWareCopyright) {
        int modifySoftwareCopyrightRes = achievementMapper.modifySoftwareCopyright(softWareCopyright);
        return modifySoftwareCopyrightRes == 0;
    }

    private Boolean commonModifyPatent(Patent patent) {
        int modifyPatentRes = achievementMapper.modifyPatent(patent);
        return modifyPatentRes == 0;
    }
}
