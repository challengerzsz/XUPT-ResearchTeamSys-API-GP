package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.ResearchDirection;
import com.xupt.xiyoumobile.web.service.IResearchDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:52
 */
@RestController
@RequestMapping("/researchDirection")
public class ResearchDirectionController {

    private IResearchDirectionService researchDirectionService;

    @Autowired
    public ResearchDirectionController(IResearchDirectionService researchDirectionService) {
        this.researchDirectionService = researchDirectionService;
    }

    @PreAuthorize("hasAnyRole('ADMIN, STUDENT')")
    @GetMapping("/getAll")
    public ApiResponse<List<ResearchDirection>> getAllResearchDirection() {
        return researchDirectionService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addResearchDirection")
    public ApiResponse<String> addResearchDirection(@Valid ResearchDirection researchDirection) {
        return researchDirectionService.addResearchDirection(researchDirection);
    }
}
