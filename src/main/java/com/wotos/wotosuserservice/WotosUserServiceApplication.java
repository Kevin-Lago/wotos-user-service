package com.wotos.wotosuserservice;

import com.wotos.wotosuserservice.dao.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackageClasses = UserRepo.class)
public class WotosUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WotosUserServiceApplication.class, args);
	}

}
