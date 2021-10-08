package top.noahlin.astera.async;

import java.util.List;

public interface EventHandler {
    void handle(Event event);

    List<EventType> getSupportEventTypes();
}
