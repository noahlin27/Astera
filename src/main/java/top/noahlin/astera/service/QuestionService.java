package top.noahlin.astera.service;

import top.noahlin.astera.model.Question;

import java.util.List;

public interface QuestionService {
    int addQuestion(Question question);

    List<Question> getLatestQuestions(int userId, int offset, int limit);

    Question getQuestion(int id);
}
