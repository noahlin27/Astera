package top.noahlin.astera.async.handler;

import org.springframework.stereotype.Component;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventHandler;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.util.MailUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginExceptionHandler implements EventHandler {
    @Resource
    MailUtil mailUtil;

    @Override
    public void handle(Event event) {
        Map<String, Object> map = new HashMap<>();
        map.put("username", event.getExt("username"));
        mailUtil.sendWithTemplate(event.getExt("email"), "登录提醒", "mails/loginException", map);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return List.of(EventType.LOGIN);
    }
}
