package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.security.util.FileUploadUtil;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.dao.IWorkMapper;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.entity.WorkReport;
import com.xupt.xiyoumobile.web.service.IWorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:15
 */
@Slf4j
@Service
public class WorkService implements IWorkService {

    @Value("${upload.report}")
    private String WORK_REPORT_UPLOAD_PATH;

    private IWorkMapper workMapper;

    private IUserMapper userMapper;

    private FileUploadUtil fileUploadUtil;

    @Autowired
    public WorkService(IWorkMapper workMapper, IUserMapper userMapper, FileUploadUtil fileUploadUtil) {
        this.workMapper = workMapper;
        this.userMapper = userMapper;
        this.fileUploadUtil = fileUploadUtil;
    }


    @Override
    public ApiResponse<String> uploadReport(String userAccount, WorkReport report, Integer type) {

        User user = userMapper.findByUsername(userAccount);

        report.setAuthor(userAccount);
        report.setAuthorName(user.getUserName());
        report.setType(type);

        int insertOpeningReportRes = workMapper.insertReport(report);
        if (insertOpeningReportRes == 0) {
            log.error("DB Error! uploadOpeningReport failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传成功");
    }



    @Override
    public ApiResponse<WorkReport> getReport(String userAccount, Integer type) {

        WorkReport workReport = workMapper.findReportByUserAccountAndType(userAccount, type);
        if (workReport == null) {
            return ApiResponse.createByErrorMsg("您有报告未上传");
        }

        return ApiResponse.createBySuccess("查询成功", workReport);
    }

    @Override
    public ApiResponse<String> uploadReportFile(String userAccount, MultipartFile multipartFile, Integer type) {

        WorkReport workReport = workMapper.findReportByUserAccountAndType(userAccount, type);
        if (workReport == null) {
            return ApiResponse.createByErrorMsg("未查询到该报告基本信息，无法进行附件上传");
        }

        String destFilePath = fileUploadUtil.uploadFile(userAccount, multipartFile, WORK_REPORT_UPLOAD_PATH);
        if (destFilePath == null) {
            return ApiResponse.createByErrorMsg("上传文件失败!");
        }

        workReport.setReportUrl(destFilePath);

        return modifyWorkReport(userAccount, workReport, type);
    }

    @Override
    public ApiResponse<String> modifyWorkReport(String userAccount, WorkReport workReport, Integer type) {

        if (!checkValid(userAccount, workReport.getId())) {
            log.warn("Someone is trying to modify other's report!");
            return ApiResponse.createByErrorMsg("该报告非本人上传，无法修改，并已通知管理员!");
        }

        int modifyWorkReportRes = workMapper.modifyReportSelective(workReport);
        if (modifyWorkReportRes == 0) {
            log.error("DB Error! uploadDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("修改报告内容成功");
    }

    @Override
    public ApiResponse<String> deleteWorkReport(String userAccount, Integer workReportId) {

        WorkReport workReport = workMapper.findReportByReportId(workReportId);
        if (workReport == null) {
            return ApiResponse.createByErrorMsg("该报告不存在，删除失败!");
        }

        if (!workReport.getAuthor().equals(userAccount)) {
            log.warn("Someone is trying to modify other's report!");
            return ApiResponse.createByErrorMsg("该报告非本人上传，无法删除，并已通知管理员!");
        }

        int deleteRes = workMapper.deleteWorkReportByUserAccountAndId(userAccount, workReportId);
        if (deleteRes == 0) {
            log.error("DB Error! uploadDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        if (!fileUploadUtil.deleteFile(workReport.getReportUrl())) {
            log.error("Delete document pdf file failed!");
            return ApiResponse.createByErrorMsg("报告不存在或删除报告附件失败!");
        }

        return ApiResponse.createBySuccessMsg("删除报告成功!");
    }

    @Override
    public ApiResponse<List<WorkReport>> getTeamWorkReports(String userAccount, Integer type) {

        List<WorkReport> workReports = workMapper.getTeamWorkReports(userAccount, type);
        if (CollectionUtils.isEmpty(workReports)) {
            return ApiResponse.createByErrorMsg("小组内无同学上传该类型报告!");
        }

        return ApiResponse.createBySuccess("查询成功", workReports);
    }

    private boolean checkValid(String userAccount, Integer workReportId) {
        int checkRes = workMapper.checkValid(userAccount, workReportId);
        return checkRes != 0;
    }
}
