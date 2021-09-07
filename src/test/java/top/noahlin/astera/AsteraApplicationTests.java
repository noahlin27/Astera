package top.noahlin.astera;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import top.noahlin.astera.service.SensitiveFilterService;

import javax.annotation.Resource;

@MapperScan("top.noahlin.astera.dao")
@SpringBootTest
class AsteraApplicationTests {
	@Resource
	SensitiveFilterService sensitiveFilterService;

	@Test
	void sensitiveFilterTest() {
    	String text = "坚决抵制毒品";


	}

}
