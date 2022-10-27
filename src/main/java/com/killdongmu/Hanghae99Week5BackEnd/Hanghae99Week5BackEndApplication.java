package com.killdongmu.Hanghae99Week5BackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableJpaAuditing
@SpringBootApplication
public class Hanghae99Week5BackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hanghae99Week5BackEndApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:3000", "3.34.146.228", "3.34.146.228:3000", "3.34.146.228:3001")
						.exposedHeaders("Authorization","RefreshToken","username")
						.allowCredentials(true)
						.allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
			}
		};
	}

}
