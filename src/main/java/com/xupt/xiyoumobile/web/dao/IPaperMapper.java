package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:40
 */
@Mapper
public interface IPaperMapper {

    int insertPaper(Paper paper);

    Paper findPaperById(Integer paperId);

    int modifyPaperSelective(Paper paper);

    List<Paper> findPaperByUserAccountAndType(@Param("userAccount") String userAccount, @Param("type") Integer type);
}
