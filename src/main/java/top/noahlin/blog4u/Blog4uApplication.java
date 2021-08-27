package top.noahlin.blog4u;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"mapper", "top.noahlin.blog4u.dao"})
@SpringBootApplication
public class Blog4uApplication {

	public static void main(String[] args) {
		SpringApplication.run(Blog4uApplication.class, args);
	}

}
