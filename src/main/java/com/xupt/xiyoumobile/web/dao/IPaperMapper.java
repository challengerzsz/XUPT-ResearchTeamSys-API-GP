package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Paper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:40
 */
@Mapper
public interface IPaperMapper {

    int insertPaper(Paper paper);
}
