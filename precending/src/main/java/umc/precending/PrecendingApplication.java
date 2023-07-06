package umc.precending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PrecendingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrecendingApplication.class, args);
	}

}
