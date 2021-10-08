package top.noahlin.astera.async.handler;

import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventHandler;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.common.DefaultUser;
import top.noahlin.astera.model.Message;
import top.noahlin.astera.service.MessageService;
import top.noahlin.astera.service.UserService;

import javax.annotation.Resource;
import java.util.List;

public class LikeHandler implements EventHandler {
    @Resource
    MessageService messageService;

    @Resource
    UserService userService;

    @Override
    public void handle(Event event) {
        Message message = new Message();
        message.setFromId(DefaultUser.ANONYMOUS_USER.getId());
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return null;
    }
}
