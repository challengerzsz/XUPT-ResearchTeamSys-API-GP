package com.xupt.xiyoumobile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = "com.xupt.xiyoumobile.web.dao")
public class ResearchTeamManagementSysApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResearchTeamManagementSysApiApplication.class, args);
	}

}
