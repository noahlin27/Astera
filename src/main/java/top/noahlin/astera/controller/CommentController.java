package top.noahlin.astera.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.noahlin.astera.common.CommentEntityTypeEnum;
import top.noahlin.astera.model.Comment;
import top.noahlin.astera.model.HostHolder;
import top.noahlin.astera.service.CommentService;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Resource
    CommentService commentService;

    @Resource
    HostHolder hostHolder;

    @PostMapping("/addComment")
    public String addComment(@RequestParam("questionId") int questionId, @RequestParam("content") String content) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUserId(hostHolder.getUser().getId());
            comment.setEntityType(CommentEntityTypeEnum.ENTITY_QUESTION.getTypeId());
            comment.setEntityId(questionId);
            comment.setCreateTime(new Date());
            commentService.addComment(comment);
        } catch (Exception e) {
            logger.error("增加评论失败"+e.getMessage());
        }
        return "redirect:/question/"+questionId;
    }


}
