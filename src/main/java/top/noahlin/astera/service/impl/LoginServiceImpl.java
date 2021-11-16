package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import top.noahlin.astera.dao.LoginTicketDAO;
import top.noahlin.astera.dao.UserDAO;
import top.noahlin.astera.model.LoginTicket;
import top.noahlin.astera.model.User;
import top.noahlin.astera.service.LoginService;
import top.noahlin.astera.util.MD5Util;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    UserDAO userDAO;

    @Resource
    LoginTicketDAO loginTicketDAO;

    @Override
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
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
        user.setHeadUrl(String.format("/images/img/%d.png", random.nextInt(36)));
        user.setPassword(MD5Util.Encode(password + user.getSalt(), "utf-8"));
        userDAO.insert(user);
        map.put("success", "注册成功! 请登录");
        return map;
    }

    @Override
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        User user = userDAO.selectByName(username);
        if (StringUtils.isBlank(username)) {
            map.put("msg", "用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
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
        return map;
    }

    @Override
    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    private String addTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(now.getTime()+3600*1000*24);
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.insert(loginTicket);
        return loginTicket.getTicket();
    }
}
