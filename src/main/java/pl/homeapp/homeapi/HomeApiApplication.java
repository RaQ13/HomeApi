package pl.homeapp.homeapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomeApiApplication {

	//odpalenie swaggera http://localhost:8080/swagger-ui/index.html#/

	public static void main(String[] args) {
		SpringApplication.run(HomeApiApplication.class, args);
	}

}
