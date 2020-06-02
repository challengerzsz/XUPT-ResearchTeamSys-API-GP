package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 20:07
 */
public interface IDocumentService {

    ApiResponse<List<Document>> searchDocument(Integer type, String content);

    ApiResponse<String> modifyDocument(Principal principal, Document document);

    ApiResponse<String> uploadDocumentFile(Integer documentId, MultipartFile multipartFile);

    ApiResponse<String> uploadDocument(Principal principal, Document document);

    ApiResponse<List<Document>> getMyUploadDocument(String userAccount);

    ApiResponse<String> deleteDocument(String userAccount, Integer documentId);

}