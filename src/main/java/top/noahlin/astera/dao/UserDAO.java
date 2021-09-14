package top.noahlin.astera.dao;

import org.apache.ibatis.annotations.*;
import top.noahlin.astera.model.User;

public interface UserDAO {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, password, salt, head_url, is_deleted";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{name}, #{password}, #{salt}, #{headUrl}, #{isDeleted})"})
    void insert(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    User selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}"})
    User selectByName(String username);

    @Update({"update", TABLE_NAME, "set password=#{password}, salt=#{salt} where id=#{id}"})
    void updatePassword(User user);

    @Update({"update", TABLE_NAME, "set is_deleted=1 where id=#{id}"})
    void updateIsDeleted(int id);

}
