package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import top.noahlin.astera.service.FollowService;
import top.noahlin.astera.util.JedisAdaptor;
import top.noahlin.astera.util.RedisKeyUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowServiceImpl implements FollowService {
    @Resource
    JedisAdaptor jedisAdaptor;

    @Override
    public boolean follow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        Date date = new Date();
        Jedis jedis = jedisAdaptor.getJedis();
        Transaction transaction = jedisAdaptor.multi(jedis);
        transaction.zadd(followerKey, date.getTime(), String.valueOf(userId));
        transaction.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> ret = jedisAdaptor.exec(transaction, jedis);
        return ret.size() == 2 && (long) ret.get(0) > 0 && (long) ret.get(1) > 0;
    }

    @Override
    public boolean unfollow(int userId, int entityType, int entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        Date date = new Date();
        Jedis jedis = jedisAdaptor.getJedis();
        Transaction transaction = jedisAdaptor.multi(jedis);
        transaction.zrem(followerKey, String.valueOf(userId));
        transaction.zrem(followeeKey, String.valueOf(entityId));
        List<Object> ret = jedisAdaptor.exec(transaction, jedis);
        return ret.size() == 2 && (long) ret.get(0) > 0 && (long) ret.get(1) > 0;
    }

    public List<Integer> getIdsFromSet(Set<String> set) {
        List<Integer> ids = new ArrayList<>();
        for (String str : set) {
            ids.add(Integer.parseInt(str));
        }
        return ids;
    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdaptor.zrevrange(followerKey, 0, count));
    }

    @Override
    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return getIdsFromSet(jedisAdaptor.zrevrange(followerKey, offset, count));
    }

    @Override
    public List<Integer> getFollowees(int entityType, int entityId, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType,entityId);
        return getIdsFromSet(jedisAdaptor.zrevrange(followeeKey, 0, count));
    }

    @Override
    public List<Integer> getFollowees(int entityType, int entityId, int offset, int count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType,entityId);
        return getIdsFromSet(jedisAdaptor.zrevrange(followeeKey, offset, count));
    }

    @Override
    public long getFollowerCount(int entityType, int entityId) {
        String followerKey= RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdaptor.zcard(followerKey);
    }

    @Override
    public long getFolloweeCount(int entityType, int entityId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        return jedisAdaptor.zcard(followeeKey);
    }

    @Override
    public boolean isFollower(int userId, int entityType, int entityId) {
        String followerKey= RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdaptor.zscore(followerKey, String.valueOf(userId)) != null;
    }
}
