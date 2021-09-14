package top.noahlin.astera.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.noahlin.astera.model.Question;
import top.noahlin.astera.model.User;

import java.util.List;

public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title, content, create_time, user_id, comment_count";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title}, #{content}, #{createTime}, #{userId}, #{commentCount})"})
    int insert(Question question);

    List<Question> selectLatest(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Question selectById(int id);
}
