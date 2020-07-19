package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.core.websocket.config.WebSocketBrokerConfiguration;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.dao.IWeeklyReportMapper;
import com.xupt.xiyoumobile.web.entity.Team;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.entity.WeeklyReport;
import com.xupt.xiyoumobile.web.entity.WeeklyReportComment;
import com.xupt.xiyoumobile.web.service.IWeeklyReportService;
import com.xupt.xiyoumobile.web.vo.SimpleUserInfoVo;
import com.xupt.xiyoumobile.web.vo.WeeklyReportVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 15:49
 */
@Slf4j
@Service
public class WeeklyReportService implements IWeeklyReportService {

    @Value("${im-push.websocket.private}")
    public String PRIVATE_TO_USER;

    private IWeeklyReportMapper weeklyReportMapper;

    private IUserMapper userMapper;

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WeeklyReportService(IWeeklyReportMapper weeklyReportMapper, IUserMapper userMapper,
                               SimpMessagingTemplate simpMessagingTemplate) {
        this.weeklyReportMapper = weeklyReportMapper;
        this.userMapper = userMapper;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public ApiResponse<String> uploadWeeklyReport(String userAccount, WeeklyReport weeklyReport) {

        User user = userMapper.findByUsername(userAccount);
        weeklyReport.setUserAccount(userAccount);
        weeklyReport.setUserName(user.getUserName());
        int insertWeeklyReportRes = weeklyReportMapper.insertWeeklyReport(weeklyReport);
        if (insertWeeklyReportRes == 0) {
            log.error("DB Error! insertWeeklyReport failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }


        List<SimpleUserInfoVo> guideTeaches = userMapper.getUserGuideTeacherByUserAccount(userAccount);
        for (SimpleUserInfoVo vo : guideTeaches) {
            simpMessagingTemplate.convertAndSendToUser(vo.getUserAccount(), PRIVATE_TO_USER,
                    "已完成周报");
        }

        return ApiResponse.createBySuccessMsg("提交周报成功!");
    }

    @Override
    public ApiResponse<List<WeeklyReport>> getOldWeeklyReports(String userAccount) {

        List<WeeklyReport> weeklyReports = weeklyReportMapper.getAllWeeklyReportByUserAccount(userAccount);
        if (CollectionUtils.isEmpty(weeklyReports)) {
            return ApiResponse.createByErrorMsg("暂未提交任何周报");
        }

        return ApiResponse.createBySuccess("查询成功", weeklyReports);
    }

    @Override
    public ApiResponse<String> commentOnWeeklyReport(String userAccount, Integer weeklyReportId, String comment) {

        User user = userMapper.findByUsername(userAccount);

        WeeklyReport weeklyReport = weeklyReportMapper.findWeeklyReportById(weeklyReportId);
        if (weeklyReport == null) {
            return ApiResponse.createByErrorMsg("该周报不存在，评论失败");
        }


        int insertWeeklyReportCommentRes =
                weeklyReportMapper.insertWeeklyReportComment(weeklyReportId, userAccount,
                        user.getUserName(), comment);
        if (insertWeeklyReportCommentRes == 0) {
            log.error("DB Error! insertWeeklyReportComment failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("提交周报批注成功");
    }

    @Override
    public ApiResponse<WeeklyReportVo> getWeeklyReport(Integer weeklyReportId) {

        WeeklyReport weeklyReport = weeklyReportMapper.findWeeklyReportById(weeklyReportId);
        if (weeklyReport == null) {
            return ApiResponse.createByErrorMsg("该周报不存在，查询失败!");
        }

        List<WeeklyReportComment> weeklyReportComments = weeklyReportMapper.findWeeklyReportCommentById(weeklyReportId);
        WeeklyReportVo weeklyReportVo = new WeeklyReportVo(weeklyReport, weeklyReportComments);

        return ApiResponse.createBySuccess("查询成功", weeklyReportVo);
    }

    @Override
    public ApiResponse<List<WeeklyReportComment>> getComments(Integer weeklyReportId) {

        List<WeeklyReportComment> weeklyReportComments = weeklyReportMapper.getCommentsByWeeklyReportId(weeklyReportId);
        if (CollectionUtils.isEmpty(weeklyReportComments)) {
            return ApiResponse.createByErrorMsg("该周报无教师批注!");
        }

        return ApiResponse.createBySuccess("查询成功", weeklyReportComments);
    }

    @Override
    public ApiResponse<List<WeeklyReport>> getTeamWeeklyReport(Integer teamId) {

        List<WeeklyReport> weeklyReports = weeklyReportMapper.getTeamWeeklyReport(teamId);
        if (CollectionUtils.isEmpty(weeklyReports)) {
            return ApiResponse.createByErrorMsg("该小组内无周报信息");
        }

        return ApiResponse.createBySuccess("查询成功", weeklyReports);
    }
}
