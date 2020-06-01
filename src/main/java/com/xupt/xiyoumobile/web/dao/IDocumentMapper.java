package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Document;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 20:12
 */
@Mapper
public interface IDocumentMapper {


    List<Document> searchDocumentByTopic(String content);

    List<Document> searchDocumentByAuthor(String content);

    List<Document> searchDocumentByDirection(String content);
}
