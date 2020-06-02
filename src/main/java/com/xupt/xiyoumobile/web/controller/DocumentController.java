package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Document;
import com.xupt.xiyoumobile.web.service.IDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 18:37
 */
@RestController
@Slf4j
@RequestMapping("/document")
public class DocumentController {

    private IDocumentService documentService;

    @Autowired
    public DocumentController(IDocumentService documentService) {
        this.documentService = documentService;
    }


    /**
     * 按论文题目、作者、研究方向模糊搜索
     * 0 1 2
     * like content
     * @param type
     * @param content
     * @return
     */
    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @GetMapping("/search/{type}")
    public ApiResponse<List<Document>> searchDocument(@PathVariable Integer type, @RequestParam String content) {
        if (StringUtils.isBlank(content)) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "检索内容为空!");
        }

        return documentService.searchDocument(type, content);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/modifyDocument")
    public ApiResponse<String> modifyDocument(Principal principal, Document document) {
        if (document == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误!");
        }

        return documentService.modifyDocument(principal, document);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/uploadDocument")
    public ApiResponse<String> uploadDocument(Principal principal, Document document) {
        if (document == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "文献信息参数错误!");
        }

        return documentService.uploadDocument(principal, document);
    }

    @PreAuthorize(("hasAnyRole('TEACHER, STUDENT')"))
    @PostMapping("/uploadDocumentFile/{documentId}")
    public ApiResponse<String> uploadDocumentFile(@PathVariable("documentId") Integer documentId,
                                                  @RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty() || documentId == null) {
            log.error("uploadDocumentFile failed! ILLEGAL_FILE_ARGUMENT");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "上传文件参数出错!");
        }
        return documentService.uploadDocumentFile(documentId, multipartFile);
    }

    @PreAuthorize(("hasAnyRole('TEACHER, STUDENT')"))
    @GetMapping("/getMyUploadDocument")
    public ApiResponse<List<Document>> getMyUploadDocument(Principal principal) {
        if (principal == null) {
            return ApiResponse.createByErrorMsg("未获取到个人信息，无法查询");
        }

        return documentService.getMyUploadDocument(principal.getName());
    }


    @PreAuthorize(("hasAnyRole('TEACHER, STUDENT')"))
    @PostMapping("/deleteDocument/{documentId}")
    public ApiResponse<String> deleteDocument(@PathVariable Integer documentId, Principal principal) {

        if (principal == null) {
            return ApiResponse.createByErrorMsg("未获取到个人信息，无法查询");
        }

        if (documentId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "文献ID参数出错，删除失败!");
        }

        return documentService.deleteDocument(principal.getName(), documentId);
    }
}
