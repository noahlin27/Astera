package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import top.noahlin.astera.service.LikeService;
import top.noahlin.astera.util.JedisAdaptor;
import top.noahlin.astera.util.RedisKeyUtil;

import javax.annotation.Resource;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    JedisAdaptor jedisAdaptor;

    @Override
    public long like(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdaptor.sadd(likeKey, String.valueOf(userId));

        dislikeCancel(userId, entityType, entityId);

        return jedisAdaptor.scard(likeKey);
    }

    @Override
    public long likeCancel(int userId, int entityType, int entityId){
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdaptor.srem(likeKey, String.valueOf(userId));

        return jedisAdaptor.scard(likeKey);
    }

    @Override
    public long dislike(int userId, int entityType, int entityId) {
        likeCancel(userId, entityType, entityId);

        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdaptor.sadd(dislikeKey, String.valueOf(userId));

        return jedisAdaptor.scard(RedisKeyUtil.getLikeKey(entityType, entityId));
    }

    @Override
    public long dislikeCancel(int userId, int entityType, int entityId){
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        jedisAdaptor.srem(dislikeKey, String.valueOf(userId));

        return jedisAdaptor.scard(RedisKeyUtil.getLikeKey(entityType, entityId));
    }

    @Override
    public int getLikeStatus(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        if (jedisAdaptor.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }
        String dislikeKey = RedisKeyUtil.getDislikeKey(entityType, entityId);
        return jedisAdaptor.sismember(dislikeKey, String.valueOf(userId)) ? -1 : 0;
    }

    @Override
    public long getLikeCount(int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        return jedisAdaptor.scard(likeKey);
    }
}
