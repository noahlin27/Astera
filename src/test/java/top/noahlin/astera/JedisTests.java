package top.noahlin.astera;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.Tuple;
import top.noahlin.astera.util.JedisAdaptor;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JedisTests {
    @Resource
    JedisAdaptor jedisAdaptor;

    Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void print(int index, Object obj) {
        System.out.printf("%d. %s%n", index, obj.toString());
    }

    @Test
    public void basicTests() {
        jedis.flushDB();
        jedis.set("hello", "world");
        print(1, jedis.get("hello"));

        jedis.rename("hello", "newhello");
        print(2, jedis.get("newhello"));

        jedis.setex("hello2", (long) 1800, "world");
    }

    @Test
    public void pvTests() {
        jedis.flushDB();
        jedis.set("pv", "0");
        jedis.incr("pv");
        print(1, jedis.get("pv"));

        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));

        jedis.decrBy("pv", 2);
        print(3, jedis.get("pv"));

        print(4, jedis.keys("*"));
    }

    @Test
    public void listTests() {
        jedis.flushDB();
        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; ++i) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(1, jedis.lrange(listName, 0, 12));
        print(2, jedis.lrange(listName, 0, 3));
        print(3, jedis.llen(listName));
        print(4, jedis.lpop(listName));
        print(5, jedis.llen(listName));
        print(6, jedis.lindex(listName, 0));
        print(7, jedis.linsert(listName, ListPosition.BEFORE, "a4", "before"));
        print(8, jedis.linsert(listName, ListPosition.AFTER, "a4", "after"));
        print(9, jedis.lrange(listName, 0, 12));
    }

    @Test
    public void hashTests() {
        jedis.flushDB();
        String userKey = "userx";
        jedis.hset(userKey, "name", "gjy");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "151****5372");
        print(1, jedis.hget(userKey, "name"));
        print(2, jedis.hgetAll(userKey));

        jedis.hdel(userKey, "phone");
        print(3, jedis.hgetAll(userKey));

        print(4, jedis.hexists(userKey, "email"));
        print(5, jedis.hexists(userKey, "age"));
        print(6, jedis.hkeys(userKey));
        print(7, jedis.hvals(userKey));

        jedis.hsetnx(userKey, "school", "njupt");
        jedis.hsetnx(userKey, "name", "noah");
        print(8, jedis.hgetAll(userKey));
    }

    @Test
    public void setTests() {
        jedis.flushDB();
        String likeKey1 = "CommentLike1";
        String likeKey2 = "CommentLike2";
        for (int i = 0; i < 10; ++i) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }
        print(1, jedis.smembers(likeKey1));
        print(2, jedis.smembers(likeKey2));
        print(3, jedis.sunion(likeKey1, likeKey2));
        print(4, jedis.sdiff(likeKey1, likeKey2));
        print(4, jedis.sdiff(likeKey2, likeKey1));
        print(5, jedis.sinter(likeKey1, likeKey2));
        print(6, jedis.sismember(likeKey1, "12"));
        print(7, jedis.sismember(likeKey2, "16"));

        jedis.srem(likeKey1, "5");
        print(8, jedis.smembers(likeKey1));

        jedis.smove(likeKey2, likeKey1, "25");
        print(9, jedis.smembers(likeKey1));
        print(10, jedis.scard(likeKey1));
    }

    @Test
    public void zsetTests1() {
        jedis.flushDB();
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "low");
        jedis.zadd(rankKey, 60, "normal");
        jedis.zadd(rankKey, 75, "medium");
        jedis.zadd(rankKey, 80, "high");
        jedis.zadd(rankKey, 90, "very high");
        print(1, jedis.zcard(rankKey));
        print(2, jedis.zcount(rankKey, 60, 100));
        print(3, jedis.zscore(rankKey, "medium"));

        jedis.zincrby(rankKey, 2, "low");
        print(4, jedis.zscore(rankKey, "low"));

        jedis.zincrby(rankKey, 5, "lao");
        print(5, jedis.zscore(rankKey, "lao"));

        print(6, jedis.zrange(rankKey, 0, 100));
        print(7, jedis.zrange(rankKey, 0, 10));
        print(8, jedis.zrange(rankKey, 1, 3));
        print(9, jedis.zrevrange(rankKey, 1, 3));

        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "60", "100")) {
            print(10, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }

        print(11, jedis.zrank(rankKey, "high"));
        print(12, jedis.zrevrank(rankKey, "high"));
    }

    @Test
    public void zsetTests2() {
        jedis.flushDB();
        String setKey = "zset";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");
        print(1, jedis.zlexcount(setKey, "-", "+"));
        print(2, jedis.zlexcount(setKey, "(b", "[d"));
        print(3, jedis.zlexcount(setKey, "[b", "[d"));

        jedis.zrem(setKey, "b");
        print(4, jedis.zrange(setKey, 0, 10));

        jedis.zremrangeByLex(setKey, "(c", "+");
        print(5, jedis.zrange(setKey, 0, 2));
    }

    @Test
    public void poolTests(){
        jedisAdaptor.sadd("key1", "value1");

    }
}
