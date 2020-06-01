package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Document;
import com.xupt.xiyoumobile.web.service.IDocumentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 18:37
 */
@RestController
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
     * like content%
     * @param type
     * @param content
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN, TEACHER, STUDENT')")
    @GetMapping("/search/{type}/{content}")
    public ApiResponse<List<Document>> searchDocument(@PathVariable Integer type, @PathVariable String content) {
        if (type < 0 || type > 2 || StringUtils.isBlank(content)) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }

        return documentService.searchDocument(type, content);
    }
}
