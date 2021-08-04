package top.noahlin.blog4u.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import top.noahlin.blog4u.model.User;

public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, password, salt, head_url, is_deleted";
    String SELECT_FIELDS = "id, name, password, salt, head_url, is_deleted";

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name}, #{password}, #{salt}, #{headUrl}, #{isDeleted})"})
    int addUser(User user);
}
