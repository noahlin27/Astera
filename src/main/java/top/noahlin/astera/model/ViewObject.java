package top.noahlin.astera.model;

import java.util.HashMap;
import java.util.Map;

public class ViewObject {
    private final Map<String, Object> objs = new HashMap<>();

    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
