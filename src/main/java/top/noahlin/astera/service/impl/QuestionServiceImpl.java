package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import top.noahlin.astera.dao.QuestionDAO;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.service.SensitiveFilterService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    QuestionDAO questionDAO;

    @Resource
    SensitiveFilterService sensitiveFilterService;

    @Override
    public int addQuestion(Question question) {
        questionFilterForWrite(question);
        return questionDAO.addQuestion(question) > 0 ? question.getUserId() : 0;
    }

    @Override
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        List<Question> questions = questionDAO.selectLatestQuestions(userId, offset, limit);
        for (Question question : questions) {
            questionFilterForRead(question);
        }
        return questions;
    }

    @Override
    public Question getQuestion(int id) {
        Question question =questionDAO.selectById(id);
        questionFilterForRead(question);
        return question;
    }

    private void questionFilterForRead(Question question){
        question.setTitle(sensitiveFilterService.filter(question.getTitle()));
        question.setContent(sensitiveFilterService.filter(question.getContent()));
    }

    private void questionFilterForWrite(Question question){
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
    }
}
