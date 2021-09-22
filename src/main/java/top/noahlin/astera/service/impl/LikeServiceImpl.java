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

        String dislikeKey = RedisKeyUtil.getDisikeKey(entityType, entityId);
        jedisAdaptor.srem(dislikeKey, String.valueOf(userId));

        return jedisAdaptor.scard(likeKey);
    }

    public long dislike(int userId, int entityType, int entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdaptor.srem(likeKey, String.valueOf(userId));

        String dislikeKey = RedisKeyUtil.getDisikeKey(entityType, entityId);
        jedisAdaptor.sadd(dislikeKey, String.valueOf(userId));

        return jedisAdaptor.scard(likeKey);
    }
}
