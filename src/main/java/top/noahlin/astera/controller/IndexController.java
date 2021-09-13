package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Resource
    QuestionService questionService;

    @Resource
    UserService userService;

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request) {
        request.setAttribute("questionListVO", questionService.getQuestionList(0));
        return "index";
    }

    @RequestMapping(value = "/user/{username}", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(HttpServletRequest request, @PathVariable("username") String username){
        request.setAttribute("questionListVO", questionService.getQuestionList(userService.getUser(username).getId()));
        return "profile";
    }
}
