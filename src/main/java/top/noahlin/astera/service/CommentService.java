package top.noahlin.astera.service;

import top.noahlin.astera.model.Comment;

import java.util.List;

public interface CommentService {
    int addComment(Comment comment);

    List<Comment> getComments(int entityType, int entityId);

    Comment getComment(int id);

    int getCommentCount(int entityType, int entityId);

    int getCommentCount(int userId);

    int deleteComment(int commentId);
}
