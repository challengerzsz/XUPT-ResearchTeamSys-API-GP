package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.ResearchDirection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:56
 */
@Mapper
public interface IResearchDirectionMapper {

    List<ResearchDirection> getAll();

    int addResearchDirection(ResearchDirection researchDirection);
}
