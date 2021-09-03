package top.noahlin.astera.service;

import top.noahlin.astera.model.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getLatestQuestion(int userId, int offset, int limit);
}
