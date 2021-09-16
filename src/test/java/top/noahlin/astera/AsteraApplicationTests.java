package top.noahlin.astera;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import top.noahlin.astera.util.SensitiveFilterUtil;

import javax.annotation.Resource;

//@MapperScan("top.noahlin.astera.dao")
@SpringBootTest
class AsteraApplicationTests {
	@Resource
	SensitiveFilterUtil sensitiveFilterUtil;

	@Test
	void sensitiveFilterTest() {
    	String text = "坚决抵制毒品";
		System.out.println(sensitiveFilterUtil.filter(text));

	}

}
