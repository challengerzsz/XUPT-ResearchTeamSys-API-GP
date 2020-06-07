package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.security.util.FileUploadUtil;
import com.xupt.xiyoumobile.web.dao.IPaperMapper;
import com.xupt.xiyoumobile.web.dao.IUserMapper;
import com.xupt.xiyoumobile.web.entity.Paper;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:37
 */
@Slf4j
@Service
public class PaperService implements IPaperService {

    @Value("${upload.paper}")
    private String PAPER_UPLOAD_PATH;

    private IPaperMapper paperMapper;

    private IUserMapper userMapper;

    @Autowired
    public PaperService(IPaperMapper paperMapper, IUserMapper userMapper) {
        this.paperMapper = paperMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse<Integer> upload(String userAccount, Paper paper) {

        User user = userMapper.findByUsername(userAccount);
        paper.setAuthor(userAccount);
        paper.setAuthorName(user.getUserName());
        int insertPaperRes = paperMapper.insertPaper(paper);
        if (insertPaperRes == 0) {
            log.error("DB Error upload paper info failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccess("上传论文信息成功", paper.getId());
    }

    @Override
    public ApiResponse<String> uploadPaperFile(String userAccount, Integer paperId, MultipartFile multipartFile) {

        Paper paper = paperMapper.findPaperById(paperId);
        if (paper == null) {
            return ApiResponse.createByErrorMsg("不存在该论文，上传文件失败!");
        }

        String uploadPaperFilePath = FileUploadUtil.uploadFile(multipartFile, PAPER_UPLOAD_PATH);
        if (uploadPaperFilePath == null) {
            return ApiResponse.createByErrorMsg("上传论文附件失败");
        }

        paper.setPdfUrl(uploadPaperFilePath);

        return modifyPaperSelective(paper);
    }

    @Override
    public ApiResponse<List<Paper>> getMyPaper(String userAccount, Integer type) {
        return getPaperByUserAccountAndType(userAccount, type);
    }

    @Override
    public ApiResponse<List<Paper>> getStudentPaper(String userAccount, Integer type) {
        return getPaperByUserAccountAndType(userAccount, type);
    }

    @Override
    public ApiResponse<String> modifyPaper(String userAccount, Paper paper) {

        Paper paperInfo = paperMapper.findPaperById(paper.getId());
        if (paperInfo == null) {
            return ApiResponse.createByErrorMsg("该论文不存在，无法修改信息");
        }
        if (!userAccount.equals(paperInfo.getAuthor())) {
            log.warn("Someone is trying to modify other's paperInfo {}", userAccount);
            return ApiResponse.createByErrorMsg("该论文非您上传，无权修改，并已通知管理员!");
        }

        return modifyPaperSelective(paper);
    }

    @Override
    public ApiResponse<List<Paper>> getMyStudentPapers(String userAccount, Integer type) {

        List<Paper> papers = paperMapper.getMyStudentPapers(userAccount, type);
        if (CollectionUtils.isEmpty(papers)) {
            return ApiResponse.createByErrorMsg("您管理的学生还未有人上传论文!");
        }

        return ApiResponse.createBySuccess("查询成功", papers);

    }

    private ApiResponse<String> modifyPaperSelective(Paper paper) {
        int modifyPaperRes = paperMapper.modifyPaperSelective(paper);
        if (modifyPaperRes == 0) {
            log.error("DB Error! modifyPaper failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("更新成功");
    }

    private ApiResponse<List<Paper>> getPaperByUserAccountAndType(String userAccount, Integer type) {
        List<Paper> papers = paperMapper.findPaperByUserAccountAndType(userAccount, type);
        if (CollectionUtils.isEmpty(papers)) {
            return ApiResponse.createByErrorMsg("还没有上传该类型的论文!");
        }

        return ApiResponse.createBySuccess("查询成功", papers);
    }

}
