package top.noahlin.astera;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import top.noahlin.astera.dao.LoginTicketDAO;
import top.noahlin.astera.dao.QuestionDAO;
import top.noahlin.astera.dao.UserDAO;
import top.noahlin.astera.model.LoginTicket;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.model.User;
import top.noahlin.astera.util.MD5Util;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AsteraApplication.class)
//@Sql("/init-schema.sql")
@MapperScan({"top.noahlin.astera.dao"})
public class DatabaseCRUDTests {
    @Resource
    UserDAO userDAO;

    @Resource
    QuestionDAO questionDAO;

    @Resource
    LoginTicketDAO loginTicketDAO;

    @Test
    public void AddUserTest() {
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            User user = new User();
            user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            user.setIsDeleted(0);
            userDAO.addUser(user);
        }

        User user = new User();
        user.setId(random.nextInt(10) + 1);
        user.setPassword("password");
        userDAO.updatePassword(user);
        userDAO.deleteById(random.nextInt(10) + 1);
    }

    @Test
    public void SelectUserTest() {
        for (int i = 0; i < 10; ++i) {
            System.out.println(userDAO.selectById(i + 1));
        }
    }

    @Test
    public void SetUserPassword() {
        for (int i = 1; i <= 10; ++i) {
            User user = new User();
            user.setSalt(UUID.randomUUID().toString().substring(0, 5));
            user.setId(i);
            user.setPassword(MD5Util.Encode("123456" + user.getSalt(), "utf-8"));
            userDAO.updatePassword(user);
        }
    }

    @Test
    public void AddQuestionTest() {
        Random random = new Random();
        for (int i = 0; i < 10; ++i) {
            Question question = new Question();
            question.setCommentCount(random.nextInt(10) + 1);
            Date date = new Date();
            date.setTime(date.getTime() + 1000L * 3600 * i);
            question.setCreateTime(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("Content %d", i));
            questionDAO.addQuestion(question);
        }
    }

    @Test
    public void SelectQuestionTest() {
        for (Question question : questionDAO.selectLatestQuestions(0, 0, 10))
            System.out.println(question.toString());
    }

    @Test
    public void addLoginTicketTest() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        Date now = new Date();
        now.setTime(now.getTime() + 3600);
        loginTicket.setExpired(now);
        loginTicketDAO.addLoginTicket(loginTicket);
    }
}
