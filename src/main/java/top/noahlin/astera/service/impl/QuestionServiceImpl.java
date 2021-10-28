package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.dao.QuestionDAO;
import top.noahlin.astera.dao.UserDAO;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.model.ViewObject;
import top.noahlin.astera.service.LikeService;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.util.SensitiveFilterUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Resource
    QuestionDAO questionDAO;

    @Resource
    UserDAO userDAO;

    @Resource
    SensitiveFilterUtil sensitiveFilterUtil;

    @Resource
    LikeService likeService;

    @Resource
    HostHolder hostHolder;

    @Override
    public int addQuestion(Question question) {
        insertQuestionFilter(question);
        return questionDAO.insert(question) > 0 ? question.getUserId() : 0;
    }

    @Override
    public List<ViewObject> getQuestionList(int userId) {
        List<Question> questions = questionDAO.selectLatest(userId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questions) {
            ViewObject vo = new ViewObject();
            selectQuestionFilter(question);
            vo.set("user", userDAO.selectById(question.getUserId()));
            vo.set("question", question);
            vo.set("likeCount", likeService.getLikeCount(EntityType.QUESTION.getValue(), question.getId()));
            if (hostHolder.getUser() != null){
            vo.set("likeStatus", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.QUESTION.getValue(), question.getId()));
            } else {
                vo.set("likeStatus", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public Question getQuestion(int id) {
        Question question = questionDAO.selectById(id);
        selectQuestionFilter(question);
        return question;
    }

    private void selectQuestionFilter(Question question) {
        question.setTitle(sensitiveFilterUtil.filter(question.getTitle()));
        question.setContent(sensitiveFilterUtil.filter(question.getContent()));
    }

    private void insertQuestionFilter(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
    }

    @Override
    public int updateCommentCount(int id, int commentCount) {
        return questionDAO.updateCommentCount(id, commentCount) > 0 ? id : 0;
    }
}
