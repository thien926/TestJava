package io.getarrays.server;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import io.getarrays.server.enumeration.Status;
import io.getarrays.server.model.Server;
import io.getarrays.server.repository.ServerRepository;

@SpringBootApplication
public class ServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(ServerRepository serverRepository) {
		return args -> {
			serverRepository.save(new Server(null, "192.168.1.160", "Ubuntu Linux", "16GB", "Personal PC", "http://localhost:8080/server/image/server1.png", Status.SERVER_UP));
			serverRepository.save(new Server(null, "192.168.1.58", "Fedora Linux", "16GB", "Dell Tower", "http://localhost:8080/server/image/server2.png", Status.SERVER_DOWN));
			serverRepository.save(new Server(null, "192.168.1.3", "MS 2008", "32GB", "Web Server", "http://localhost:8080/server/image/server3.png", Status.SERVER_UP));
			serverRepository.save(new Server(null, "192.168.1.14", "Red Hat Enterprise Linux", "64GB", "Mail Server", "http://localhost:8080/server/image/server4.png", Status.SERVER_UP));
		};
	}
	
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:4200"));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept",
				"Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
	            "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token",
	            "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
	            "Filename"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);
	}
}
