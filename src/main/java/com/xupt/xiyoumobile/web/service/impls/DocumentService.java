package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.dao.IDocumentMapper;
import com.xupt.xiyoumobile.web.entity.Document;
import com.xupt.xiyoumobile.web.service.IDocumentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 20:08
 */
@Slf4j
@Service
public class DocumentService implements IDocumentService {

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
}
