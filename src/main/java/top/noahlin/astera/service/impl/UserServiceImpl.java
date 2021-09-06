package top.noahlin.astera.service.impl;

import org.springframework.stereotype.Service;
import top.noahlin.astera.dao.UserDAO;
import top.noahlin.astera.model.User;
import top.noahlin.astera.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDAO userDAO;

    @Override
    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    @Override
    public User getUser(String username) {
        return userDAO.selectByName(username);
    }


}
