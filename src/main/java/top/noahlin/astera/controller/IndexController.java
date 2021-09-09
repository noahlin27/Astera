package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.model.ViewObject;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.service.UserService;

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
        request.setAttribute("vos", getQuestions(0));

//        request.setAttribute("user", getQuestions(0).get(0).get("user")); //待修改

        return "index";
    }

    @RequestMapping(value = "/user/{username}", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(HttpServletRequest request, @PathVariable("username") String username){
        request.setAttribute("vos", getQuestions(userService.getUser(username).getId()));
//        request.setAttribute("user", userService.getUser(username));
        return "profile";
    }

    public List<ViewObject> getQuestions(int userId){
        List<Question> questionList = questionService.getLatestQuestions(userId, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
