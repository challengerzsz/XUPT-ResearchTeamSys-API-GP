package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Paper;
import com.xupt.xiyoumobile.web.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:32
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    private IPaperService paperService;

    @Autowired
    public PaperController(IPaperService paperService) {
        this.paperService = paperService;
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/upload")
    public ApiResponse<String> uploadPaper(Principal principal, Paper paper) {

        if (paper == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "参数错误!");
        }

        return paperService.upload(principal.getName(), paper);
    }
}
