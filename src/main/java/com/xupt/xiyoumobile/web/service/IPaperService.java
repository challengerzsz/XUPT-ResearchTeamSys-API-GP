package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Paper;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:35
 */
public interface IPaperService {

    ApiResponse<String> upload(String userAccount, Paper paper);
}
