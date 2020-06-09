package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Paper;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:35
 */
public interface IPaperService {

    ApiResponse<Integer> upload(String userAccount, Paper paper);

    ApiResponse<String> uploadPaperFile(String userAccount, Integer paperId, MultipartFile multipartFile);

    ApiResponse<List<Paper>> getMyPaper(String userAccount, Integer type);

    ApiResponse<List<Paper>> getStudentPaper(String userAccount, Integer type);

    ApiResponse<String> modifyPaper(String userAccount, Paper paper);

    ApiResponse<List<Paper>> getMyStudentPapers(String userAccount, Integer type);

    ApiResponse<String> deleteSmallPaper(Integer type, Integer paperId);
}
