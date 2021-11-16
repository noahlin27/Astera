package top.noahlin.astera.service;

import java.util.Map;

public interface LoginService {
    Map<String, String> register(String username, String password);

    Map<String, String> login(String username, String password);

    void logout(String ticket);

}
