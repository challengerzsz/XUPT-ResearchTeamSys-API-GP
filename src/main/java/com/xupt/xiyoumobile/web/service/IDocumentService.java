package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Document;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 20:07
 */
public interface IDocumentService {

    ApiResponse<List<Document>> searchDocument(Integer type, String content);
}
