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
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(sensitiveFilterService.filter(question.getTitle()));
        question.setContent(sensitiveFilterService.filter(question.getContent()));
        return questionDAO.addQuestion(question) > 0 ? question.getUserId() : 0;
    }

    @Override
    public List<Question> getLatestQuestion(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
