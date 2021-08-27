package top.noahlin.blog4u.service;

import org.springframework.stereotype.Service;
import top.noahlin.blog4u.dao.QuestionDAO;
import top.noahlin.blog4u.model.Question;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionService {
    @Resource
    QuestionDAO questionDAO;

    public List<Question> getLatestQuestion(int userId, int offset, int limit){
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

}
