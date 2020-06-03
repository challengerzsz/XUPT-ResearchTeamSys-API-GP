package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Competition;
import com.xupt.xiyoumobile.web.entity.Patent;
import com.xupt.xiyoumobile.web.entity.SoftWareCopyright;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:52
 */
public interface IAchievementService {

    ApiResponse<String> uploadCompetition(Competition competition);

    ApiResponse<List<Competition>> getAllCompetition();

    ApiResponse<String> deleteCompetition(Integer competitionId);

    ApiResponse<String> modifyCompetition(Competition competition);

    ApiResponse<String> uploadPatent(Patent patent);

    ApiResponse<String> deletePatent(Integer patentId);

    ApiResponse<String> uploadPatentFile(Integer patentId, MultipartFile multipartFile);

    ApiResponse<List<Patent>> getAllPatent();

    ApiResponse<String> modifyPatent(Patent patent);

    ApiResponse<String> uploadSoftWareCopyright(SoftWareCopyright softWareCopyright);

    ApiResponse<String> uploadSoftWareCopyrightFiles(Integer softWareCopyrightId, MultipartFile document,
                                                     MultipartFile project, MultipartFile certificate);

    ApiResponse<String> modifySoftWareCopyright(SoftWareCopyright softWareCopyright);

    ApiResponse<List<SoftWareCopyright>> getAllSoftWareCopyright();
}
