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

    @PostMapping("/followUser")
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_USER.getTypeId(), userId);
        eventProducer.fireEvent(new Event(EventType.FOLLOW).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.ENTITY_USER.getTypeId()).
                setEntityId(userId).
                setEntityOwnerId(userId));
        return JsonUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(
                EventType.FOLLOW.getValue(), hostHolder.getUser().getId())));
    }

    @PostMapping("/unfollowUser")
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        if (hostHolder.getUser() == null) {
            return JsonUtil.getJSONString(999);
        }

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_USER.getTypeId(), userId);
        eventProducer.fireEvent(new Event(EventType.UNFOLLOW).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.ENTITY_USER.getTypeId()).
                setEntityId(userId).
                setEntityOwnerId(userId));
        return JsonUtil.getJSONString(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(
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

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION.getTypeId(), questionId);
        eventProducer.fireEvent(new Event(EventType.FOLLOW).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.ENTITY_QUESTION.getTypeId()).
                setEntityId(questionId).
                setEntityOwnerId(questionId));

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFolloweeCount(EntityType.ENTITY_QUESTION.getTypeId(), questionId));
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

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION.getTypeId(), questionId);
        eventProducer.fireEvent(new Event(EventType.UNFOLLOW).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.ENTITY_QUESTION.getTypeId()).
                setEntityId(questionId).
                setEntityOwnerId(questionId));

        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.getFolloweeCount(EntityType.ENTITY_QUESTION.getTypeId(), questionId));
        return JsonUtil.getJSONString(ret ? 0 : 1, info);
    }

    @GetMapping("/user/{userId}/followers")
    public String followers(@PathVariable("userId") int userId, HttpServletRequest request) {
        List<Integer> followersIds = followService.getFollowers(userId, EntityType.ENTITY_USER.getTypeId(),
                10);
        if(hostHolder.getUser() != null){
            request.setAttribute("followers", getUserInfo(hostHolder.getUser().getId(), followersIds));
        }else {
            request.setAttribute("followers", getUserInfo(0, followersIds));
        }
        return "followers";
    }

    @GetMapping("/user/{userId}/followees")
    public String followees(@PathVariable("userId") int userId, HttpServletRequest request) {
        List<Integer> followeesIds = followService.getFollowers(userId, EntityType.ENTITY_USER.getTypeId(),
                10);
        if(hostHolder.getUser() != null){
            request.setAttribute("followees", getUserInfo(hostHolder.getUser().getId(), followeesIds));
        }else {
            request.setAttribute("followees", getUserInfo(0, followeesIds));
        }
        return "followees";
    }

    private List<ViewObject> getUserInfo(int localUserId, List<Integer> userIds) {
        List<ViewObject> userInfos = new ArrayList<>();
        for (Integer uid : userIds) {
            User user = userService.getUser(uid);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("followerCount", followService.getFollowerCount(EntityType.ENTITY_USER.getTypeId(), uid));
            vo.set("followeeCount", followService.getFolloweeCount(EntityType.ENTITY_USER.getTypeId(), uid));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, EntityType.ENTITY_USER.getTypeId(), uid));
            } else {
                vo.set("followed", false);
            }
            userInfos.add(vo);
        }
        return userInfos;
    }
}
