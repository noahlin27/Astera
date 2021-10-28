package top.noahlin.astera.async.handler;

import org.springframework.stereotype.Component;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventHandler;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.common.DefaultUser;
import top.noahlin.astera.model.Message;
import top.noahlin.astera.model.User;
import top.noahlin.astera.service.MessageService;
import top.noahlin.astera.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Resource
    MessageService messageService;

    @Resource
    UserService userService;

    @Override
    public void handle(Event event) {
        Message message = new Message();
        message.setFromId(DefaultUser.SYSTEM_USER.getId());
        message.setToId(event.getEntityOwnerId());
        message.setCreateTime(new Date());
        User user = userService.getUser(event.getActorId());
        message.setContent(user.getName() + event.getExt("message"));
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return List.of(EventType.LIKE);
    }
}
