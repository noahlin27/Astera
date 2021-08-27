package top.noahlin.blog4u;

import org.junit.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import top.noahlin.blog4u.dao.NewsDAO;
import top.noahlin.blog4u.dao.QuestionDAO;
import top.noahlin.blog4u.dao.UserDAO;
import top.noahlin.blog4u.model.News;
import top.noahlin.blog4u.model.Question;
import top.noahlin.blog4u.model.User;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Blog4uApplication.class)
//@Sql("/init-schema.sql")
@MapperScan({"top.noahlin.blog4u.dao"})
public class InitDatabaseTests {
    @Resource
    UserDAO userDAO;

    @Resource
    NewsDAO newsDAO;

    @Resource
    QuestionDAO questionDAO;

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
    public void NewsTests() {
        for (int i = 0; i < 10; ++i) {
            News news = new News();
            news.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * 5 * i);
            news.setCreateTime(date);
            news.setImage(String.format("https://images.nowcoder.com/head/%dt.png", i));
            news.setLikeCount(i);
            news.setUserId(i);
            news.setTitle(String.format("TITLE{%d}", i));
            news.setLink("https://noahlin27.top");
            newsDAO.addNews(news);
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
    public void SelectQuestionTest(){
        for (Question question : questionDAO.selectLatestQuestions(0, 0, 10))
            System.out.println(question.toString());
    }
}
