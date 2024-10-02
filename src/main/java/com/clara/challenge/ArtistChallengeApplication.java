package com.clara.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ArtistChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtistChallengeApplication.class, args);
	}

}
