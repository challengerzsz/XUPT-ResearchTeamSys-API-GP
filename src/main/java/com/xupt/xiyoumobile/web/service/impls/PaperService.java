package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.security.util.FileUploadUtil;
import com.xupt.xiyoumobile.web.dao.IPaperMapper;
import com.xupt.xiyoumobile.web.entity.Paper;
import com.xupt.xiyoumobile.web.service.IPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    public PaperService(IPaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    @Override
    public ApiResponse<Integer> upload(String userAccount, Paper paper) {

        paper.setAuthor(userAccount);

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
    public ApiResponse<Paper> getMyPaper(String userAccount, Integer type) {
        return getPaperByUserAccountAndType(userAccount, type);
    }

    @Override
    public ApiResponse<Paper> getStudentPaper(String userAccount, Integer type) {
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

    private ApiResponse<String> modifyPaperSelective(Paper paper) {
        int modifyPaperRes = paperMapper.modifyPaperSelective(paper);
        if (modifyPaperRes == 0) {
            log.error("DB Error! modifyPaper failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("更新成功");
    }

    private ApiResponse<Paper> getPaperByUserAccountAndType(String userAccount, Integer type) {
        Paper paper = paperMapper.findPaperByUserAccountAndType(userAccount, type);
        if (paper == null) {
            return ApiResponse.createByErrorMsg("还没有上传该类型的文献!");
        }

        return ApiResponse.createBySuccess("查询成功", paper);
    }

}
