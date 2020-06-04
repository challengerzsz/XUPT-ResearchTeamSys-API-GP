package com.xupt.xiyoumobile;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.config.JwtConfig;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ResearchTeamManagementSysApiApplicationTests {

    @Test
    void contextLoads() {
    }

//    @Autowired
//    private IUserService userService;

//    @Test
//    void testPasswordEncoder() {
//        System.out.println(passwordEncoder.encode("123456"));
//    }
//
//    @Test
//    void testJwtConfig() {
//        System.out.println(JwtConfig.TOKEN_PREFIX);
//        System.out.println(JwtConfig.EXPIRATION);
//    }

    //    user_account, user_password, user_name, major, grade, classify, research_direction, sex
    @Test
    void testUserRegister() {
        User user = new User();
        user.setUserAccount("04163216");
        user.setUserPassword("123456");
        user.setUserName("江婷婷");
        user.setClassify("学硕");
        user.setResearchDirection("AI");
        user.setSex(1);

//        userService.register(user);
    }

}
