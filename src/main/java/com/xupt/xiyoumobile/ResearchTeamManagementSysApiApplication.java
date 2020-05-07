package com.xupt.xiyoumobile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@MapperScan(basePackages = "com.xupt.xiyoumobile.web.dao")
public class ResearchTeamManagementSysApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchTeamManagementSysApiApplication.class, args);
	}

}
