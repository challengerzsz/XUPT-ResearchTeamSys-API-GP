package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Paper;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:35
 */
public interface IPaperService {

    ApiResponse<Integer> upload(String userAccount, Paper paper);

    ApiResponse<String> uploadPaperFile(String userAccount, Integer paperId, MultipartFile multipartFile);

    ApiResponse<Paper> getMyPaper(String userAccount, Integer type);

    ApiResponse<Paper> getStudentPaper(String userAccount, Integer type);

    ApiResponse<String> modifyPaper(String userAccount, Paper paper);
}
