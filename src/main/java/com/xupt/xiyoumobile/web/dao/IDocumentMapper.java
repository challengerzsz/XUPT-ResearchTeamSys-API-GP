package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Document;
import com.xupt.xiyoumobile.web.entity.DocumentComment;
import com.xupt.xiyoumobile.web.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    int modifyDocumentBySelective(Document document);

    int insertDocument(@Param("userAccount") String userAccount, @Param("document") Document document);

    Document findByDocumentId(Integer documentId);

    int deleteDocumentByUserAccountAndId(@Param("userAccount") String userAccount, @Param("documentId") Integer documentId);

    List<Document> getMyUploadDocument(String userAccount);

    int checkValid(@Param("userAccount") String userAccount, @Param("id") Integer id);

    List<Document> searchMyDocumentByTopic(@Param("userAccount") String userAccount, @Param("content") String content);

    List<Document> searchMyDocumentByAuthor(@Param("userAccount") String userAccount, @Param("content") String content);

    List<Document> searchMyDocumentByDirection(@Param("userAccount") String userAccount, @Param("content") String content);

    List<Document> getAllDocument();

    List<DocumentComment> getCommentsByDocumentId(Integer documentId);

    int insertDocumentComment(@Param("documentId") Integer documentId, @Param("content") String content,
                              @Param("user") User user);

    List<Document> getAllMyDocuments(String userAccount);
}
