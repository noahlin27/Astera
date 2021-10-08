package top.noahlin.astera.async;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import top.noahlin.astera.util.JedisAdaptor;
import top.noahlin.astera.util.RedisKeyUtil;

import javax.annotation.Resource;

@Service
public class EventProducer {
    @Resource
    JedisAdaptor jedisAdaptor;

    public boolean fireEvent(Event event){
        try {
            String json = JSONObject.toJSONString(event);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdaptor.lpush(key, json);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
