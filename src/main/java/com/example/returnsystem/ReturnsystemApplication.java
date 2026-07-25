package com.example.returnsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.returnsystem.model.Seller;
import com.example.returnsystem.repository.SellerRepository;

@SpringBootApplication
public class ReturnsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReturnsystemApplication.class, args);
	}

	// 🔐 Default Seller Creator (RUNS ON START)
	@Bean
	CommandLineRunner init(SellerRepository repo, PasswordEncoder encoder) {
		return args -> {
			if (repo.findByEmail("admin@gmail.com") == null) {
				Seller s = new Seller();
				s.setEmail("admin@gmail.com");
				s.setPassword(encoder.encode("1234"));
				repo.save(s);

				System.out.println("✅ Default seller created: admin@gmail.com / 1234");
			}
		};
	}
}