package top.noahlin.astera;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("top.noahlin.astera.dao")
@SpringBootApplication
public class AsteraApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsteraApplication.class, args);
	}

}
