package top.noahlin.astera.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.noahlin.astera.model.Comment;
import top.noahlin.astera.model.Question;

import java.util.List;

public interface CommentDAO {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "content, user_id, entity_id, entity_type, create_time, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{content}, #{userId}, #{entityId}, #{entityType}, #{createTime}, #{status})"})
    int insert(Comment comment);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType} order by create_date desc"})
    List<Comment> selectCommentsByEntity(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Select({"select count(id) from", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType}"})
    int selectCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
