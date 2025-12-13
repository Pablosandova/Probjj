package com.probjj.probjj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.probjj.probjj.dao", "com.probjj.probjj.repository"})
public class ProbjjApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProbjjApplication.class, args);
	}

}
