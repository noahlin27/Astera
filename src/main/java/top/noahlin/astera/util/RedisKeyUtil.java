package top.noahlin.astera.util;

import javax.xml.transform.sax.SAXResult;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String LIKE = "LIKE";
    private static final String DISLIKE = "DISLIKE";

    private static final String EVENT_QUEUE = "EVENT_QUEUE";

    private static final String FOLLOWER = "FOLLOWER";
    private static final String FOLLOWING = "FOLLOWING";
    private static final String TIMELINE = "TIMELINE";

    public static String getLikeKey(int entityType, int entityId) {
        return LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDislikeKey(int entityType, int entityId) {
        return DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return EVENT_QUEUE;
    }

    public static String getFollowerKey(int entityType, int entityId) {
        return FOLLOWER + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getFollowingKey(int entityType, int entityId) {
        return FOLLOWING + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getTimelineKey(int userId){
        return TIMELINE + SPLIT + String.valueOf(userId);
    }
}
