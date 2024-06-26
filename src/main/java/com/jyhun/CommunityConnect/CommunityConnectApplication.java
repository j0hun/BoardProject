package com.jyhun.CommunityConnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommunityConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityConnectApplication.class, args);
	}

}
