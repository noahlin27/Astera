package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;
import top.noahlin.astera.dao.CommentDAO;
import top.noahlin.astera.model.Comment;
import top.noahlin.astera.service.CommentService;
import top.noahlin.astera.util.SensitiveFilterUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentDAO commentDAO;

    @Resource
    SensitiveFilterUtil sensitiveFilterUtil;

    @Override
    public int addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        return commentDAO.insert(comment) > 0 ? comment.getId() : 0;
    }

    @Override
    public List<Comment> getComments(int entityType, int entityId) {
        List<Comment> comments = commentDAO.selectCommentsByEntity(entityType, entityId);
        for (Comment comment:comments){
            comment.setContent(sensitiveFilterUtil.filter(comment.getContent()));
        }
        return comments;
    }

    @Override
    public Comment getComment(int id) {
        Comment comment = commentDAO.selectById(id);
        comment.setContent(sensitiveFilterUtil.filter(comment.getContent()));
        return comment;
    }

    @Override
    public int getCommentCount(int entityType, int entityId) {
        return commentDAO.selectCount(entityType, entityId);
    }

    @Override
    public int getCommentCount(int userId){
        return commentDAO.selectCountByUserId(userId);
    }

    @Override
    public int deleteComment(int commentId) {
        return commentDAO.updateStatus(commentId, 1);
    }
}
