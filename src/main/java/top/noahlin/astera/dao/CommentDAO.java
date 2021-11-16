package top.noahlin.astera.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.noahlin.astera.model.Comment;

import java.util.List;

public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "content, user_id, entity_type, entity_id, create_time, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{content}, #{userId}, #{entityType}, #{entityId}, #{createTime}, #{status})"})
    int insert(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where entity_type=#{entityType} and entity_id=#{entityId} order by create_time desc"})
    List<Comment> selectCommentsByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    @Select({"select count(id) from", TABLE_NAME, "where entity_type=#{entityType} and entity_id=#{entityId}"})
    int selectCount(@Param("entityType") int entityType, @Param("entityId") int entityId);

    @Select({"select count(id) from", TABLE_NAME, "where user_id=#{userId}"})
    int selectCountByUserId(@Param("userId") int userId);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
