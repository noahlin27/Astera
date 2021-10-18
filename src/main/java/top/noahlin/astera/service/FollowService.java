package top.noahlin.astera.service;

import java.util.List;

public interface FollowService {
    boolean follow(int userId, int entityType, int entityId);

    boolean unfollow(int userId, int entityType, int entityId);

    List<Integer> getFollowers(int entityType, int entityId, int count);

    List<Integer> getFollowers(int entityType, int entityId, int offset, int count);

    List<Integer> getFollowing(int entityType, int entityId, int count);

    List<Integer> getFollowing(int entityType, int entityId, int offset, int count);

    long getFollowerCount(int entityType, int entityId);

    long getFollowingCount(int entityType, int entityId);

    boolean isFollower(int userId, int entityType, int entityId);
}
