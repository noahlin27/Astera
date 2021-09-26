package top.noahlin.astera.util;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String LIKE = "LIKE";
    private static final String DISLIKE = "DISLIKE";

    public static String getLikeKey(int entityType, int entityId) {
        return LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDislikeKey(int entityType, int entityId) {
        return DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }
}
