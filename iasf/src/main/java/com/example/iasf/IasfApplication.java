package com.example.iasf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.iasf.entity.User;
import com.example.iasf.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class IasfApplication {
	@Bean
	CommandLineRunner seed(UserRepository repo) {
		return args -> {
			repo.save(new User(null, "Sam", 30, 70000.0));
			repo.save(new User(null, "Tom", 40, 50000.0));
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(IasfApplication.class, args);
	}

}
