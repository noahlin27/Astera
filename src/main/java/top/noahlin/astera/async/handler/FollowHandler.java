package top.noahlin.astera.async.handler;

import org.springframework.stereotype.Component;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventHandler;
import top.noahlin.astera.async.EventType;
import top.noahlin.astera.common.DefaultUser;
import top.noahlin.astera.common.EntityType;
import top.noahlin.astera.model.Message;
import top.noahlin.astera.model.User;
import top.noahlin.astera.service.MessageService;
import top.noahlin.astera.service.UserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class FollowHandler implements EventHandler {
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
        if (event.getEntityType() == EntityType.QUESTION.getValue()) {
            message.setContent(user.getName() + "关注了你的问题");
        } else if (event.getEntityType() == EntityType.USER.getValue()) {
            message.setContent(user.getName() + "关注了你");
        }
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return List.of(EventType.FOLLOW);
    }
}
