package com.xupt.xiyoumobile;

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
public class UserServiceTests {

    @Autowired
    private IUserService userService;

    @Test
    void testUserService() {
        User user = userService.selectUserByUserAccount("04163209");
        if (user == null) System.out.println("null");
        System.out.println(user.getUserName());
    }

//    user_account, user_password, user_name, major, grade, classify, research_direction, sex
    @Test
    void testUserRegister() {
        User user = new User();
        user.setUserAccount("04163216");
        user.setUserPassword("123456");
        user.setUserName("江婷婷");
        user.setMajor("软件工程");
        user.setGrade(1);
        user.setClassify("学硕");
        user.setResearchDirection(1);
        user.setSex(1);

        userService.register(user);
    }

    @Test
    void testModifyUserInfo() {
        User user = userService.selectUserByUserAccount("04163209");
        user.setPersonalSignature("还是越努力越幸运！");
        userService.modifyInfo(user);
    }
}
