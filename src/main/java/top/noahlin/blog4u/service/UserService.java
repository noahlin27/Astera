package top.noahlin.blog4u.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.noahlin.blog4u.dao.UserDAO;
import top.noahlin.blog4u.model.User;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

    public User getUser(int id){
        return userDAO.selectById(id);
    }
}
