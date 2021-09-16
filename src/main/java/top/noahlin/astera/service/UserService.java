package top.noahlin.astera.service;

import top.noahlin.astera.model.User;

public interface UserService {
    User getUser(int id);

    User getUser(String username);
}
