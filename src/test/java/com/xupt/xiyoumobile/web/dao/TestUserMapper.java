package com.xupt.xiyoumobile.web.dao;

import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 17:52
 */
@SpringBootTest
public class TestUserMapper {

    @Autowired
    private IUserService userService;

    @Test
    private void testUserService() {
        User user = userService.selectUserByUserAccount("04163209");
        if (user == null) System.out.println("null");
        System.out.println(user.getUserName());
    }
}
