package com.example.mascota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MascotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MascotaApplication.class, args);
	}

}
