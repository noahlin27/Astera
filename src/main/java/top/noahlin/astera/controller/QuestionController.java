package top.noahlin.astera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.*;
import top.noahlin.astera.service.*;
import top.noahlin.astera.util.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Resource
    QuestionService questionService;

    @Resource
    CommentService commentService;

    @Resource
    UserService userService;

    @Resource
    LikeService likeService;

    @Resource
    HostHolder hostHolder;

    @Resource
    FollowService followService;

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
                                 @PathVariable("questionId") int questionId) {
        ViewObject questionVO = new ViewObject();
        Question question = questionService.getQuestion(questionId);
        questionVO.set("user", userService.getUser(question.getUserId()));
        questionVO.set("question", question);
        if (hostHolder.getUser() == null) {
            questionVO.set("followed", false);
        } else {
            questionVO.set("followed", followService.isFollower(hostHolder.getUser().getId(),
                    EntityType.QUESTION.getValue(), questionId));
        }
        questionVO.set("followerCount", followService.getFollowerCount(EntityType.QUESTION.getValue(),
                questionId));
        List<Integer> followers = followService.getFollowers(EntityType.QUESTION.getValue(), questionId,
                20);
        List<ViewObject> followerInfo = new ArrayList<>();
        for (Integer userId : followers){
            ViewObject userInfo = new ViewObject();
            User follower = userService.getUser(userId);
            userInfo.set("id", userId);
            userInfo.set("name", follower.getName());
            userInfo.set("headUrl", follower.getHeadUrl());
            followerInfo.add(userInfo);
        }
        questionVO.set("followers", followerInfo);

        request.setAttribute("questionVO", questionVO);

        List<Comment> comments = commentService.getCommentsByEntity(questionId, EntityType.QUESTION.getValue());
        List<ViewObject> vos = new ArrayList<>();
        for (Comment comment : comments) {
            ViewObject commentVO = new ViewObject();
            commentVO.set("comment", comment);
            if (hostHolder.getUser() == null) {
                commentVO.set("likeStatus", 0);
            } else {
                commentVO.set("likeStatus", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.COMMENT.getValue(), comment.getId()));
            }
            commentVO.set("likeCount", likeService.getLikeCount(EntityType.COMMENT.getValue(), comment.getId()));
            commentVO.set("user", userService.getUser(comment.getUserId()));
            vos.add(commentVO);
        }
        request.setAttribute("vos", vos);

        return "questionDetail";
    }
}
