package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.service.LikeService;
import top.noahlin.astera.util.JsonUtil;

import javax.annotation.Resource;

@Controller
public class LikeController {
    @Resource
    LikeService likeService;

    @Resource
    HostHolder hostHolder;

    @PostMapping("/like")
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser()==null){
            return JsonUtil.getJSONString(999);
        }
        long likeCount = likeService.like((hostHolder.getUser().getId()), EntityType.ENTITY_COMMENT.getTypeId(), commentId);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @PostMapping("/dislike")
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser()==null){
            return JsonUtil.getJSONString(999);
        }
        long dislikeCount = likeService.dislike((hostHolder.getUser().getId()), EntityType.ENTITY_COMMENT.getTypeId(), commentId);
        return JsonUtil.getJSONString(0, String.valueOf(dislikeCount));
    }
}
