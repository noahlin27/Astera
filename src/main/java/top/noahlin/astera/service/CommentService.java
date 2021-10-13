package top.noahlin.astera.service;

import top.noahlin.astera.model.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    List<Comment> getCommentsByEntity(int entityId, int entityType);

    Comment getComment(int id);

    int getCommentCount(int entityType, int entityId);

    int deleteComment(int commentId);
}
