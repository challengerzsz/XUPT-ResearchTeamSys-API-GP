package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IDocumentMapper;
import com.xupt.xiyoumobile.web.entity.Document;
import com.xupt.xiyoumobile.web.service.IDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public ApiResponse<String> modifyDocument(Document document) {
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

        int deleteRes = documentMapper.deleteDocumentByUserAccount(userAccount, documentId);
        if (deleteRes == 0) {
            log.error("DB Error! uploadDocument failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        File delFile = new File(document.getPdfUrl());
        if (delFile.exists()) {
            if (!delFile.delete()) {
                log.error("Delete document pdf file failed!");
                return ApiResponse.createByErrorMsg("删除文献附件失败!");
            }
        } else {
            return ApiResponse.createBySuccessMsg("文献附件不存在，删除文献基本信息成功");
        }


        return ApiResponse.createBySuccessMsg("删除文献成功");
    }

    @Override
    public ApiResponse<String> uploadDocumentFile(Integer documentId, MultipartFile multipartFile) {
        Document document = documentMapper.findByDocumentId(documentId);
        if (document == null) {
            return ApiResponse.createByErrorMsg("未查询到该文献基本信息，无法进行附件上传");
        }

        String fileOriginName = multipartFile.getOriginalFilename();
        if (fileOriginName == null) {
            log.error("Get file original name failed! fileOriginName is null!");
            return ApiResponse.createByErrorMsg("获取文件原始名失败!");
        }
        String destFilePath = DOCUMENT_UPLOAD_PATH + fileOriginName;
        File destFile = new File(destFilePath);
        try {
            multipartFile.transferTo(destFile);
        } catch (IOException e) {
            log.error("multipartFile transferTo a new file errored!");
            e.printStackTrace();
        }

        document.setPdfUrl(destFilePath);
        documentMapper.modifyDocumentBySelective(document);

        return ApiResponse.createBySuccessMsg("上传文献附件成功");
    }
}
