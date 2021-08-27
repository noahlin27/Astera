package top.noahlin.blog4u.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.noahlin.blog4u.model.Question;
import top.noahlin.blog4u.model.ViewObject;
import top.noahlin.blog4u.service.QuestionService;
import top.noahlin.blog4u.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Resource
    QuestionService questionService;

    @Resource
    UserService userService;

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request) {
        List<Question> questionList = questionService.getLatestQuestion(0, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        request.setAttribute("vos", vos);
        request.setAttribute("user", vos.get(0).get("user"));
        return "index";
    }

    @RequestMapping(value = "/user/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(HttpServletRequest request, @PathVariable("userId") int userId){
        List<Question> questionList = questionService.getLatestQuestion(userId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        request.setAttribute("vos", vos);
        request.setAttribute("user", vos.get(0).get("user"));
        return "index";
    }
}
