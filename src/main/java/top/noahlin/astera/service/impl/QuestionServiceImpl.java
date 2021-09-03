package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import top.noahlin.astera.dao.QuestionDAO;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.service.QuestionService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    QuestionDAO questionDAO;

    @Override
    public List<Question> getLatestQuestion(int userId, int offset, int limit){
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
