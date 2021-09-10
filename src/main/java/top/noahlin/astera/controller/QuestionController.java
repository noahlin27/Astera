package top.noahlin.astera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.util.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Resource
    QuestionService questionService;

    @Resource
    HostHolder hostHolder;

    @PostMapping("/question/add")
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,
                              @RequestParam("content") String content) {
        try {
            Question question = new Question();
            if (hostHolder.getUser() == null) {
                return JsonUtil.getJSONString(3);
            } else {
                question.setUserId(hostHolder.getUser().getId());
            }
            question.setTitle(title);
            question.setContent(content);
            question.setCreateTime(new Date());
            question.setCommentCount(0);
            if (questionService.addQuestion(question) > 0) {
                return JsonUtil.getJSONString(0);
            }
        } catch (Exception e) {
            logger.error("增加问题失败" + e.getMessage());
        }
        return JsonUtil.getJSONString(1, "增加问题失败");
    }

    @GetMapping("/question/{questionId}")
    public String questionDetail(HttpServletRequest request,
                           @PathVariable("questionId") int id) {
        Question question = questionService.getQuestion(id);
        request.setAttribute("question", question);
        return "detail";
    }
}
