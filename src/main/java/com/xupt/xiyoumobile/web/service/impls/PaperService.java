package com.xupt.xiyoumobile.web.service.impls;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.dao.IPaperMapper;
import com.xupt.xiyoumobile.web.entity.Paper;
import com.xupt.xiyoumobile.web.service.IPaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:37
 */
@Slf4j
@Service
public class PaperService implements IPaperService {

    private IPaperMapper paperMapper;

    @Autowired
    public PaperService(IPaperMapper paperMapper) {
        this.paperMapper = paperMapper;
    }

    @Override
    public ApiResponse<String> upload(String userAccount, Paper paper) {

        paper.setAuthor(userAccount);

        int insertPaperRes = paperMapper.insertPaper(paper);
        if (insertPaperRes == 0) {
            log.error("DB Error upload paper info failed!");
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.DB_ERROR.getCode(), "DB Error!");
        }

        return ApiResponse.createBySuccessMsg("上传论文信息成功");
    }

}
