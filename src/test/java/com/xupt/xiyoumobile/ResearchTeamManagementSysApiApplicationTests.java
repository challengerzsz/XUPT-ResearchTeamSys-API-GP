package com.xupt.xiyoumobile;

import com.xupt.xiyoumobile.config.JwtConfig;
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

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testPasswordEncoder() {
        System.out.println(passwordEncoder.encode("123456"));
    }

    @Test
    void testJwtConfig() {
        System.out.println(JwtConfig.TOKEN_PREFIX);
        System.out.println(JwtConfig.EXPIRATION);
    }

//	@Test
//	void testUserService() {
//		User user = userService.selectUserByUserAccount("04163209");
//		if (user == null) System.out.println("null");
//		System.out.println(user.getUserName());
//	}

}
