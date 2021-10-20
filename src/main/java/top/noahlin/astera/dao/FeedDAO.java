package top.noahlin.astera.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.noahlin.astera.model.Feed;

import java.util.List;

public interface FeedDAO {
    String TABLE_NAME = "feed";
    String INSERT_FIELDS = "type, user_id, create_time, data";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{type}, #{userId}, #{createTime}, #{data})"})
    int insert(Feed feed);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Feed selectById(int id);

    List<Feed> selectFeeds(@Param("maxId") int maxId, @Param("userIds") List<Integer> userIds,
                           @Param("count") int count);
}
