package top.noahlin.astera.controller;

import io.swagger.models.auth.In;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.Feed;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.service.FeedService;
import top.noahlin.astera.service.FollowService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Resource
    FeedService feedService;

    @Resource
    HostHolder hostHolder;

    @Resource
    FollowService followService;

    @GetMapping("/pull_feeds")
    private String getPullFeeds(HttpServletRequest request) {
        int localUserId = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();

        List<Integer> following = new ArrayList<>();
        if (localUserId != 0) {
            following = followService.getFollowing(localUserId, EntityType.ENTITY_USER.getTypeId(), Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getFeeds(Integer.MAX_VALUE, following, 10);
        request.setAttribute("feeds", feeds);
        return "feeds";
    }
}
