package pl.tomo.flickrtogoogle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlickrtogoogleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlickrtogoogleApplication.class, args);
	}
}
