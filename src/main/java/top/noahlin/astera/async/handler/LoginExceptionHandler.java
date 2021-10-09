package top.noahlin.astera.async.handler;

import org.springframework.stereotype.Component;
import top.noahlin.astera.async.Event;
import top.noahlin.astera.async.EventHandler;
import top.noahlin.astera.async.EventType;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LoginExceptionHandler implements EventHandler {
    @Resource


    @Override
    public void handle(Event event) {

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return null;
    }
}
