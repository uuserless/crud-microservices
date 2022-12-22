package ru.uuserless.example.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.uuserless.example.userservice.config.ApplicationRunner;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoRepositories
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationStartupRunner() {
		return new ApplicationRunner();
	}
}
