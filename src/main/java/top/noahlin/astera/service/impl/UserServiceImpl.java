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


}
