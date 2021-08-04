package top.noahlin.blog4u;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import top.noahlin.blog4u.dao.UserDAO;
import top.noahlin.blog4u.model.User;

import javax.annotation.Resource;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Blog4uApplication.class)
@Sql("/init-schema.sql")
@MapperScan("top.noahlin.blog4u.dao")
public class InitDatabaseTests {
    @Resource
    UserDAO userDAO;

    @Test
    public void contextLoads() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            user.setIsDeleted(0);
            userDAO.addUser(user);
        }
    }
}
