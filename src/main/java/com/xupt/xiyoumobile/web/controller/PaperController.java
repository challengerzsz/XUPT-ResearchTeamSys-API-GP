package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Paper;
import com.xupt.xiyoumobile.web.entity.PaperComment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-15 03:09
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    @GetMapping("{id}")
    public ApiResponse<Paper> getPaper(@PathVariable("id") Integer id) {
        return null;
    }

    public ApiResponse<String> uploadPaper(MultipartFile file) {
        return null;
    }

    @GetMapping
    public ApiResponse<List<PaperComment>> getPaperComment() {
        return null;
    }

    @PostMapping
    public ApiResponse<String> commentOnPaper(String comment) {
        return null;
    }

}
