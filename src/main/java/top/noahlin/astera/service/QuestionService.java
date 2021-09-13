package top.noahlin.astera.service;

import top.noahlin.astera.model.Question;
import top.noahlin.astera.model.ViewObject;

import java.util.List;

public interface QuestionService {
    int addQuestion(Question question);

    List<ViewObject> getQuestionList(int userId);

    Question getQuestion(int id);
}
