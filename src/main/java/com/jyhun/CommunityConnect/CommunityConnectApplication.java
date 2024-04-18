package com.jyhun.CommunityConnect;

import com.jyhun.CommunityConnect.repository.BoardRepository;
import com.jyhun.CommunityConnect.repository.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CommunityConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityConnectApplication.class, args);
	}

	@Bean
	@Profile("local")
	public DataInit boardDataInit(BoardRepository boardRepository, MemberRepository memberRepository) {
		return new DataInit(boardRepository,memberRepository);
	}

}
