package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import top.noahlin.astera.dao.LoginTicketDAO;
import top.noahlin.astera.dao.UserDAO;
import top.noahlin.astera.model.LoginTicket;
import top.noahlin.astera.model.User;
import top.noahlin.astera.service.UserService;
import top.noahlin.astera.util.MD5Util;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;

    @Resource
    private LoginTicketDAO loginTicketDAO;

    @Override
    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    @Override
    public User getUser(String username) {
        return userDAO.selectByName(username);
    }

    @Override
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isEmptyOrWhitespace(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isEmptyOrWhitespace(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        if (userDAO.selectByName(username) != null) {
            map.put("msg", "用户名已存在");
            return map;
        }

        User user = new User();
        Random random = new Random();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("https://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        user.setPassword(MD5Util.Encode(password + user.getSalt(), "utf-8"));
        userDAO.addUser(user);
        map.put("success", "注册成功! 请登录");
        return map;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        User user = userDAO.selectByName(username);
        if (StringUtils.isEmptyOrWhitespace(username)) {
            map.put("msg", "用户名不能为空");
        }
        if (StringUtils.isEmptyOrWhitespace(password)) {
            map.put("msg", "密码不能为空");
        }

        if (user == null) {
            map.put("msg", "用户名不存在");
            return map;
        }

        if (!MD5Util.Encode(password + user.getSalt(), "utf-8").equals(user.getPassword())) {
            map.put("msg", "用户名或密码错误");
            return map;
        }

        String ticket = addTicket((user.getId()));
        map.put("ticket", ticket);
//        map.put("success", "登录成功! 即将跳转...");
        return map;
    }

    @Override
    public String addTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(now.getTime()+3600*1000);
        Date expired = now;
        loginTicket.setExpired(expired);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
