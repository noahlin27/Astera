package top.noahlin.astera.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.noahlin.astera.model.Comment;
import top.noahlin.astera.model.Message;

import java.util.List;

public interface MessageDAO {
    String TABLE_NAME = "message";
    String INSERT_FIELDS = "from_id, to_id, content, create_time, has_read, conversation_id";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{fromId}, #{toId}, #{content}, #{createTime}, #{hasRead}, #{conversationId})"})
    int insert(Message message);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Comment selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where conversation_id=#{conversationId} order by created_time desc limit #{offset}, #{limit}"})
    List<Message> selectLatest(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from", TABLE_NAME, "where entity_id=#{entityId} and entity_type=#{entityType}"})
    int selectCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("id") int id, @Param("status") int status);
}
