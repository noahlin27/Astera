package top.noahlin.astera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventProducer;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.Comment;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.service.CommentService;
import top.noahlin.astera.service.QuestionService;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Resource
    CommentService commentService;

    @Resource
    QuestionService questionService;

    @Resource
    HostHolder hostHolder;

    @Resource
    EventProducer eventProducer;

    @PostMapping("/addComment")
    public String addComment(@RequestParam("questionId") int questionId, @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUserId(hostHolder.getUser().getId());
            comment.setEntityType(EntityType.QUESTION.getValue());
            comment.setEntityId(questionId);
            comment.setCreateTime(new Date());
            commentService.addComment(comment);

            questionService.updateCommentCount(comment.getEntityId(),
                    commentService.getCommentCount(comment.getEntityType(), comment.getEntityId()));

            eventProducer.fireEvent(
                    new Event(EventType.COMMENT).setActorId(comment.getUserId()).setEntityId(questionId));
        } catch (Exception e) {
            logger.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + questionId;
    }


}
