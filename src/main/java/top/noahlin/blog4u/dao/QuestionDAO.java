package top.noahlin.blog4u.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.noahlin.blog4u.model.Question;
import top.noahlin.blog4u.model.User;

import java.util.List;

public interface QuestionDAO {
    String TABLE_NAME = "question";
    String INSERT_FIELDS = "title, content, create_time, user_id, comment_count";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{title}, #{content}, #{createTime}, #{userId}, #{commentCount})"})
    int addQuestion(Question question);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

    @Update({"update", TABLE_NAME, "set password=#{password} where id=#{id}"})
    void updatePassword(User user);

    @Update({"update", TABLE_NAME, "set is_deleted=1 where id=#{id}"})
    void deleteById(int id);
}
