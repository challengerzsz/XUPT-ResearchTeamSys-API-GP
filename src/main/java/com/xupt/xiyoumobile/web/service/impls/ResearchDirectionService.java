package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IResearchDirectionMapper;
import com.xupt.xiyoumobile.web.entity.ResearchDirection;
import com.xupt.xiyoumobile.web.service.IResearchDirectionService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:55
 */
@Api(description = "ResearchDirection api")
@Slf4j
@Service
public class ResearchDirectionService implements IResearchDirectionService {

    private IResearchDirectionMapper researchDirectionMapper;

    @Autowired
    public ResearchDirectionService(IResearchDirectionMapper researchDirectionMapper) {
        this.researchDirectionMapper = researchDirectionMapper;
    }

    @Override
    public ApiResponse<List<ResearchDirection>> getAll() {

        List<ResearchDirection> researchDirections = researchDirectionMapper.getAll();
        if (CollectionUtils.isEmpty(researchDirections)) {
            return ApiResponse.createByErrorMsg("系统未存在研究方向，待添加");
        }

        return ApiResponse.createBySuccess("查询成功", researchDirections);
    }

    @Override
    public ApiResponse<String> addResearchDirection(ResearchDirection researchDirection) {

        int addRes = researchDirectionMapper.addResearchDirection(researchDirection);
        if (addRes == 0) {
            log.error("researchDirectionMapper insert researchDirection failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "新增研究方向失败");
        }

        return ApiResponse.createBySuccessMsg("新增研究方向成功");
    }
}
