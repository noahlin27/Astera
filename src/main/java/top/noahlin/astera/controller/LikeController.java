package top.noahlin.astera.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventProducer;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.Comment;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.service.CommentService;
import top.noahlin.astera.service.LikeService;
import top.noahlin.astera.service.QuestionService;
import top.noahlin.astera.util.JsonUtil;

import javax.annotation.Resource;

@Controller
public class LikeController {
    @Resource
    LikeService likeService;

    @Resource
    HostHolder hostHolder;

    @Resource
    EventProducer eventProducer;

    @Resource
    QuestionService questionService;

    @Resource
    CommentService commentService;

    @PostMapping("/likeQuestion")
    @ResponseBody
    public String likeQuestion(@RequestParam("QuestionId") int QuestionId){
        if(hostHolder.getUser()==null){
            return JsonUtil.getJSONString(999);
        }

        Question question = questionService.getQuestion(QuestionId);
        eventProducer.fireEvent(new Event(EventType.LIKE).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EventType.LIKE.getValue()).
                setEntityId(QuestionId).
                setExt("questionId", String.valueOf(question)).
                setEntityOwnerId(question.getUserId()));

        long likeCount = likeService.like((hostHolder.getUser().getId()), EntityType.COMMENT.getTypeId(), QuestionId);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @PostMapping("/dislikeQuestion")
    @ResponseBody
    public String dislikeQuestion(@RequestParam("QuestionId") int QuestionId){
        if(hostHolder.getUser()==null){
            return JsonUtil.getJSONString(999);
        }
        long dislikeCount = likeService.dislike((hostHolder.getUser().getId()), EntityType.COMMENT.getTypeId(), QuestionId);
        return JsonUtil.getJSONString(0, String.valueOf(dislikeCount));
    }

    @PostMapping("/likeComment")
    @ResponseBody
    public String likeComment(@RequestParam("CommentId") int commentId){
        if(hostHolder.getUser()==null){
            return JsonUtil.getJSONString(999);
        }

        Comment comment = commentService.getComment(commentId);
        eventProducer.fireEvent(new Event(EventType.LIKE).
                setActorId(hostHolder.getUser().getId()).
                setEntityType(EntityType.COMMENT.getTypeId()).
                setEntityId(commentId).
                setExt("questionId", String.valueOf(comment.getEntityId())).
                setEntityOwnerId(comment.getUserId()));

        long likeCount = likeService.like((hostHolder.getUser().getId()), EntityType.COMMENT.getTypeId(), commentId);
        return JsonUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @PostMapping("/dislikeComment")
    @ResponseBody
    public String dislikeComment(@RequestParam("commentId") int commentId){
        if(hostHolder.getUser()==null){
            return JsonUtil.getJSONString(999);
        }
        long dislikeCount = likeService.dislike((hostHolder.getUser().getId()), EntityType.COMMENT.getTypeId(), commentId);
        return JsonUtil.getJSONString(0, String.valueOf(dislikeCount));
    }
}
