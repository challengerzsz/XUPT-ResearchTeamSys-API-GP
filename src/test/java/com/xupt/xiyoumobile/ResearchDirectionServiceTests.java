package com.xupt.xiyoumobile;

import com.xupt.xiyoumobile.web.entity.ResearchDirection;
import com.xupt.xiyoumobile.web.service.IResearchDirectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 23:03
 */
@SpringBootTest
public class ResearchDirectionServiceTests {

    @Autowired
    private IResearchDirectionService researchDirectionService;

    @Test
    void testGetAllResearchDirection() {
        List<ResearchDirection> researchDirections = researchDirectionService.getAll().getData();
        for (ResearchDirection researchDirection : researchDirections) {
            System.out.println(researchDirection.getDirectionName() + " " + researchDirection.getCreateTime());
        }
    }
}
