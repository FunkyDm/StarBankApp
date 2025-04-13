package pro.sky.StarBankApp.StarBankApp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class StarBankAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarBankAppApplication.class, args);
	}

}
