package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.model.User;
import top.noahlin.astera.model.ViewObject;
import top.noahlin.astera.service.CommentService;
import top.noahlin.astera.service.FollowService;
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

    @Resource
    CommentService commentService;

    @Resource
    FollowService followService;

    @Resource
    HostHolder hostHolder;

    @RequestMapping(value = {"/", "/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(HttpServletRequest request) {
        request.setAttribute("questionListVO", questionService.getQuestionList(0));
        return "index";
    }

    @RequestMapping(value = "/user/{username}", method = {RequestMethod.GET, RequestMethod.POST})
    public String profile(HttpServletRequest request, @PathVariable("username") String username){
        User user = userService.getUser(username);
        ViewObject vo = new ViewObject();
        vo.set("user", userService.getUser(username));
        vo.set("commentCount", commentService.getCommentCount(EntityType.ENTITY_USER.getTypeId(), user.getId()));
        vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER.getTypeId(), user.getId()));
        vo.set("followingCount", followService.getFollowingCount(EntityType.ENTITY_USER.getTypeId(), user.getId()));
        if (hostHolder.getUser() != null) {
            vo.set("followed", followService.isFollower(hostHolder.getUser().getId(),
                    EntityType.ENTITY_USER.getTypeId(), user.getId()));
        } else {
            vo.set("followed", false);
        }
        request.setAttribute("questionListVO", questionService.getQuestionList(userService.getUser(username).getId()));
        request.setAttribute("userProfile", vo);
        return "profile";
    }
}
