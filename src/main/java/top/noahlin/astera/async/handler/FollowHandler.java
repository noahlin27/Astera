package top.noahlin.astera.async.handler;

import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventHandler;
import top.noahlin.astera.async.EventType;

import java.util.List;

public class FollowHandler implements EventHandler {
    @Override
    public void handle(Event event) {

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return List.of(EventType.FOLLOW);
    }
}
