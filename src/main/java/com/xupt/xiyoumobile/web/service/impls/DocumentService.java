package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.security.util.FileUploadUtil;
import com.xupt.xiyoumobile.web.dao.IDocumentMapper;
import com.xupt.xiyoumobile.web.entity.Document;
import com.xupt.xiyoumobile.web.service.IDocumentService;
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
 * @date : 2020-06-01 20:08
 */
@Slf4j
@Service
public class DocumentService implements IDocumentService {

    @Value("${upload.document}")
    private String DOCUMENT_UPLOAD_PATH;

    private IDocumentMapper documentMapper;

    @Autowired
    public DocumentService(IDocumentMapper documentMapper) {
        this.documentMapper = documentMapper;
    }

    @Override
    public ApiResponse<List<Document>> searchDocument(Integer type, String content) {
        List<Document> documents = null;
        switch (type) {
            case 0:
                documents = documentMapper.searchDocumentByTopic(content);
                break;
            case 1:
                documents = documentMapper.searchDocumentByAuthor(content);
                break;
            case 2:
                documents = documentMapper.searchDocumentByDirection(content);
                break;
        }

        if (documents == null) {
            return ApiResponse.createBySuccessMsg("无需要检索的文献!");
        }

        return ApiResponse.createBySuccess("检索成功", documents);
    }

    @Override
    public ApiResponse<String> modifyDocument(Principal principal, Document document) {

        int checkRes = documentMapper.checkValid(principal.getName(), document.getId());
        if (checkRes == 0) {
            log.warn("Someone is trying to modify other's document!");
            return ApiResponse.createByErrorMsg("该文献非本人上传，无法修改，并已通知管理员！");
        }

        int modifyRes = documentMapper.modifyDocumentBySelective(document);
        if (modifyRes == 0) {
            log.error("DB Error! modifyDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("修改文献信息成功");
    }

    @Override
    public ApiResponse<String> uploadDocument(Principal principal, Document document) {
        int insertDocumentRes = documentMapper.insertDocument(principal.getName(), document);
        if (insertDocumentRes == 0) {
            log.error("DB Error! uploadDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("添加文献基本信息成功");
    }

    @Override
    public ApiResponse<List<Document>> getMyUploadDocument(String userAccount) {
        List<Document> documents = documentMapper.getMyUploadDocument(userAccount);
        if (CollectionUtils.isEmpty(documents)) {
            return ApiResponse.createByErrorMsg("您还未上传任何文献!");
        }

        return ApiResponse.createBySuccess("查询上传文献成功", documents);
    }

    @Override
    public ApiResponse<String> deleteDocument(String userAccount, Integer documentId) {
        Document document = documentMapper.findByDocumentId(documentId);
        if (document == null) {
            return ApiResponse.createByErrorMsg("不存在该文献，无法删除！");
        }
        if (!userAccount.equals(document.getUploader())) {
            return ApiResponse.createByErrorMsg("该文献非本人上传，无法删除");
        }

        int deleteRes = documentMapper.deleteDocumentByUserAccountAndId(userAccount, documentId);
        if (deleteRes == 0) {
            log.error("DB Error! uploadDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        if (!FileUploadUtil.deleteFile(document.getPdfUrl())) {
            log.error("Delete document pdf file failed!");
            return ApiResponse.createByErrorMsg("报告不存在或删除文献附件失败!");
        }

        return ApiResponse.createBySuccessMsg("删除文献成功");
    }

    @Override
    public ApiResponse<String> uploadDocumentFile(Integer documentId, MultipartFile multipartFile) {
        Document document = documentMapper.findByDocumentId(documentId);
        if (document == null) {
            return ApiResponse.createByErrorMsg("未查询到该文献基本信息，无法进行附件上传");
        }

        String destFilePath = FileUploadUtil.uploadFile(multipartFile, DOCUMENT_UPLOAD_PATH);
        if (destFilePath == null) {
            return ApiResponse.createByErrorMsg("上传文件失败!");
        }

        document.setPdfUrl(destFilePath);
        int modifyDocumentRes = documentMapper.modifyDocumentBySelective(document);
        if (modifyDocumentRes == 0) {
            log.error("DB Error! uploadDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传文献附件成功");
    }
}
