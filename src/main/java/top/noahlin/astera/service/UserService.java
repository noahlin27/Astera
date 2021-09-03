package top.noahlin.astera.service;

import top.noahlin.astera.model.User;

import java.util.Map;

public interface UserService {
    User getUser(int id);

    User getUser(String username);

    Map<String, String> register(String username, String password);

    Map<String, String> login(String username, String password);

    String addTicket(int userId);
}
