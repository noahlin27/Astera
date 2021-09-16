package top.noahlin.astera.service;

import top.noahlin.astera.model.User;

public interface UserService {
    int addUser(User user);

    User getUser(int id);

    User getUser(String username);
}
