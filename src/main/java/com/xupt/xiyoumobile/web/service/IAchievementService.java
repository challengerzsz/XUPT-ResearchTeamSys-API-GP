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

    ApiResponse<Integer> uploadPatent(Patent patent);

    ApiResponse<String> deletePatent(Integer patentId);

    ApiResponse<String> uploadPatentFile(String userAccount, Integer patentId, MultipartFile multipartFile);

    ApiResponse<List<Patent>> getAllPatent();

    ApiResponse<String> modifyPatent(Patent patent);

    ApiResponse<Integer> uploadSoftWareCopyright(SoftWareCopyright softWareCopyright);

    ApiResponse<String> uploadSoftWareCopyrightFile(String userAccount, Integer softWareCopyrightId,
                                                    MultipartFile file, Integer type);

    ApiResponse<String> modifySoftWareCopyright(SoftWareCopyright softWareCopyright);

    ApiResponse<List<SoftWareCopyright>> getAllSoftWareCopyright();

    ApiResponse<String> deleteSoftWareCopyright(Integer scId);

    ApiResponse<String> uploadCompetitionFile(Integer competitionId, MultipartFile multipartFile, String userAccount);
}
