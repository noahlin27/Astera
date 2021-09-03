package top.noahlin.astera.model;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static final ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser() {
        return users.get();
    }

    public void setUser(User user) {
        users.set(user);
    }

    public void clear() {
        users.remove();
    }
}
