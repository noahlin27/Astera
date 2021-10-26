package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventProducer;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.model.User;
import top.noahlin.astera.model.ViewObject;
import top.noahlin.astera.service.CommentService;
import top.noahlin.astera.service.FollowService;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.service.UserService;
import top.noahlin.astera.util.JsonUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    @Resource
    FollowService followService;

    @Resource
    HostHolder hostHolder;

    @Resource
    EventProducer eventProducer;

    @Resource
    QuestionService questionService;

    @Resource
    UserService userService;

    @Resource
    CommentService commentService;

    @PostMapping("/followUser")
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.USER.getTypeId(), userId);
        eventProducer.fireEvent(new Event(EventType.FOLLOW).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.USER.getTypeId()).
                setEntityId(userId).
                setEntityOwnerId(userId));
        return JsonUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFollowingCount(
                EventType.FOLLOW.getValue(), hostHolder.getUser().getId())));
    }

    @PostMapping("/unfollowUser")
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.USER.getTypeId(), userId);
        return JsonUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFollowingCount(
                EventType.FOLLOW.getValue(), hostHolder.getUser().getId())));
    }

    @PostMapping("/followQuestion")
    @ResponseBody
    public String followQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            return JsonUtil.getJSONString(1, "问题不存在");
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.QUESTION.getTypeId(), questionId);
        eventProducer.fireEvent(new Event(EventType.FOLLOW).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.QUESTION.getTypeId()).
                setEntityId(questionId).
                setEntityOwnerId(questionId));

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowingCount(EntityType.QUESTION.getTypeId(), questionId));
        return JsonUtil.getJSONString(ret ? 0 : 1, info);
    }

    @PostMapping("/unfollowQuestion")
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId") int questionId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        Question question = questionService.getQuestion(questionId);
        if (question == null) {
            return JsonUtil.getJSONString(1, "问题不存在");
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.QUESTION.getTypeId(), questionId);
        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFollowingCount(EntityType.QUESTION.getTypeId(), questionId));
        return JsonUtil.getJSONString(ret ? 0 : 1, info);
    }

    @GetMapping("/user/{userName}/followers")
    public String followers(@PathVariable("userName") String userName, HttpServletRequest request) {
        int curUserId = userService.getUser(userName).getId();
        ViewObject userProfile = new ViewObject();
        List<Integer> followersIds = followService.getFollowers(EntityType.USER.getTypeId(), curUserId,
                10);
        if(hostHolder.getUser() != null){
            int localUserId = hostHolder.getUser().getId();
            request.setAttribute("userProfile", getUserProfile(localUserId, curUserId));
            request.setAttribute("followers", getUserInfo(localUserId, followersIds));
        }else {
            request.setAttribute("userProfile", getUserProfile(0, curUserId));
            request.setAttribute("followers", getUserInfo(0, followersIds));
        }
        return "follow";
    }

    @GetMapping("/user/{userName}/following")
    public String following(@PathVariable("userName") String userName, HttpServletRequest request) {
        int curUserId = userService.getUser(userName).getId();
        ViewObject userProfile = new ViewObject();
        List<Integer> followingIds = followService.getFollowing(EntityType.USER.getTypeId(), curUserId,
                10);
        if(hostHolder.getUser() != null){
            int localUserId = hostHolder.getUser().getId();
            request.setAttribute("userProfile", getUserProfile(localUserId, curUserId));
            request.setAttribute("following", getUserInfo(localUserId, followingIds));
        }else {
            request.setAttribute("userProfile", getUserProfile(0, curUserId));
            request.setAttribute("following", getUserInfo(0, followingIds));
        }
        return "follow";
    }

    private ViewObject getUserProfile(int localUserId, int curUserId){
        ViewObject userProfile = new ViewObject();
        userProfile.set("user", userService.getUser(curUserId));
        userProfile.set("followerCount", followService.getFollowerCount(EntityType.USER.getTypeId(), curUserId));
        userProfile.set("followingCount", followService.getFollowingCount(EntityType.USER.getTypeId(), curUserId));
        userProfile.set("commentCount", commentService.getCommentCount(curUserId));
        if (localUserId != 0) {
            userProfile.set("followed", followService.isFollower(localUserId, EntityType.USER.getTypeId(), curUserId));
        } else {
            userProfile.set("followed", false);
        }
        return userProfile;
    }

    private List<ViewObject> getUserInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer uid : userIds) {
            User user = userService.getUser(uid);
            if (user == null) {
                continue;
            }
            userInfos.add(getUserProfile(localUserId, uid));
        }
        return userInfos;
    }
}
