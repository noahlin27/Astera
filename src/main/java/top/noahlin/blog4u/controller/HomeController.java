package top.noahlin.blog4u.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.noahlin.blog4u.model.News;
import top.noahlin.blog4u.model.ViewObject;
import top.noahlin.blog4u.service.NewsService;
import top.noahlin.blog4u.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Resource
    NewsService newsService;

    @Resource
    UserService userService;

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request) {
        List<News> newsList = newsService.getLatestNews(0, 0, 10);
        List<ViewObject> vos = new ArrayList<>();
        for (News news : newsList) {
            ViewObject vo = new ViewObject();
            vo.set("news", news);
            vo.set("user", userService.getUser(news.getUserId()));
            vos.add(vo);
        }
        request.setAttribute("vos", vos);
        request.setAttribute("user", vos.get(0).get("user"));
//        request.setAttribute("user", null);
        return "index";
    }
}
