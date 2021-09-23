package top.noahlin.astera.service;

public interface LikeService {
    long like(int userId, int entityType, int entityId);

    long dislike(int userId, int entityType, int entityId);

    int getLikeStatus(int userId, int entityType, int entityId);

    long getLikeCount(int entityType, int entityId);
}
