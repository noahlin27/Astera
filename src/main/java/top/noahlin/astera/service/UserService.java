package top.noahlin.astera.service;

import top.noahlin.astera.model.User;

import java.util.Map;

public interface UserService {
    User getUser(int id);

    User getUser(String username);
}
