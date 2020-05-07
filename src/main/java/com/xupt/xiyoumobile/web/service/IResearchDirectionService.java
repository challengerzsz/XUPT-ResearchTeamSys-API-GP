package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.ResearchDirection;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:54
 */
public interface IResearchDirectionService {

    ApiResponse<List<ResearchDirection>> getAll();

    ApiResponse<String> addResearchDirection(ResearchDirection researchDirection);
}
